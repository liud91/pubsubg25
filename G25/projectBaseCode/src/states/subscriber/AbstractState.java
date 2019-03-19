package states.subscriber;
import events.AbstractEvent;

/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public abstract class AbstractState {
	private StateName stateName;
	
	
	public AbstractState(StateName stateName) {
		this.stateName = stateName;
	}
	
	public abstract void handleEvent(AbstractEvent event, String channelName);
	
	protected StateName getStateName() {
		return this.stateName;
	}
	
}
