import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;


public class Peer {

	private static int myPort;
	
	private static int numPeer;
	
	Socket[] serverSockets;
	static int nodePort[] = new int[16];
	static String nodeIP[] = new String[16];
	
	static ObjectOutputStream[] outStream;
	static ObjectInputStream[] inStream;
	
	public static void main(String[] args) throws IOException {
	
		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		myPort = Integer.parseInt(props.getProperty("myPort"));
		numPeer = Integer.parseInt(props.getProperty("numPeer"));
		
		//Port initialization
		
		// Port initialization

		nodePort[0] = Integer.parseInt(props.getProperty("nodePort0"));
		nodePort[1] = Integer.parseInt(props.getProperty("nodePort2"));
		nodePort[2] = Integer.parseInt(props.getProperty("nodePort3"));
		nodePort[3] = Integer.parseInt(props.getProperty("nodePort4"));
		nodePort[4] = Integer.parseInt(props.getProperty("nodePort5"));
		nodePort[5] = Integer.parseInt(props.getProperty("nodePort6"));
		nodePort[6] = Integer.parseInt(props.getProperty("nodePort7"));
		nodePort[7] = Integer.parseInt(props.getProperty("nodePort8"));
		nodePort[8] = Integer.parseInt(props.getProperty("nodePort9"));
		nodePort[9] = Integer.parseInt(props.getProperty("nodePort10"));
		nodePort[10] = Integer.parseInt(props.getProperty("nodePort11"));
		nodePort[11] = Integer.parseInt(props.getProperty("nodePort12"));
		nodePort[12] = Integer.parseInt(props.getProperty("nodePort13"));
		nodePort[13] = Integer.parseInt(props.getProperty("nodePort14"));
		nodePort[14] = Integer.parseInt(props.getProperty("nodePort15"));
		nodePort[15] = Integer.parseInt(props.getProperty("nodePort16"));
		
		// IP initialization

		nodeIP[0] = props.getProperty("nodeIP1");
		nodeIP[1] = props.getProperty("nodeIP2");
		nodeIP[2] = props.getProperty("nodeIP3");
		nodeIP[3] = props.getProperty("nodeIP4");
		nodeIP[4] = props.getProperty("nodeIP5");
		nodeIP[5] = props.getProperty("nodeIP6");
		nodeIP[6] = props.getProperty("nodeIP7");
		nodeIP[7] = props.getProperty("nodeIP8");
		nodeIP[8] = props.getProperty("nodeIP9");
		nodeIP[9] = props.getProperty("nodeIP10");
		nodeIP[10] = props.getProperty("nodeIP11");
		nodeIP[11] = props.getProperty("nodeIP12");
		nodeIP[12] = props.getProperty("nodeIP13");
		nodeIP[13] = props.getProperty("nodeIP14");
		nodeIP[14] = props.getProperty("nodeIP15");
		nodeIP[15] = props.getProperty("nodeIP16");
		
				
		
		boolean serversConnected = false; 
		
		Scanner scanner = new Scanner(System.in);
		
		Peer peer = new Peer();
		
		
		try {
			
			Thread t = new Thread(new PeerServer(myPort));
			t.start();
		
			do {
				System.out.println("*****************************************\n");
				System.out.println("\t\tMenu\n");
				System.out.println("*****************************************\n");
				System.out.println("Enter the digit for the opration to perform\n");
				System.out.println("1.Put\n");
				System.out.println("2.Get\n");
				System.out.println("3.Delete\n");
				System.out.println("4.Exit");
				System.out.println("*****************************************\n");

				System.out.println("Enter Your Choice\n");				
				String in = scanner.nextLine();
				int input = Integer.parseInt(in);

				if (!serversConnected){
					serversConnected = true;
					peer.connectToServers();
				}

				switch (input) {

				case 1:
					peer.putValue();
					break;

				case 2:
					peer.getValue();
					break;

				case 3:
					peer.deleteValue();
					break;
					
				case 4:
					System.exit(0);
					break;

				default:
					System.out.println("Invalid Option\n");
					break;

				}
			} while (true);
		} catch (Exception uhEx) {
			System.out.println("\nHost ID not found!\n");
		//	uhEx.printStackTrace();
		} catch (ExceptionInInitializerError Ex) {
		//	Ex.printStackTrace();
		} finally {
			scanner.close();
		}
		
	}
	
	public void connectToServers() {
		serverSockets = new Socket[8];
		outStream = new ObjectOutputStream[8];
		inStream = new ObjectInputStream[8];
		try {
			for (int i = 0; i < numPeer; i++){
				serverSockets[i] = new Socket(nodeIP[i], nodePort[i]);
				outStream[i] = new ObjectOutputStream(serverSockets[i].getOutputStream());
				inStream[i] = new ObjectInputStream(serverSockets[i].getInputStream());
			}
			
		} catch (IOException e) {
			
	 	//	e.printStackTrace();
		}
		
	}
	
	
public static void putValue() {
		
		System.out.println("Enter key\n");
		Scanner takekey = new Scanner(System.in);
		String in = takekey.nextLine();
		String key = in;
		System.out.println("Enter value\n");
		Scanner takevalue = new Scanner(System.in);	
		String inp = takekey.nextLine();
		String value = inp;
	    String putvalue = ("p"+"_"+key+"_"+value);
		
	   
	    int hash = myHashFunction(key);
	    
		    
	    try {
	    	
	    	outStream[hash].writeObject(putvalue);
	    	outStream[hash].flush();
	    	
	    		    	
	    	try {
		
	    		String response  = (String)inStream[hash].readObject();
				
				System.out.println(response);
				
			} catch (ClassNotFoundException e) {
				
			//	e.printStackTrace();
			}
	    	
	    }catch (IOException e) {
			
		//	e.printStackTrace();
		}
	}

		public void getValue() {
	
			System.out.println("Enter key\n");
			Scanner takekey = new Scanner(System.in);
			String in = takekey.nextLine();
			String key = in;
			String putvalue = ("g"+"_"+key);
	
    
			int hash = myHashFunction(key);
    
			
 			try {
    	
				outStream[hash].writeObject(putvalue);
				outStream[hash].flush();
    	
				String value  = (String)inStream[hash].readObject();
    	
				System.out.println("Value found : " + value);
    	
			}catch (IOException e) {
				
			//	e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
			//	e.printStackTrace();
			}
	
		}

	public static int myHashFunction(String key) {
			
		String str = key;
		char[] charArray = str.toCharArray();
		
		int sum = 0;

		for (int i = 0; i < charArray.length; i++){
		
			sum = sum + charArray[i];
			
		}
		
		return sum%numPeer;
		
			
		}

	public void deleteValue() {
	
	System.out.println("delete\n");
	System.out.println("Enter key\n");
	Scanner takekey = new Scanner(System.in);
	String in = takekey.nextLine();
	String key = in;
	String putvalue = ("d"+"_"+key);
	
    int hash = myHashFunction(key);
    
       
    try {
    	
    	outStream[hash].writeObject(putvalue);
    	outStream[hash].flush();
    	
    	    	
		try {
		
			String response = (String)inStream[hash].readObject();
		
			System.out.println(response);
			
		} catch (ClassNotFoundException e) {
			
		//	e.printStackTrace();
		}
		
		
		
    }catch (IOException e) {
		
	//	e.printStackTrace();
	}
	
	
}




}



