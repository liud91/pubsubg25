package states.subscriber;
import events.AbstractEvent;
/**
 * 
 * @author dmaclean, dliu, awebb, jbowman, ikhan
 *
 */
class AState extends AbstractState {
	/**
	 * constructor for AState
	 * @param is the name of the state to be made
	 */
	protected AState(StateName stateName) {
		super(stateName);
	}
	
	/**
	 * allows a subscriber to handle an event via it's associated state
	 * @param event is the event to be handled
	 * @param channelName is the channel to which the event is being posted
	 */
	public void handleEvent(AbstractEvent event, String channelName, long subId) {
        System.out.println("Subscriber " + subId + " receives event " + event.getEventId() + " and handles it at state A");
	}

}
