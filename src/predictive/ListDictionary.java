package predictive;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** ListDictionary.java
 * ListDictionary is a class for Worksheet 3: Predictive Text Entry for
 * MSc Software Workshop, Spring Term 2016-2017
 *
 * This exercise assesses several concepts taught on the course
 * including data structures and algorithms.
 * Created by Robert Campbell on 01/02/2017.
 * @version 12/02/2017
 * */
public class ListDictionary {
    private static ArrayList<WordSig> myDictionary = new ArrayList<>();

    /** The ListDictionary constructor takes a String path to the dictionary
     * and creates an ArrayList<WordSig> dictionary.
     * Each entry of the ArrayList is a pair, consisting of the word that has
     * been read in and its signature. These entries are objects of the class WordSig.
     *
     * Once the scanner has finished reading each line of the dictionary .txt
     * file the ArrayList is sorted so that lookups can be performed using the
     * binary search method.
     *
     * @param path the filepath for the dictionary .txt file
     */
    public ListDictionary(String path) {
        String s;

        try (Scanner scan = new Scanner(new File(path))) {

            while (scan.hasNextLine()) {
                s = scan.nextLine().toLowerCase();
                if (isValidWord(s)) {
                    this.myDictionary.add(new WordSig(PredictivePrototype.wordToSignature(s), s));
//                    System.out.println(s);
                }
            }
            scan.close();
            Collections.sort(this.myDictionary);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /** isValidWord is a method which takes a String word and checks
     * to see if any of its characters are non-alphabetic. If every character of
     * the word is alphabetic then the method returns true.
     * If not the method returns false.
     *
     * @param word a String word
     * @return a boolean indicating whether the word contains only alphabetical characters
     */
    public static boolean isValidWord(String word) {
//        word = word.toLowerCase();
        Pattern p = Pattern.compile("[A-z]");
        Matcher m;
        for (int i = 0; i < word.length(); i++) {
            m = p.matcher(word.substring(i,i+1));
//            System.out.println(word.substring(i,i+1));
            if (!m.matches()) {
                return false;
            }
        }
        return true;
    }

    /** signatureToWords is a method that takes a numeric signature and
     * returns a set of possible matching words from the dictionary
     * (default dictionary: "smallDict.txt"). If the dictionary can not
     * be found then a fileNotFoundException will be thrown.
     *
     * The dictionary is stored in an ArrayList<WordSig> called myDictionary.
     *
     * Before scanning the dictionary the method first checks the validity of the signature
     * using the isValidSignature method. If the signature is invalid then the method returns
     * an empty set
     *
     * The returned list of words does not have duplicates, each word is in lowercase, and
     * each word does not contain any non-alphabetic characters.
     * @param signature the numeric signature to be used for matching
     * @return a set of possible matching words from the dictionary
     */
    public static Set<String> signatureToWords(String signature) {
        if (PredictivePrototype.isValidSignature(signature)) {
            Set<String> set = new HashSet<>();
            int pos = Collections.binarySearch(myDictionary, new WordSig(signature, null));
            if (pos < 0) return set;

            int backtrack = pos;

            //Move forward through the dictionary adding words with a matching signature to the result set
            while (pos < myDictionary.size() - 1 && myDictionary.get(pos).getSignature().equals(signature)) {
                set.add(myDictionary.get(pos).getWord());
                pos++;
            }
            //Move backwards through the dictionary adding words with a matching signature to the result set
            while (0 <= backtrack && myDictionary.get(backtrack).getSignature().equals(signature)) {
                set.add(myDictionary.get(backtrack).getWord());
                backtrack--;
            }
            return set;
        }
        else {
            return new HashSet<>();
        }
    }


    public static void main(String[] args) {
//        Long startTime = System.nanoTime();
//        ListDictionary l1 = new ListDictionary("smallDict.txt");
//        Long endTime = System.nanoTime();
//        System.out.println(endTime - startTime);

//        System.out.println(l1);
//        signatureToWords("227");

//        System.out.println(signatureToWords("227").toString());

    }
}
