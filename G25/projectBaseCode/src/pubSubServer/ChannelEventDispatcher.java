package pubSubServer;

import java.util.List;

import events.AbstractEvent;
import publishers.AbstractPublisher;


/**
 * @author kkontog, ktsiouni, mgrigori
 * MUST IMPLEMENT the Singleton design pattern
 * Class providing an interface for {@link AbstractPublisher} objects to cover their publishing needs 
 */
public class ChannelEventDispatcher {
    private static ChannelEventDispatcher instance = null;
	private ChannelDiscovery channelDiscovery;
	
	private ChannelEventDispatcher() {
        channelDiscovery = ChannelDiscovery.getInstance();
	}
	
	public static ChannelEventDispatcher getInstance() {
		if(instance == null) {
            instance = new ChannelEventDispatcher();
        }
        return instance;
	}

	
	
	/**
	 * @param event event to be published
	 * @param listOfChannels list of channel names to which the event must be published to 
	 */
	public void postEvent(AbstractEvent event, List<String> listOfChannels) {
		
		for(String channelName : listOfChannels) {
			AbstractChannel channel = channelDiscovery.findChannel(channelName);
			if(channel == null) {
				channel = ChannelCreator.getInstance().addChannel(channelName);
			}
			
			
			System.out.println("Channel " + channelName + " has event " + event.getEventId() + " from publisher " + event.getEventPublisher());
			channel.publishEvent(event);
		}
	}
	
	
}
