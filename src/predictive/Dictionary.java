package predictive;

import java.util.Set;

/**
 * Created by Robert Campbell on 01/02/2017.
 * @version 01/02/2017
 */
public interface Dictionary {

    public Set<String> signatureToWords(String signature);
}
