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

    public String getSignature() {
        return signature;
    }

    public int getCounter() {
        return counter;
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
            setWords(new ArrayList<>());
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void addCharacter(char key) {
        Set<String> temp = myDict.signatureToWords(signature + key);
        if (temp.size() != 0) {
            setWords(new ArrayList<>(temp));
            getMessage().set(getMessage().size()-1, getWords().get(getCounter() % getWords().size()));
            setSignature(signature + key);
            setCounter(0);
            setChanged();
            notifyObservers();
        }
    }


    @Override
    public List<String> getMessage() {
        return message;
    }

    @Override
    public void nextMatch() {
        if (getWords().size() != 0) {
            setCounter((counter + 1) % words.size());
            getMessage().set(getMessage().size()-1, getWords().get(getCounter()));
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void removeLastCharacter() {
        if (getSignature().length() != 0) {
            setSignature(getSignature().substring(0, getSignature().length()-1));
            setWords(new ArrayList<>(myDict.signatureToWords(getSignature())));

//            Get the "current" word from the last point of the message
            String currentWord = getMessage().get(getMessage().size()-1);
            getMessage().set(getMessage().size()-1, currentWord.substring(0, currentWord.length()-1));
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
    }
}
