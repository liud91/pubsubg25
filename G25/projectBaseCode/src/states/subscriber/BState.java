package states.subscriber;
import events.AbstractEvent;
/**
 * 
 * @author dmaclean, dliu
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
        // probably need to do more on this?
        // System.out.println("Handles event at B State\n");
	}

}
