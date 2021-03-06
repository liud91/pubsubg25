package publishers;

import strategies.publisher.StrategyFactory;
import strategies.publisher.StrategyName;


/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link AbstractPublisher} objects
 * contributes to the Strategy design pattern
 * implements the FactoryMethod design pattern   
 */
public class PublisherFactory {

	
	/**
	 * This is an implementation of the Factory Method design pattern
	 * Creates an instance of any of the classes implementing the top level Interface IPublisher
	 * 
	 * note we have multiple entries that return instances of the same ConcretePublisher class
	 * 
	 * @param publisherType an entry from the {@link PublisherType} enumeration
	 * @param strategyName an entry from the {@link StrategyName} enumeration
	 * @return an instance of the specified IPublisher implementation with the specified strategyName attached to it
	 */
	public static AbstractPublisher createPublisher(PublisherType publisherType, StrategyName strategyName) {
		AbstractPublisher ip;
		long id = PublisherIDMaker.getNewPublisherID();
		switch (publisherType) {
			case alphaPub : 
				ip = new ConcretePublisherA(StrategyFactory.createStrategy(strategyName), id);
				return ip;
			case betaPub : 
				ip = new ConcretePublisherB(StrategyFactory.createStrategy(strategyName), id);
				return ip;
			case gammaPub : 
				ip = new ConcretePublisherG(StrategyFactory.createStrategy(strategyName), id);
				return ip;
			default : 
				ip = new ConcretePublisherD(StrategyFactory.createStrategy(strategyName), id);
				return ip;
		}
	}
	
}
