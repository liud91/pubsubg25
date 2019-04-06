package publishers;

import events.AbstractEvent;
import strategies.publisher.AbstractStrategy;
import strategies.publisher.StrategyFactory;


/**
 * @author kkontog, ktsiouni, mgrigori
 * 
 * the WeatherPublisher class is an example of a ConcretePublisher 
 * implementing the IPublisher interface. Of course the publish 
 * methods could have far more interesting logics a
 */
public class ConcretePublisherG extends AbstractPublisher {

	
	
	
	
	/**
	 * @param concreteStrategy attaches a concreteStrategy generated from the {@link StrategyFactory#createStrategy(strategies.publisher.StrategyName)}
	 * method
	 */
	protected ConcretePublisherG(AbstractStrategy concreteStrategy, long publisherId) {
		this.publishingStrategy = concreteStrategy;
		this.id = publisherId;
	}

	/* (non-Javadoc)
	 * @see publishers.AbstractPublisher#publish(events.AbstractEvent)
	 */
	@Override
	public void publish(AbstractEvent event) {
		publishingStrategy.doPublish(event, (int) id);
	}

	/* (non-Javadoc)
	 * @see publishers.AbstractPublisher#publish()
	 */
	@Override
	public void publish() {
		publishingStrategy.doPublish((int) id);
	}

}
