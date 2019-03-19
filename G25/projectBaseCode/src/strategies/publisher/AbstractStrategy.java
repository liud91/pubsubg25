package strategies.publisher;
import events.EventFactory;
import pubSubServer.ChannelEventDispatcher;
import pubSubServer.ChannelPoolManager;
import events.EventMessage;
import events.EventType;

/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public abstract class AbstractStrategy {
	private StrategyName strategyName;
	protected EventType defaultEventType = EventType.TypeC;
	protected String defaultHeader = "Default Header";
	protected String defaultBody = "Default Body";
	
	
	public AbstractStrategy(StrategyName strategyName) {
		this.strategyName = strategyName;
	}
	
	protected StrategyName getStrategyName() {
		return this.strategyName;
	}
	
	public abstract void doPublish(AbstractEvent event, int publisherId);
	
	public abstract void doPublish(int publisherId);
	
}



