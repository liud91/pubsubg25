package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader extends AbstractReader {
    
    public ConfigReader(String fileName) {
        super(fileName);
    }
    
    public List<List<String>> getData() {
        List<List<String>> instructionList = new ArrayList<>();
        try {
        	BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			while(br.ready()) {
				List<String> instructionLine = br.readLine().split(" ");
				instructionList.add(instructionLine);
			}
	        br.close();
		} catch (IOException e) {
			instructionList = null;
		}

        return instructionList;
}
