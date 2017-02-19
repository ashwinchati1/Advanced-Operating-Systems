import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class PerformEval {

	private static int myPort;
	private static int numPeer;
	Socket[] serverSockets;
	static int nodePort[] = new int[8];
	static String nodeIP[] = new String[8];

	static ObjectOutputStream[] outStream;
	static ObjectInputStream[] inStream;

	public static void main(String[] args) throws IOException {

		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		myPort = Integer.parseInt(props.getProperty("myPort"));
		numPeer = Integer.parseInt(props.getProperty("numPeer"));
		
		// Port initialization

		nodePort[0] = Integer.parseInt(props.getProperty("nodePort1"));
		nodePort[1] = Integer.parseInt(props.getProperty("nodePort2"));
		nodePort[2] = Integer.parseInt(props.getProperty("nodePort3"));
		nodePort[3] = Integer.parseInt(props.getProperty("nodePort4"));
		nodePort[4] = Integer.parseInt(props.getProperty("nodePort5"));
		nodePort[5] = Integer.parseInt(props.getProperty("nodePort6"));
		nodePort[6] = Integer.parseInt(props.getProperty("nodePort7"));
		nodePort[7] = Integer.parseInt(props.getProperty("nodePort8"));

		// IP initialization

		nodeIP[0] = props.getProperty("nodeIP1");
		nodeIP[1] = props.getProperty("nodeIP2");
		nodeIP[2] = props.getProperty("nodeIP3");
		nodeIP[3] = props.getProperty("nodeIP4");
		nodeIP[4] = props.getProperty("nodeIP5");
		nodeIP[5] = props.getProperty("nodeIP6");
		nodeIP[6] = props.getProperty("nodeIP7");
		nodeIP[7] = props.getProperty("nodeIP8");

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
		serverSockets = new Socket[8];
		outStream = new ObjectOutputStream[8];
		inStream = new ObjectInputStream[8];
		try {
			for (int i = 0; i < 8; i++) {
				serverSockets[i] = new Socket(nodeIP[i], nodePort[i]);
				outStream[i] = new ObjectOutputStream(
						serverSockets[i].getOutputStream());
				inStream[i] = new ObjectInputStream(
						serverSockets[i].getInputStream());
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public static void putValue() {
		System.out.println("Put test started");
		int i = 0;

		long begintime = System.currentTimeMillis();
		
		for (i = 0; i <= 100000; i++) {

			String key = "a" + i;

			String putvalue = ("p" + "_" + key + "_" + "sadjhajdasbdsahdhasdhashdsahdsahdiuahdosauhdisuahieuwhroiuwehruehf");

			int hash = myHashFunction(key);

			try {

				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();

				
				try {

					String response = (String) inStream[hash].readObject();

					
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}

			} catch (IOException e) {
				
				e.printStackTrace();
			}

						
		}
	
		System.out.println("Time Take for 100000  Put :"+(System.currentTimeMillis() - begintime));
		
	}

	
	public void getValue() {
		System.out.println("Get test started");
		int i = 0;
		
		long begintime = System.currentTimeMillis();

		for (i = 1; i <= 100000; i++) {

			String key = "a" + i;

			String putvalue = ("g" + "_" + key);

			int hash = myHashFunction(key);

			try {

				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();

				String value = (String) inStream[hash].readObject();

				
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}

			
			
		}

		System.out.println("Time Take for 100000  get :"+(System.currentTimeMillis() - begintime));
		
	}

	public void deleteValue() {
		System.out.println("Delete test started");

		int i = 0;
		
		long begintime = System.currentTimeMillis();
		
		for (i = 1; i <= 100000; i++) {

			String key = "a" + i;

			String putvalue = ("d" + "_" + key);

			int hash = myHashFunction(key);

			
			try {

				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();

				
				try {

					String response = (String) inStream[hash].readObject();

				
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}

			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}

		System.out.println("Time Take for 100000 k delete :"+(System.currentTimeMillis() - begintime));
		
		
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
