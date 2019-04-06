package states.subscriber;
import events.AbstractEvent;
/**
 * 
 * @author dmaclean, dliu, awebb, jbowman, ikhan
 *
 */
class DefaultState extends AbstractState {

	/**
	 * constructor for DefaltState
	 * @param  is the name of the state to be made
	 */
	protected DefaultState(StateName stateName) {
		super(stateName);
	}
	
	public void handleEvent(AbstractEvent event, String channelName) {
        // probably need to do more on this?
        // System.out.println("Handles event at Default State\n");
	}

}
