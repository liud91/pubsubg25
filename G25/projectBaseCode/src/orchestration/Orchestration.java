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
		try {
			listOfPublishers = testHarness.createPublishers();
			listOfSubscribers = testHarness.createSubscribers();

			
			List<AbstractChannel> channels = ChannelDiscovery.getInstance().listChannels();
			//For demonstration purposes only
			try {
			BufferedReader initialChannels = new BufferedReader(new FileReader(new File("Channels.chl")));
			List<String> channelList = new ArrayList<String>();
			String line = "";
			while((line = initialChannels.readLine()) != null )
				channelList.add(line);
			int subscriberIndex = 0;
			for(AbstractSubscriber subscriber : listOfSubscribers) {
				subscriber.subscribe(channelList.get(subscriberIndex%channelList.size()));
				subscriberIndex++;
			}
			initialChannels.close();
			}catch(IOException ioe) {
				System.out.println("Loading Channels from file failed proceeding with random selection");
				for(AbstractSubscriber subscriber : listOfSubscribers) {
					//int index = (int) Math.round((Math.random()*10))/3;
					int index = 0;
					SubscriptionManager.getInstance().subscribe(channels.get(index).getChannelTopic(), subscriber);
				}
			}
			for(AbstractPublisher publisher : listOfPublishers) {
				publisher.publish();
			}
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.out.println("Will now terminate");
			return;
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
