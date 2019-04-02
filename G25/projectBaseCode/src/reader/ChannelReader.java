package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Channel Reader class
 * @author dmaclean, dliu
 *
 */
public class ChannelReader extends AbstractReader {
    
	/**
	 * Constructor for concrete channel reader
	 * @param fileName is the file to be read
	 */
    public ChannelReader(String fileName) {
        super(fileName);
    }
    
    /**
     * getter
     * @return the array list of channels
     */
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
			System.out.println("Error reading Channels file.");
			System.exit(1);
		}

        return channelConfigArray;
    }
}
    
    
