package reader;

public class ChannelReader extends AbstractReader {
    
    public ChannelReader(String fileName) {
        super(fileName);
    }
    
    public List<String> getData() {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        List<String> channelConfigArray = new ArrayList<String>();
        while(br.ready()) {
			String channelConfigLine = br.readLine();
			channelConfigArray.add(channelConfigLine);
        }
        br.close;
        return channelConfigArray;
    }
    
    
