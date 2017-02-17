package predictive;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/** TreeDictionary.java
 *
 * TreeDictionary is a class for Worksheet 3: Predictive Text Entry for
 * MSc Software Workshop, Spring Term 2016-2017
 *
 * Created by Robert Campbell on 10/02/2017.
 * @version 13/02/2017
 */
public class TreeDictionary implements Dictionary {
    private TreeDictionary[] children;
    private Set<String> words;

    /** Constructor for initialising default TreeDictionary objects.
     * This constructor takes no parameters and initialises the
     * children and words field variables to their default values.
     *
     */
    public TreeDictionary() {
        this.children = new TreeDictionary[8];
        this.words = new HashSet<>();
    }

    /** Constructor for the TreeDictionary class which takes a path
     * parameter. If the path is valid then the constructor calls a
     * helper method addWord whilst scanning the .txt dictionary file
     * and adds any valid words in the Trie in the correct place.
     *
     * @param path the filepath for the dictionary .txt file
     */
    public TreeDictionary(String path) {
        this.children = new TreeDictionary[8];
        this.words = new HashSet<>();
        String s;

        try (Scanner scan = new Scanner(new File(path))) {

            while (scan.hasNextLine()) {
                s = scan.nextLine().toLowerCase();
                if (ListDictionary.isValidWord(s)) {
//                    System.out.println(s);
                    addWord(s, PredictivePrototype.wordToSignature(s));
                }
            }
            scan.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /** addWord is a recursive method which iterates through the TreeDictionary
     * and adds a given word to each successive node of the Tree based on the signature
     * of each letter in that word.
     *
     * @param word the word you wish to add to the TreeDictionary
     * @param signature the signature of the word you wish to add to the TreeDictionary
     */
    public void addWord(String word, String signature) {
        if (signature.length() >= 1) {
            int i = signature.charAt(0) - '2';

            if (getChildren()[i] == null) {
                getChildren()[i] = new TreeDictionary();
//                System.out.println(word);
                getChildren()[i].getWords().add(word);
                getChildren()[i].addWord(word, signature.substring(1));
            }
            else {
//                System.out.println(word);
                getChildren()[i].getWords().add(word);
                getChildren()[i].addWord(word, signature.substring(1));
            }
        }
    }

    /** getter to return the Set<String> of words contained at a child node
     * of a tree.
     *
     * @return a Set<String> containing the words at a particular child node
     */
    public Set<String> getWords() {
        return words;
    }

    /** getter to return an Array of TreeDictionary containing each of the child
     * nodes at the current node of the tree
     *
     * @return an Array of TreeDictionary child nodes
     */
    public TreeDictionary[] getChildren() {
        return children;
    }

    /** signatureToWords is a method which returns a set of words and prefixes
     * associated with the given numeric signature.
     *
     * @param signature the numeric signature to be used for matching
     * @return a set of words and prefixes matching the given signature
     */
    @Override
    public Set<String> signatureToWords(String signature) {
        if (signature.length() <= 0) {
            return new HashSet<>();
        } else {
            return trimSignatureToWords(signature, signature.length());
        }
    }


    /** trimSignatureToWords is a recursive method which iterates through the
     * TreeDictionary and returns all of the words and prefixes that match the given
     * signature.
     *
     * The prefixes are made by taking any complete word whose prefix matches the given
     * signature and then trimming that word to the length of the given signature.
     *
     * @param signature the numeric signature to be used for matching
     * @param trimmedLength the length of the signature provided
     * @return a HashSet<String> of matching words and prefixes
     */
    public Set<String> trimSignatureToWords(String signature, int trimmedLength) {
        int i = signature.charAt(0) - '0' - 2;
        TreeDictionary child = getChildren()[i];
        if (i < 0 || i > 8) {
            return new HashSet<>();
        }
//        System.out.println(signature);
        if (child == null) {
            return new HashSet<>();
        }
        else if (signature.length() == 1) {
            Set<String> results = new HashSet<>();
            for (String s : child.getWords()) {
                results.add(s.substring(0, trimmedLength));
            }
            return results;
        }
        else {
            return child.trimSignatureToWords(signature.substring(1), trimmedLength);
        }
    }

    public static void main(String[] args) {
        TreeDictionary t1 = new TreeDictionary("words.txt");

        System.out.println(": " + t1.signatureToWords(""));
//        System.out.println("27753: " + t1.signatureToWords("27753"));
//        System.out.println("3: " + t1.signatureToWords("3"));
//        System.out.println("4: " + t1.signatureToWords("4"));
//        System.out.println("5: " + t1.signatureToWords("5"));
//        System.out.println("6: " + t1.signatureToWords("6"));
//        System.out.println("7: " + t1.signatureToWords("7"));
//        System.out.println("8: " + t1.signatureToWords("8"));
//        System.out.println("9: " + t1.signatureToWords("9"));


//        TreeDictionary[] t2 = new TreeDictionary[8];
//
//        t2[0] = new TreeDictionary();
//        t2[0].words.add("test");
//        for (TreeDictionary tree : t2) {
//            if (tree != null) {
//                System.out.println(tree.getWords());
//            }
//        }
//
//        String s = "a";
//        int i = Integer.parseInt(PredictivePrototype.wordToSignature(s.substring(0,1)));
//        System.out.println(i);

    }
}
