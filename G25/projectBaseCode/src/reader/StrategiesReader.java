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
public class StrategiesReader extends AbstractReader {
	/**
	 * Constructor for concrete strategies reader
	 * @param fileName is the file to be read
	 */
    public StrategiesReader(String fileName) {
        super(fileName);
    }
    
    /**
     * getter
     * @return the array list of strategies
     */
    public List<int[]> getData() {
        BufferedReader br;
        List<int[]> StrategyConfigIntArray = new ArrayList<int[]>();
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			while(br.ready()) {
				String StrategyConfigLine = br.readLine();
				String[] StrategyConfigArray = StrategyConfigLine.split("\t");
				int[] lineData = new int[StrategyConfigArray.length];
				for(int j = 0; j < StrategyConfigArray.length; j++) {
					lineData[j] = Integer.parseInt(StrategyConfigArray[j]);
				}
				StrategyConfigIntArray.add(lineData);
	        }
			br.close();
		} catch (IOException e) {
			System.out.println("Error reading Strategies file");
			System.exit(1);
		}   
        return StrategyConfigIntArray;
    }
}