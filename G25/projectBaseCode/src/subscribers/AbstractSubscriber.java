package subscribers;

import baseEntities.IEntity;
import events.AbstractEvent;
import states.subscriber.AbstractState;
import states.subscriber.StateFactory;
import states.subscriber.StateName;


/**
 * @author kkontog, ktsiouni, mgrigori
 * the base Interface for the Subscriber hierarchy
 */
public abstract class AbstractSubscriber implements IEntity {
	
	private long id;
	protected AbstractState state;

	/**
	 * set's the {@link IState} for this ISubscriber implementation using the {@link StateFactory}
	 * @param stateName the entry from the {@link StateName} enumeration that we want the new IState of the ISubscriber to be 
	 */
	 
    public AbstractSubscriber(long id) {
        this.id = id;
    }
    
	public void setState(StateName stateName) {};

	
	/**
	 * is the function called each time an event is published to one of the channels that the 
	 * ISubscriber is subscribed to
	 * @param event the AbstractEvent that's received
	 * @param channelName the name of the channel that sent the AbstractEvent to the ISubscriber
	 */
	
	public void alert(AbstractEvent event, String channelName) {
		System.out.println("Subscriber " + id + " receives event " + event.getEventId() + " and handles it at state " + state.getStateName());
		state.handleEvent(event, channelName);
		int random = (int) (Math.random() * 2);
		StateName nextState StateName.values()[random];
		setState(nextState);
	}
	
	
	/**
	 * subscribes to the channel whose name is provided by the parameter channelName 
	 * @param channelName type String
	 */
	public void subscribe(String channelName) {};
	
	
	/**
	 * unsubscribes from the channel whose name is provided by the parameter channelName
	 * @param channelName type String
	 */
	public void unsubscribe(String channelName) {};
	
	public long getId() {
        return id;
    }
	
}
