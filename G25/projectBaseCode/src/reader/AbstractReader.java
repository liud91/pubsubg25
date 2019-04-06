package reader;

/**
 * Parent class for all reader classes
 * @author dmaclean, dliu, awebb, jbowman, ikhan
 *
 */
public class AbstractReader {
    protected String fileName;
   
    
    /**
     * Default Constructor for the abstract class AbstractReader
     * @param fileName is the file to be read
     */
    public AbstractReader(String fileName) {
        this.fileName = fileName;
    }
}
