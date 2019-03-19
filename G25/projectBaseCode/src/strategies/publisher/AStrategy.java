package strategies.publisher;
import events.EventFactory;
import pubSubServer.ChannelEventDispatcher;
import pubSubServer.ChannelPoolManager;
import events.EventMessage;
import events.AbstractEvent;


class AStrategy extends AbstractStrategy {

	protected AStrategy(StrategyName strategyName) {
		super(strategyName);
	}
	
	public void doPublish(int publisherId) {
        EventMessage message = new EventMessage(defaultHeader, defaultBody);
        AbstractEvent event = EventFactory.createEvent(defaultEventType, publisherId, message);
        ChannelEventDispatcher.getInstance().postEvent(event, ChannelPoolManager.getInstance().listChannels()); 
	}
	
	public void doPublish(AbstractEvent event, int publisherId) {
        ChannelEventDispatcher.getInstance().postEvent(event, ChannelPoolManager.getInstance().listChannels()); 
	}

}
