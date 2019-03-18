package states.subscriber;


/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public abstract class AbstractState {
	private StateName stateName;
	
	
	public AbstractEvent(StateName stateName) {
		this.stateName = stateName;
	}
	
	public void handleEvent(AbstractEvent event, String channelName);
	
	protected StateName getStateName() {
		return this.stateName;
	}
	
}
