package strategies.publisher;


class DefaultStrategy extends AbstractStrategy {

	protected DefaultStrategy(StrategyName strategyName) {
		super(strategyName);
	}
	
	public void doPublish(int publisherId) {
        EventMessage message = new EventMessage(defaultHeader, defaultBody);
        AbstractEvent event = EventFactory.createEvent(defaultEventType, publisherId, message);
        ChannelEventDispatcher.getInstance().postEvent(event, ChannelPoolManager.getInstance().listChannels()); 
	}
	
	public void doPublish(AbstractEvent event, int publisherId) {
        ChannelEventDispatcher.getInstance().postEvent(event, ChannelPoolManager.getInstance().listChannels()); 
	}

}
