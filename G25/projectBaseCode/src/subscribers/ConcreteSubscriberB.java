package subscribers;

import pubSubServer.SubscriptionManager;
import states.subscriber.StateFactory;
import states.subscriber.StateName;


/**
 * @author kkontog, ktsiouni, mgrigori
 * an example concrete subscriber
 */
class ConcreteSubscriberB extends AbstractSubscriber {

	/**
	 * constructor for ConcreteSubscriberB
	 * @param id is the publisher's ID
	 */
	protected ConcreteSubscriberB(long id) {
        super(id); 
		state = StateFactory.createState(StateName.defaultState);
	}
	
	/* (non-Javadoc)
	 * @see subscribers.AbstractSubscriber#setState(states.subscriber.StateName)
	 */
	public void setState(StateName stateName) {
		state = StateFactory.createState(stateName);
	}
	
	
	/* (non-Javadoc)
	 * @see subscribers.AbstractSubscriber#alert(events.AbstractEvent, java.lang.String)
	 */

	@Override
	public void subscribe(String channelName) {
		SubscriptionManager.getInstance().subscribe(channelName, this);		
	}

	/* (non-Javadoc)
	 * @see subscribers.AbstractSubscriber#unsubscribe(java.lang.String)
	 */
	@Override
	public void unsubscribe(String channelName) {
		SubscriptionManager.getInstance().subscribe(channelName, this);
		
	}
	
	
}
