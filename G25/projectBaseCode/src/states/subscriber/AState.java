package states.subscriber;
import events.AbstractEvent;

class AState extends AbstractState {

	protected AState(StateName stateName) {
		super(stateName);
	}
	
	public void handleEvent(AbstractEvent event, String channelName) {
        // probably need to do more on this?
        System.out.println("Handles event at A State\n");
	}

}
