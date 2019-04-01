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

public class Orchestration {

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
			}
		}

		
    }
    
    private void publishEvent(String publisherId, List<AbstractPublisher> listOfPublishers) {
        long id = (long) Integer.parseInt(publisherId);
		for(AbstractPublisher publisher : listOfPublishers) {
			if (id == publisher.getId()) {
				publisher.publish();
			}
		}
    }
    
    private void subToChannel(String subscriberId, String channelName, List<AbstractSubscriber> listOfSubscribers) {
        SubscriptionManager subManager = SubscriptionManager.getInstance();
        long id = (long) Integer.parseInt(subscriberId);
        for (AbstractSubscriber sub : listOfSubscribers) {
            if (sub.getId() == id) {
                subManager.subscribe(channelName, sub);
            }
        }
    }
    
    private void blockUser(String subscriberId, String channelName, List<AbstractSubscriber> listOfSubscribers) {
        ChannelAccessControl channelAccess = ChannelAccessControl.getInstance();
        long id = (long) Integer.parseInt(subscriberId);
        for (AbstractSubscriber sub : listOfSubscribers) {
            if (sub.getId() == id) {
                channelAccess.blockSubscriber(sub, channelName);
            }
        }
    }
    
    private void unBlockUser(String subscriberId, String channelName, List<AbstractSubscriber> listOfSubscribers) {
        ChannelAccessControl channelAccess = ChannelAccessControl.getInstance();
        long id = (long) Integer.parseInt(subscriberId);
        for (AbstractSubscriber sub : listOfSubscribers) {
            if (sub.getId() == id) {
                channelAccess.unBlockSubscriber(sub, channelName);
            }
        }
    }
	
	
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
        }
		return listOfPublishers;
	}
	
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
        }
		return listOfSubscribers;
	}	
}
