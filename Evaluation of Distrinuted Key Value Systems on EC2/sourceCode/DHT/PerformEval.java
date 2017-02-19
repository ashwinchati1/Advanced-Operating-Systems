import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PerformEval {

	private static int myPort;
	private static int numPeer;
	static int requests;
	Socket[] serverSockets;
	static int nodePort[] = new int[16];
	static String nodeIP[] = new String[16];

	static ObjectOutputStream[] outStream;
	static ObjectInputStream[] inStream;

	static Logger logger;
	static FileHandler fileHandler;
	static SimpleFormatter simpleFormatter;
	
	public static void main(String[] args) throws IOException {

		Properties properties = new Properties();
		properties.load(new FileInputStream("config.properties"));
		myPort = Integer.parseInt(properties.getProperty("myPort"));
		numPeer = Integer.parseInt(properties.getProperty("numPeer"));
		requests = Integer.parseInt(properties.getProperty("requests"));
		
		logger = Logger.getLogger("PerformanceTest");
		fileHandler = new FileHandler("PerformanceTest.log");
		logger.addHandler(fileHandler);
		simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
		
		// Port initialization

		nodePort[0] = Integer.parseInt(properties.getProperty("nodePort1"));
		nodePort[1] = Integer.parseInt(properties.getProperty("nodePort2"));
		nodePort[2] = Integer.parseInt(properties.getProperty("nodePort3"));
		nodePort[3] = Integer.parseInt(properties.getProperty("nodePort4"));
		nodePort[4] = Integer.parseInt(properties.getProperty("nodePort5"));
		nodePort[5] = Integer.parseInt(properties.getProperty("nodePort6"));
		nodePort[6] = Integer.parseInt(properties.getProperty("nodePort7"));
		nodePort[7] = Integer.parseInt(properties.getProperty("nodePort8"));
		nodePort[8] = Integer.parseInt(properties.getProperty("nodePort9"));
		nodePort[9] = Integer.parseInt(properties.getProperty("nodePort10"));
		nodePort[10] = Integer.parseInt(properties.getProperty("nodePort11"));
		nodePort[11] = Integer.parseInt(properties.getProperty("nodePort12"));
		nodePort[12] = Integer.parseInt(properties.getProperty("nodePort13"));
		nodePort[13] = Integer.parseInt(properties.getProperty("nodePort14"));
		nodePort[14] = Integer.parseInt(properties.getProperty("nodePort15"));
		nodePort[15] = Integer.parseInt(properties.getProperty("nodePort16"));
		
		
		// IP initialization

		nodeIP[0] = properties.getProperty("nodeIP0");
		nodeIP[1] = properties.getProperty("nodeIP1");
		nodeIP[2] = properties.getProperty("nodeIP2");
		nodeIP[3] = properties.getProperty("nodeIP3");
		nodeIP[4] = properties.getProperty("nodeIP4");
		nodeIP[5] = properties.getProperty("nodeIP5");
		nodeIP[6] = properties.getProperty("nodeIP6");
		nodeIP[7] = properties.getProperty("nodeIP7");
		nodeIP[8] = properties.getProperty("nodeIP8");
		nodeIP[9] = properties.getProperty("nodeIP9");
		nodeIP[10] = properties.getProperty("nodeIP10");
		nodeIP[11] = properties.getProperty("nodeIP11");
		nodeIP[12] = properties.getProperty("nodeIP12");
		nodeIP[13] = properties.getProperty("nodeIP13");
		nodeIP[14] = properties.getProperty("nodeIP14");
		nodeIP[15] = properties.getProperty("nodeIP15");
		
		boolean serversConnected = false;

		Scanner scanner = new Scanner(System.in);

		PerformEval per = new PerformEval();

	
		if (!serversConnected) {
			serversConnected = true;
			per.connectToServers();
		}
		
		
			per.putValue();
		
			per.getValue();
		
			per.deleteValue();
				
		
		
	}
	
	

	public void connectToServers() {
		serverSockets = new Socket[16];
		outStream = new ObjectOutputStream[16];
		inStream = new ObjectInputStream[16];
		try {
			for (int i = 0; i < 16; i++) {
				serverSockets[i] = new Socket(nodeIP[i], nodePort[i]);
				outStream[i] = new ObjectOutputStream(
						serverSockets[i].getOutputStream());
				inStream[i] = new ObjectInputStream(
						serverSockets[i].getInputStream());
			}

		} catch (IOException e) {
			
		//	e.printStackTrace();
		}

	}

	public static void putValue() {
		logger.info("Put test started");
		int i = 0;

		long begintime = System.currentTimeMillis();
		
		for (i = 0; i <= requests; i++) {

			String key = "a" + i;

			String putvalue = ("p" + "_" + key + "_" + "sadjhajdasbdsahdhasdhashdsahdsahdiuahdosauhdisuahieuwhroiuwehruehf");

			int hash = myHashFunction(key);

			try {

				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();

				
				try {

					String response = (String) inStream[hash].readObject();

					
				} catch (ClassNotFoundException e) {
					
			//		e.printStackTrace();
				}

			} catch (IOException e) {
				
			//	e.printStackTrace();
			}

						
		}
	
		logger.info("Time Take for" +requests+  "Put :"+(System.currentTimeMillis() - begintime));
		
	}

	
	public void getValue() {
		logger.info("Get test started");
		int i = 0;
		
		long begintime = System.currentTimeMillis();

		for (i = 1; i <= requests; i++) {

			String key = "a" + i;

			String putvalue = ("g" + "_" + key);

			int hash = myHashFunction(key);

			try {

				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();

				String value = (String) inStream[hash].readObject();

				
			} catch (IOException e) {
				
		//		e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
		//		e.printStackTrace();
			}

			
			
		}

		logger.info("Time Take for" +requests+  "get :"+(System.currentTimeMillis() - begintime));
		
	}

	public void deleteValue() {
		logger.info("Delete test started");

		int i = 0;
		
		long begintime = System.currentTimeMillis();
		
		for (i = 1; i <= requests; i++) {

			String key = "a" + i;

			String putvalue = ("d" + "_" + key);

			int hash = myHashFunction(key);

			
			try {

				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();

				
				try {

					String response = (String) inStream[hash].readObject();

				
				} catch (ClassNotFoundException e) {
					
			//		e.printStackTrace();
				}

			} catch (IOException e) {
				
			//	e.printStackTrace();
			}
			
		}

		logger.info("Time Take for" +requests+  "delete :"+(System.currentTimeMillis() - begintime));
		
		
	}

	public static int myHashFunction(String key) {

		String str = key;
		char[] charArray = str.toCharArray();

		int sum = 0;

		for (int i = 0; i < charArray.length; i++) {

			sum = sum + charArray[i];

		}

		return sum % numPeer;

	}

}
