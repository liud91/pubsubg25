package subscribers;


/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public class SubscriberIDMaker {

	private static long subUIDs = 0L;
	
	/**
	 * 
	 * @return the next number of type {@link long}in the series of UID for the AbstractEvent derived classes
	 */
	protected static long getNewSubID() {
		return subUIDs++;
	}
	
}
