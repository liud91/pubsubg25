package orchestration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import pubSubServer.AbstractChannel;
import pubSubServer.ChannelDiscovery;
import pubSubServer.SubscriptionManager;
import publishers.AbstractPublisher;
import publishers.PublisherFactory;
import publishers.PublisherType;
import states.subscriber.StateName;
import strategies.publisher.StrategyName;
import subscribers.AbstractSubscriber;
import subscribers.SubscriberFactory;
import subscribers.SubscriberType;
import reader.AbstractReader;
import reader.ChannelReader;
import reader.StatesReader;
import reader.StrategiesReader;

public class Orchestration {

	public static void main(String[] args) {

		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		Orchestration testHarness = new Orchestration();
		
		listOfPublishers = testHarness.createPublishers();
		listOfSubscribers = testHarness.createSubscribers();

		
		List<AbstractChannel> channels = ChannelDiscovery.getInstance().listChannels();
		//For demonstration purposes only
		int subscriberIndex = 0;
		for(AbstractSubscriber subscriber : listOfSubscribers) {
			subscriber.subscribe(channels.get(subscriberIndex%channels.size()).getChannelTopic());
			subscriberIndex++;
		}
		for(AbstractPublisher publisher : listOfPublishers) {
			publisher.publish();
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
