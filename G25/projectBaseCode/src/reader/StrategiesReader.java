package reader

public class StrategiesReader extends AbstractReader {
    
    public StrategiesReader(String fileName) {
        super(fileName);
    }
    
    public int[][] getData() {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        int i = 0;
        while(br.ready()) {
			String StrategyConfigLine = br.readLine();
			String[] StrategyConfigArray = StrategyConfigLine.split("\t");
			int[][] StrategyConfigIntArray = new int[][2];
			for(int j = 0; j < StrategyConfigArray.length; j++) {
				StrategyConfigIntArray[i][j] = Integer.parseInt(StrategyConfigArray[j]);
            }
           i++; 
        }
        br.close;
        return StrategyConfigIntArray;
    }
}
