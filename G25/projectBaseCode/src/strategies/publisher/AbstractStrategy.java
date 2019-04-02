package strategies.publisher;
import events.EventType;
import events.AbstractEvent;

/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public abstract class AbstractStrategy {
	private StrategyName strategyName;
	protected EventType defaultEventType = EventType.TypeC;
	protected String defaultHeader = "Default Header";
	protected String defaultBody = "Default Body";
	
	/**
	 * constructor for AbstractStrategy
	 * @param strategyName is the name of the publisher's associated strategy
	 */
	public AbstractStrategy(StrategyName strategyName) {
		this.strategyName = strategyName;
	}
	
	/**
	 * getter for strategy 
	 * @return this publisher's strategy name
	 */
	protected StrategyName getStrategyName() {
		return this.strategyName;
	}
	
	/**
	 * publishes an event using the publisher ID
	 * @param event is being published
	 * @param publisherId is the publisher's ID with which the event will be associated
	 */
	public abstract void doPublish(AbstractEvent event, int publisherId);
	
	/**
	 * publishers a default event using the publisher's ID
	 * @param publisherId publisherId is the publisher's ID with which the event will be associated
	 */
	public abstract void doPublish(int publisherId);
	
}



