package orchestration;

import java.util.ArrayList;
import java.util.List;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.ChannelAccessControl;
import pubSubServer.SubscriptionManager;
import publishers.AbstractPublisher;
import publishers.PublisherFactory;
import publishers.PublisherType;
import reader.ConfigReader;
import reader.StatesReader;
import reader.StrategiesReader;
import states.subscriber.StateName;
import strategies.publisher.StrategyName;
import subscribers.AbstractSubscriber;
import subscribers.SubscriberFactory;
import subscribers.SubscriberType;

/**
 * @author dmaclean, dliu
 */
public class Orchestration {

	/**
	 * Constructor for Orchestration class. This is the class which coordinates and performs the operation of the PubSub entity
	 * @param args
	 */
	public static void main(String[] args) {

		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		Orchestration testHarness = new Orchestration();
		
		listOfPublishers = testHarness.createPublishers();
		listOfSubscribers = testHarness.createSubscribers();

		
		//List<AbstractChannel> channels = ChannelDiscovery.getInstance().listChannels();
		//For demonstration purposes only
		/*int subscriberIndex = 0;
		for(AbstractSubscriber subscriber : listOfSubscribers) {
			subscriber.subscribe(channels.get(subscriberIndex%channels.size()).getChannelTopic());
			subscriberIndex++;
		}
		for(AbstractPublisher publisher : listOfPublishers) {
			publisher.publish();
		}
		*/
		
		testHarness.runConfigFile("Config.cfg", listOfPublishers, listOfSubscribers);
		
            
	}

	/**
	 * reads in the config file which denotes a sequence of publications, (un)blocks, (un)subscribes
	 * @param fileName the file name which contains the events in the specified format per project descriptions
	 * @param listOfPublishers contains all publishers in the system 
	 * @param listOfSubscribers contains all subscribers in the system
	 */
	private void runConfigFile(String fileName, List<AbstractPublisher> listOfPublishers, List<AbstractSubscriber> listOfSubscribers) {
        ConfigReader cr = new ConfigReader(fileName);
		List<List<String>> instructionList = cr.getData();
		for (List<String> instruction : instructionList) {
            switch (instruction.get(0)) {
                case "PUB" :
                    if (instruction.size() == 5) {
                        publishEvent(instruction.get(1), instruction.get(2), instruction.get(3), instruction.get(4), listOfPublishers);
                    } else {
                        publishEvent(instruction.get(1), listOfPublishers);
                    }
                    break;
                case "SUB" :
                    subToChannel(instruction.get(1), instruction.get(2), listOfSubscribers); 
                    break;
                case "BLOCK" :
                    blockUser(instruction.get(1), instruction.get(2), listOfSubscribers);
                    break;
                case "UNBLOCK" :
                    unBlockUser(instruction.get(1), instruction.get(2), listOfSubscribers);
                    break;
            }
        }
	}
	/**
	 * publishes an event to the specified publisher type
	 * @param publisherId the type of publishers which will create the events
	 * @param eventType the type of event to be created
	 * @param header event header
	 * @param payload event message
	 * @param listOfPublishers reference object to list of publishers
	 */
	private void publishEvent(String publisherId, String eventType, String header, String payload, List<AbstractPublisher> listOfPublishers) {

		int id = Integer.parseInt(publisherId);
		EventType type;
		if (eventType.toUpperCase().equals("A")) {
			type = EventType.TypeA;
		} else if (eventType.toUpperCase().equals("B")) {
			type = EventType.TypeB;
		} else {
            type = EventType.TypeC;
		}
		
		EventMessage eventMessage = new EventMessage(header, payload);
		
		AbstractEvent event = EventFactory.createEvent(type, id, eventMessage);
		
		for(AbstractPublisher publisher : listOfPublishers) {
			if (id == publisher.getId()) {
				publisher.publish(event);
				System.out.println("Publisher " + publisher.getId() + " publishes event " + event.getEventId());
			}
		}

		
    }
    /**
     * publishes a default event to the publisher type specified
     * @param publisherId the type of publishers who will create the event
     * @param listOfPublishers reference object to the list of publishers
     */
    private void publishEvent(String publisherId, List<AbstractPublisher> listOfPublishers) {
        long id = (long) Integer.parseInt(publisherId);
		for(AbstractPublisher publisher : listOfPublishers) {
			if (id == publisher.getId()) {
				publisher.publish();
			}
		}
    }
    /**
     * subscribes a subscriber to a channel
     * @param subscriberId the subscriber to be subscribed
     * @param channelName the channel to which the subscriber will be subscribed
     * @param listOfSubscribers a reference object to the list of subscribers
     */
    private void subToChannel(String subscriberId, String channelName, List<AbstractSubscriber> listOfSubscribers) {
        SubscriptionManager subManager = SubscriptionManager.getInstance();
        long id = (long) Integer.parseInt(subscriberId);
        for (AbstractSubscriber sub : listOfSubscribers) {
            if (sub.getId() == id) {
                subManager.subscribe(channelName, sub);
                System.out.println("Subscriber " + sub.getId() + " subscribes to channel " + channelName);
            }
        }
    }
    
