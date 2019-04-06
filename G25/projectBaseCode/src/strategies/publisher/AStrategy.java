package strategies.publisher;
import events.EventFactory;
import pubSubServer.ChannelEventDispatcher;
import pubSubServer.ChannelDiscovery;
import events.EventMessage;
import events.AbstractEvent;


class AStrategy extends AbstractStrategy {

	/**
	 * constructor for AStrategy
	 * @param strategyName is the name of the publisher's associated strategy
	 */
	protected AStrategy(StrategyName strategyName) {
		super(strategyName, 0);
	}
	
	/**
	 * publishers a default event using the publisher's ID
	 * @param publisherId publisherId is the publisher's ID with which the event will be associated
	 */
	public void doPublish(int publisherId) {
        EventMessage message = new EventMessage(defaultHeader, defaultBody);
        AbstractEvent event = EventFactory.createEvent(defaultEventType, publisherId, message);
        ChannelEventDispatcher.getInstance().postEvent(event, getValidChannels()); 
	}
	
	/**
	 * publishes an event using the publisher ID
	 * @param event is being published
	 * @param publisherId is the publisher's ID with which the event will be associated
	 */
	public void doPublish(AbstractEvent event, int publisherId) {
        ChannelEventDispatcher.getInstance().postEvent(event, getValidChannels()); 
	}

}
