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

	
	private List<AbstractPublisher> createPublishers() throws IOException{
        StrategyReader sr = new StrategyReader("Strategies.str");
        int[][] strategyData = sr.getData();
		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		AbstractPublisher newPub;
        for(int i = 0; i < strategyData.length; i++) {
            newPub = PublisherFactory.createPublisher(
                    PublisherType.values()[Integer.parseInt(strategyReader[i][0])],
                    StrategyName.values()[Integer.parseInt(strategyReader[i][1])]);
            listOfPublishers.add(newPub);
        }
		StrategyBufferedReader.close();
		return listOfPublishers;
	}
	
	private List<AbstractSubscriber> createSubscribers() throws IOException{
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		AbstractSubscriber newSub;
		BufferedReader StateBufferedReader = new BufferedReader(new FileReader(new File("States.sts")));
		while(StateBufferedReader.ready()) {
			String StateConfigLine = StateBufferedReader.readLine();
			String[] StateConfigArray = StateConfigLine.split("\t");
			int[] StateConfigIntArray = new int[2];
			for(int i = 0; i < StateConfigArray.length; i++) {
				StateConfigIntArray[i] = Integer.parseInt(StateConfigArray[i]);
			}
			newSub = SubscriberFactory.createSubscriber(
					SubscriberType.values()[StateConfigIntArray[0]], 
					StateName.values()[StateConfigIntArray[1]]);
			listOfSubscribers.add(newSub);
		}
		StateBufferedReader.close();
		return listOfSubscribers;
	}
	
	
	
}
