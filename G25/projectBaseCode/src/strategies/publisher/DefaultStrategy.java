package strategies.publisher;
import events.EventFactory;
import pubSubServer.ChannelEventDispatcher;
import events.EventMessage;
import events.AbstractEvent;

/**
 * default concrete implementation of AbstractStrategy
 * @author dmaclean, dliu, awebb, jbowman, ikhan
 *
 */
class DefaultStrategy extends AbstractStrategy {

	/**
	 * constructor for DefaulttStrategy
	 * @param strategyName is the name of the publisher's associated strategy
	 */
	protected DefaultStrategy(StrategyName strategyName) {
		super(strategyName, 2);
	}
	
	/**
	 * publishers a default event using the publisher's ID
	 * @param publisherId publisherId is the publisher's ID with which the event will be associated
	 */
	public void doPublish(int publisherId) {
        EventMessage message = new EventMessage(defaultHeader, defaultBody);
        AbstractEvent event = EventFactory.createEvent(defaultEventType, publisherId, message);
        System.out.println("Publisher " + publisherId + " publishes event " + event.getEventId() + " with header \"" + event.getPayload().getHeader() + "\" and payload \"" + event.getPayload().getBody() + "\"");
        ChannelEventDispatcher.getInstance().postEvent(event, getValidChannels()); 
	}
	
	/**
	 * publishes an event using the publisher ID
	 * @param event is being published
	 * @param publisherId is the publisher's ID with which the event will be associated
	 */
	public void doPublish(AbstractEvent event, int publisherId) {
        System.out.println("Publisher " + publisherId + " publishes event " + event.getEventId() +  " with header \"" + event.getPayload().getHeader() + "\" and payload \"" + event.getPayload().getBody() + "\"");
        ChannelEventDispatcher.getInstance().postEvent(event, getValidChannels()); 
	}

}
