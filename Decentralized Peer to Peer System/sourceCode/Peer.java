import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author ashwin
 * 
 */
public class Peer {

	private static int myPort;

	private static int numPeer;

	Socket[] serverSockets;
	static int nodePort[] = new int[8];
	static String nodeIP[] = new String[8];
	static int replication;
	static int downloadPort[] = new int[8];

	static ObjectOutputStream[] outStream;
	static ObjectInputStream[] inStream;
	public static String clientFolderLocation;
	private static String clientPort;
	public static String downloadFolder;

	public static void main(String[] args) throws IOException {

		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		myPort = Integer.parseInt(props.getProperty("myPort"));
		numPeer = Integer.parseInt(props.getProperty("numPeer"));
		clientFolderLocation = props.getProperty("clientFolderLocation");
		clientPort = props.getProperty("clientPort");
		replication = Integer.parseInt(props.getProperty("replication"));
		downloadFolder = props.getProperty("downloadFolder");

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

		// Download Port initialization

		downloadPort[0] = Integer.parseInt(props.getProperty("clientPort1"));
		downloadPort[1] = Integer.parseInt(props.getProperty("clientPort2"));
		downloadPort[2] = Integer.parseInt(props.getProperty("clientPort3"));
		downloadPort[3] = Integer.parseInt(props.getProperty("clientPort4"));
		downloadPort[4] = Integer.parseInt(props.getProperty("clientPort5"));
		downloadPort[5] = Integer.parseInt(props.getProperty("clientPort6"));
		downloadPort[6] = Integer.parseInt(props.getProperty("clientPort7"));
		downloadPort[7] = Integer.parseInt(props.getProperty("clientPort8"));

		boolean serversConnected = false;

		Scanner scanner = new Scanner(System.in);

		Peer peer = new Peer();
		Peer client = new Peer();

		try {

			Thread t = new Thread(new PeerServer(myPort));
			t.start();

			Thread th = new Thread(new ClientServer(
					Integer.parseInt(clientPort)));
			th.start();

			do {
				System.out.println("*****************************************\n");

				System.out.println("\t\tMenu\n");
				System.out.println("*****************************************\n");
				System.out.println("Enter the digit for the opration to perform\n");
				System.out.println("1.Register Files\n");
				System.out.println("2.Search Files\n");
				System.out.println("3.Exit\n");
				System.out.println("*****************************************\n");

				System.out.println("Enter Your Choice\n");
				String in = scanner.nextLine();
				int input = Integer.parseInt(in);

				if (!serversConnected) {
					serversConnected = true;
					peer.connectToServers();
				}

				switch (input) {

				case 1:
					peer.Register();
					break;

				case 2:
					peer.Search();
					break;

				case 3:
					System.exit(0);
					break;

				default:
					System.out.println("Invalid Option\n");
					break;

				}
			} while (true);

		} catch (Exception uhEx) {
			
			System.out.println("\nHost ID not found!\n");
			
		} catch (ExceptionInInitializerError Ex) {
			
		} finally {
			scanner.close();
		}

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

			
		}

	}

	private void Register() {

		try {

			ArrayList<String> fileList = new ArrayList<String>();
			String ipaddress = getMyIP();
			int a;
			String putvalue;

			File folder = new File(clientFolderLocation);
			File[] listOfFiles = folder.listFiles();

			fileList.add(ipaddress);
			fileList.add(clientPort);

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {

					String fname = listOfFiles[i].getName();
				//	System.out.println(fname);
					fileList.add(fname);
					int hash = myHashFunction(fname);

					putvalue = "p" + ":" + ipaddress + "|" + clientPort;

					if (replication == 0) {

						putvalue = "p" + ":" + ipaddress + "|" + clientPort
								+ ":" + fname;

						try {
							
							RegisterFile(hash, putvalue);

						} catch (Exception e) {

						}

					}

					else {

						int dport = hash;

						for (a = 0; a <= replication - 1; a++) {

							dport = (dport + 1) % numPeer;

							putvalue = putvalue + "#" + "/" + nodeIP[dport]
									+ "|" + downloadPort[dport];

						}

						putvalue = putvalue + ":" + fname;
						

						try {
					
							putvalue = putvalue + ":" + "mainserver";
							RegisterFile(hash, putvalue);

						} catch (Exception e) {

						}

						try {

							putvalue = putvalue.replaceAll("mainserver","replicaserver");
						
							for (a = 0; a < replication; a++) {

								hash = (hash + 1) % numPeer;
								RegisterFile(hash, putvalue);
								
							}
						} catch (Exception e) {

						}
					}
					
				}

				else if (listOfFiles[i].isDirectory()) {
					System.out.println(fileList);

				}

			}

		} catch (Exception e) {
			
		}

	}

	private void RegisterFile(int hash, String putvalue) throws IOException,
			ClassNotFoundException {

		outStream[hash].writeObject(putvalue);

		outStream[hash].flush();

		String response;

		response = (String) inStream[hash].readObject();

		System.out.println(response);

	}

	private void Search() {

		System.out.println("Enter key\n");
		Scanner takekey = new Scanner(System.in);
		String in = takekey.nextLine();
		String key = in;
		String putvalue = ("g" + ":" + key);
		String value = null;

		int hash = myHashFunction(key);
		String ipAdd;
		int port;

		if (replication == 0) {

			try {

				value = SearchFile(hash, putvalue);

			} catch (Exception e) {

				System.out.println("Server Down");

			}

		} else {

			try {
				
				value = SearchFile(hash, putvalue);

			} catch (Exception e) {
				System.out
						.println("Server Down..Getting information from another server");

				try {

					for (int a = 0; a < replication; a++) {

						hash = (hash + 1) % numPeer;
						value = SearchFile(hash, putvalue);
					}
				} catch (Exception e1) {

				}

			}

		}
		
		
		String[] parts = value.split("#");

		System.out.println("File found on servers:");

		
		
		for (int i = 0; i < parts.length; i++) {

			System.out.println(parts[i]);

		}
		
		System.out.println("Do you want to download the file?(Y or N)");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		if (input.equalsIgnoreCase("Y")) {

			if (parts.length > 1) {

				int choice;

				System.out.println("From which server you want the file\n");
				Scanner inp = new Scanner(System.in);
				choice = inp.nextInt();

				String prefernece = parts[choice - 1];
			
				String[] location = prefernece.split("\\|");

				ipAdd = location[0];
				ipAdd = ipAdd.substring(1, ipAdd.length());
				
				port = Integer.parseInt(location[1]);
				
				System.out.println("IP:" + ipAdd + "\tPort:"
						+ port);
				
				downloadFile(ipAdd, port, key);
				
			} else {

				String[] location = parts[0].split("\\|");

				ipAdd = location[0];
				ipAdd = ipAdd.substring(1, ipAdd.length());
				
				port = Integer.parseInt(location[1]);
				
				System.out.println("IP:" + ipAdd + "\tPort:"
						+ port);

				downloadFile(ipAdd, port, key);
				
			}
		}
	}

	
	private String SearchFile(int hash, String putvalue) throws IOException,
			ClassNotFoundException {

		outStream[hash].writeObject(putvalue);
		outStream[hash].flush();
		String value = (String) inStream[hash].readObject();
		return value;
	
	}
	
	
	public static void downloadFile(String ipAddr, int port, String fname) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		int bytesRead, size = 1024;

		Socket clientsocket = null;

	
		try {

			clientsocket = new Socket(ipAddr, port);

			ObjectOutputStream oos = new ObjectOutputStream(
					clientsocket.getOutputStream());
			
			oos.writeObject(fname);
			oos.flush();
			System.out.println("File Name sent to the downloder");

			int current = 0;
			System.out.println("Connecting...");

			System.out.println("Receiving File");
			byte[] mybytearray = new byte[size];
			DataInputStream is = new DataInputStream(
					clientsocket.getInputStream());

			fos = new FileOutputStream(downloadFolder + fname);
			bos = new BufferedOutputStream(fos);

			Long fileLength = is.readLong();

			long bytesToReceive = fileLength;

			int bytesReceived = 0;

			int bytesThisTime = 0;

			int i = 0;

			while (bytesReceived < bytesToReceive) {

				bytesThisTime = (int) bytesToReceive - bytesReceived;

				bytesThisTime = bytesThisTime < mybytearray.length ? bytesThisTime
						: mybytearray.length;

				is.read(mybytearray, 0, (int) bytesThisTime);

				bos.write(mybytearray, 0, bytesThisTime);

				bos.flush();

				bytesReceived += bytesThisTime;
				i++;

			}

			System.out.println("File Received");
			
		} catch (IOException e) {
			
		} catch (Exception exception) {
			
		} finally {
			if (fos != null)
				try {
					fos.close();
					if (bos != null)
						bos.close();
					if (clientsocket != null)
						clientsocket.close();
				} catch (IOException e) {

					
				}
		}
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

	private static String getMyIP() throws SocketException {
		Enumeration en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface i = (NetworkInterface) en.nextElement();
			for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
				InetAddress addr = (InetAddress) en2.nextElement();
				if (!addr.isLoopbackAddress()) {
					if (addr instanceof Inet4Address) {
						return addr.toString();
					}
					if (addr instanceof Inet6Address) {
						continue;
					}
				}
			}
		}
		return null;
	}

}
