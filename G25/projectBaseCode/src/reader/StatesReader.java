package reader

public class StatesReader extends AbstractReader {
    
    public StatesReader(String fileName) {
        super(fileName);
    }
    
    public int[][] getData() {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        int i = 0;
        while(br.ready()) {
			String StateConfigLine = StateBufferedReader.readLine();
			String[] StateConfigArray = StateConfigLine.split("\t");
			int[][] StateConfigIntArray = new int[][2];
			for(int j = 0; j < StateConfigArray.length; j++) {
				StateConfigIntArray[i][j] = Integer.parseInt(StateConfigArray[j]);
			}
			i++;
        }
        br.close;
        return StateConfigIntArray;
    }
}
