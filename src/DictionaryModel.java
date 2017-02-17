import predictive.TreeDictionary;

import java.util.*;

/**
 * Created by Rob on 17/02/2017.
 */
public class DictionaryModel extends Observable implements DictionaryModelInterface {
    private TreeDictionary myDict;
    private String signature = "";
    private Set<String> temp = new HashSet<>();
    private List<String> words = new ArrayList<>();
    private List<String> message = new ArrayList<>();
    private int counter = 0;

    public DictionaryModel(String dictionaryFile) throws java.io.IOException {
        this.myDict = new TreeDictionary(dictionaryFile);
        addToMessage("");
    }

    public DictionaryModel() throws java.io.IOException {
        this.myDict = new TreeDictionary("words.txt");
        addToMessage("");
    }

    public List<String> getWords() {
        return words;
    }

    public Set<String> getTemp() {
        return temp;
    }

    public String getSignature() {
        return signature;
    }

    public int getCounter() {
        return counter;
    }

    public void setTemp(Set<String> wordset) {
        this.temp = wordset;
    }

    public void setWords(List<String> wordlist) {
        this.words = wordlist;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setCounter(int number) {
        this.counter = number;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public void addToMessage(String word) {
        getMessage().add(word);
    }

    @Override
    public void acceptWord() {
        if (words.size() != 0) {
            addToMessage("");
            setSignature("");
//            temp = new HashSet<>();
            setTemp(new HashSet<>());
//            words = new ArrayList<>();
            setWords(new ArrayList<>());
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void addCharacter(char key) {
//        temp = myDict.signatureToWords(signature + key);
        setTemp(myDict.signatureToWords(signature + key));
        if (getTemp().size() != 0) {
//            this.words = new ArrayList<>(temp);
            setWords(new ArrayList<>(getTemp()));
//            message.set(message.size()-1, words.get(counter % words.size()));
            getMessage().set(getMessage().size()-1, getWords().get(getCounter() % getWords().size()));
//            this.signature = signature + key;
            setSignature(signature + key);
//            counter = 0;
            setCounter(0);
            setChanged();
            notifyObservers();
        }
//        System.out.println(signature);
    }


    @Override
    public List<String> getMessage() {
        return message;
    }

    @Override
    public void nextMatch() {
        if (getWords().size() != 0) {
//            counter = (counter + 1) % words.size();
            setCounter((counter + 1) % words.size());
//            System.out.println(words.get(counter));
//            message.set(message.size()-1, words.get(counter));
            getMessage().set(getMessage().size()-1, getWords().get(getCounter()));
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void removeLastCharacter() {
        if (getSignature().length() != 0) {
//            this.signature = signature.substring(0, signature.length()-1);
            setSignature(getSignature().substring(0, getSignature().length()-1));
//            this.words = new ArrayList<>(myDict.signatureToWords(signature));
            setWords(new ArrayList<>(myDict.signatureToWords(getSignature())));

//            Get the "current" word from the last point of the message
//            String currentWord = message.get(message.size()-1);
            String currentWord = getMessage().get(getMessage().size()-1);
//            message.set(message.size()-1, currentWord.substring(0, currentWord.length()-1));
            getMessage().set(getMessage().size()-1, currentWord.substring(0, currentWord.length()-1));
//            counter = 0;
            setCounter(0);
        }
        else if (getSignature().length() == 0 && getMessage().size() > 1) {
            getMessage().remove(getMessage().size()-1);
        }
        else {
            setMessage(new ArrayList<>());
            addToMessage("");
        }
        setChanged();
        notifyObservers();
//        System.out.println(this.signature);
    }
}
