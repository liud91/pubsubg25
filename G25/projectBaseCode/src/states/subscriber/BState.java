package states.subscriber;
import events.AbstractEvent;
/**
 * 
 * @author dmaclean, dliu, awebb, jbowman, ikhan
 *
 */
class BState extends AbstractState {
	/**
	 * constructor for BState
	 * @param is the name of the state to be made
	 */
	protected BState(StateName stateName) {
		super(stateName);
	}
	
	public void handleEvent(AbstractEvent event, String channelName) {
	}

}
