package states.subscriber;
import events.AbstractEvent;

class DefaultState extends AbstractState {

	protected DefaultState(StateName stateName) {
		super(stateName);
	}
	
	public void handleEvent(AbstractEvent event, String channelName) {
        // probably need to do more on this?
        // System.out.println("Handles event at Default State\n");
	}

}
