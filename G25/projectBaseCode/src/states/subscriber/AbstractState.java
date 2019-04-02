package states.subscriber;
import events.AbstractEvent;

/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public abstract class AbstractState {
	private StateName stateName;
	
	/**
	 * constructor for AbstractState
	 * @param is the name of the state to be made
	 */
	public AbstractState(StateName stateName) {
		this.stateName = stateName;
	}
	
	/**
	 * allows a subscriber to handle an event via it's associated state
	 * @param event is the event to be handled
	 * @param channelName is the channel to which the event is being posted
	 */
	public abstract void handleEvent(AbstractEvent event, String channelName);
	
	/**
	 * getter for state name
	 * @return state name
	 */
	public StateName getStateName() {
		return this.stateName;
	}
	
}
