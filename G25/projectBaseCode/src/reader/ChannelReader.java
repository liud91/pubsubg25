package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelReader extends AbstractReader {
    
    public ChannelReader(String fileName) {
        super(fileName);
    }
    
    public List<String> getData() {
    	List<String> channelConfigArray = new ArrayList<String>();
        try {
        	BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			while(br.ready()) {
				String channelConfigLine = br.readLine();
				channelConfigArray.add(channelConfigLine);
			}
	        br.close();
		} catch (IOException e) {
			channelConfigArray = null;
		}

        return channelConfigArray;
    }
}
    
    
