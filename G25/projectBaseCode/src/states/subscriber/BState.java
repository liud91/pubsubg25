package states.subscriber;
import events.AbstractEvent;

class BState extends AbstractState {

	protected BState(StateName stateName) {
		super(stateName);
	}
	
	public void handleEvent(AbstractEvent event, String channelName) {
        // probably need to do more on this?
        System.out.println("Handles event at B State\n");
	}

}
