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
	private EventType defaultEventType = EventType.TypeC;
	private String defaultHeader = "Default Header";
	private String defaultBody = "Default Body";
	
	
	public AbstractStrategy(StrategyName strategyName) {
		this.strategyName = strategyName;
	}
	
	protected StrategyName getStrategyName() {
		return this.strategyName;
	}
	
	public void doPublish(AbstractEvent event, int publisherId);
	
	public void doPublish(int publisherId);
	
}



