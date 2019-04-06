package strategies.publisher;
import java.util.ArrayList;
import java.util.List;

import events.AbstractEvent;
import events.EventType;
import pubSubServer.ChannelDiscovery;

/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public abstract class AbstractStrategy {
	private StrategyName strategyName;
	private int strategyDefinition;
	private int numberOfStrategies;
	protected EventType defaultEventType = EventType.TypeC;
	protected String defaultHeader = "Default Header";
	protected String defaultBody = "Default Body";
	
	/**
	 * constructor for AbstractStrategy
	 * @param strategyName is the name of the publisher's associated strategy
	 */
	public AbstractStrategy(StrategyName strategyName, int strategyDefinition) {
		this.strategyName = strategyName;
		this.strategyDefinition = strategyDefinition;
		numberOfStrategies = 3;
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
	
	public List<String> getValidChannels() {
        List<String> channels = ChannelDiscovery.getInstance().listChannelNames();
        List<String> validChannels = new ArrayList<>();
        for (String channel : channels) {
            if ((channel.length() % numberOfStrategies) == strategyDefinition) {
                validChannels.add(channel);
            }
        }
        return validChannels;
    }
	
	
}



