package predictive;
//import java.math.BigInteger;

/** WordSig.java
 * WordSig is a class which pairs numeric signatures with words.
 *
 * Created by Robert Campbell on 01/02/2017.
 * @version 12/02/2017
 */
public class WordSig implements Comparable<WordSig>{
    private String signature;
    private String words;

    /** Constructor for the WordSig class
     *
     * Note this class does not check the validity of words or signatures.
     * This validation testing must be handled by the class constructing
     * the WordSig object
     *
     * @param signature the signature you wish to pair with a word
     * @param word the word you wish to pair with a signature
     */
    public WordSig(String signature, String word) {
        this.words = word;
        this.signature = signature;
    }

    /** getter method to retrieve the words associated with the WordSig
     * object
     *
     * @return the String words for the current object
     */
    public String getWord() {
        return this.words;
    }

    /** getter method to retrieve the signature associated with the WordSig
     * object
     *
     * @return the String signature for the current object
     */
    public String getSignature() {
        return this.signature;
    }

    /** compareTo is a method that must be implemented due to contract
     * made with the Comparable interface.
     *
     * I looked at different ways of sorting the WordSig objects including
     * using BigInteger, length, etc. so that they could be sorted in absolute
     * numerical order but this was unnecessary and slower than simply sorting
     * based on their String signatures.
     *
     * @param ws the WordSig object you wish to compare with the current WordSig
     * @return -1 or lower, 0, or 1 or higher according to whether the current object is less than,
     * equal to, or greater than the argument object, in the intended ordering.
     */
    @Override
    public int compareTo(WordSig ws) {
        return this.getSignature().compareTo(ws.getSignature());

//        The Code below isn't any more efficient than the above code for massive signatures!!!!

//        BigInteger w1 = new BigInteger(this.getSignature());
//        BigInteger w2 = new BigInteger(ws.getSignature());
//        return w1.compareTo(w2);

//        String a = this.getSignature();
//        String b = ws.getSignature();
//
//        if (a.length() < b.length() && a.length() <= 9) {
//            return -1;
//        }
//        else if (a.length() >= 8 || b.length() >= 8) {
//            return (new BigInteger((a))).compareTo(new BigInteger(b));
//        }
//        else {
//            Long la = Long.parseLong(a);
//            return la.compareTo(Long.parseLong(b));
//        }
    }
}
