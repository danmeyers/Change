package image;

import java.io.IOException;

public class Main {

	
	
	public static void main(String[] args){
		Options options = new Options();
		if(!options.setTimes()) return;
		try {
			options.chooseFiles();
		} catch (IOException e) {
			System.err.println("IO Error");
			return;
		} catch (CancelException e) {
			System.out.println("Program cancelled");
			return;
		}
		
		Framework framework = new Framework(options);
		framework.run();
		framework.shutdown();

	}

}
