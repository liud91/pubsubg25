package publishers;


/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public class PublisherIDMaker {

	private static long publisherUIDs = 0L;
	
	/**
	 * 
	 * @return the next number of type {@link long}in the series of UID for the AbstractPublisher derived classes
	 */
	protected static long getNewPublisherID() {
		return publisherUIDs++;
	}
	
}