    /**
     * blocks a subscriber on a specified channel
     * @param subscriberId the subscriber to be blocked
     * @param channelName the channel on which to block the subscriber
     * @param listOfSubscribers a reference object to the list of subscribers
     */
    private void blockUser(String subscriberId, String channelName, List<AbstractSubscriber> listOfSubscribers) {
        ChannelAccessControl channelAccess = ChannelAccessControl.getInstance();
        long id = (long) Integer.parseInt(subscriberId);
        for (AbstractSubscriber sub : listOfSubscribers) {
            if (sub.getId() == id) {
                channelAccess.blockSubscriber(sub, channelName);
                System.out.println("Subscriber " + sub.getId() + " is blocked on channel " + channelName);
            }
        }
    }
    
    /**
     * unblocks a subscriber on a specified channel
     * @param subscriberId the subscriber to be unblocked
     * @param channelName the channel on which to unblock the subscriber
     * @param listOfSubscribers a reference object to the list of subscribers
     */
    private void unBlockUser(String subscriberId, String channelName, List<AbstractSubscriber> listOfSubscribers) {
        ChannelAccessControl channelAccess = ChannelAccessControl.getInstance();
        long id = (long) Integer.parseInt(subscriberId);
        for (AbstractSubscriber sub : listOfSubscribers) {
            if (sub.getId() == id) {
                channelAccess.unBlockSubscriber(sub, channelName);
                System.out.println("Subscriber " + sub.getId() + " is unblocked on channel " + channelName);
            }
        }
    }
	
	/**
	 * uses reader object to read file specifying which publishers to create, and which strategies they will be associated with
	 * @return the object which contains the list of publishers
	 */
	private List<AbstractPublisher> createPublishers() {
        StrategiesReader sr = new StrategiesReader("Strategies.str");
        List<int[]> strategyData = sr.getData();
        List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		AbstractPublisher newPub;
        for(int i = 0; i < strategyData.size(); i++) {
            newPub = PublisherFactory.createPublisher(
                    PublisherType.values()[strategyData.get(i)[0]],
                    StrategyName.values()[strategyData.get(i)[1]]);
            listOfPublishers.add(newPub);
//            System.out.println("Publisher " + newPub.getId() + " created");
//            System.out.println("Publisher " + newPub.getId() + " has strategy " + StrategyName.values()[strategyData.get(i)[1]]);
            System.out.println("Publisher " + newPub.getId() + " created and has strategy " + StrategyName.values()[strategyData.get(i)[1]]);
        }
        System.out.println("Publishers Created.\n");
        
		return listOfPublishers;
	}
	
	/**
	 * uses reader object to read the file specifying which subscribers to create, and their assoicated states
	 * @return the object which contains the list of subscribers
	 */
	private List<AbstractSubscriber> createSubscribers() {
        StatesReader sr = new StatesReader("States.sts");
        List<int[]> stateData = sr.getData();
        // gotta deal with null list
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		AbstractSubscriber newSub;
        for(int i = 0; i < stateData.size(); i++) {
            newSub = SubscriberFactory.createSubscriber(
                    SubscriberType.values()[stateData.get(i)[0]],
                    StateName.values()[stateData.get(i)[1]]);
            listOfSubscribers.add(newSub);
//            System.out.println("Subscriber " + newSub.getId() + " created");
//            System.out.println("Subscriber " + newSub.getId() + " is on state " + StateName.values()[stateData.get(i)[1]]);
            System.out.println("Subscriber " + newSub.getId() + " created and is on state " + StateName.values()[stateData.get(i)[1]]);        }
        System.out.println("Subscribers Created.\n");
		return listOfSubscribers;
	}	
}
