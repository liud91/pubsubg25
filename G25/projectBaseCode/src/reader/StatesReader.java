package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatesReader extends AbstractReader {
    
    public StatesReader(String fileName) {
        super(fileName);
    }
    
    public List<int[]> getData() {
        BufferedReader br;
        List<int[]> StateConfigIntArray = new ArrayList<int[]>();
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			while(br.ready()) {
				String StateConfigLine = br.readLine();
				String[] StateConfigArray = StateConfigLine.split("\t");
				int[] lineData = new int[StateConfigArray.length];
				for(int j = 0; j < StateConfigArray.length; j++) {
					lineData[j] = Integer.parseInt(StateConfigArray[j]);
				}
				StateConfigIntArray.add(lineData);
	        }
			br.close();
		} catch (IOException e) {
			System.out.println("Error reading States file.");
			System.exit(1);
		}   
        return StateConfigIntArray;
    }
}
