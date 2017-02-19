import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class Client {

	private static String serverIP;
	private static int PORT ;
	private static String clientPort ;
	public static HashMap<ArrayList<String>, String> hmap = new HashMap<ArrayList<String>, String>();
	public static String clientFolderLocation;
	
	public static void main(String[] args) throws IOException {
		
		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		PORT = Integer.parseInt(props.getProperty("serverPort"));
		clientPort = props.getProperty("clientPort");
		serverIP = props.getProperty("serverIP");
		 clientFolderLocation = props.getProperty("clientFolderLocation");
		
		Scanner scanner = new Scanner(System.in);
		try {
			Client client = new Client();
			Thread t = new Thread(
					new ClientServer(Integer.parseInt(clientPort)));
			t.start();

			String message, response;

			do {
				System.out.println("Enter Your Choice\n");
				System.out.println("1.Register Files\n");
				System.out.println("2.Search File\n");
				System.out.println("3.Exit\n");

				String in = scanner.nextLine();
				int input = Integer.parseInt(in);

				// outputserver.println(in);
				

				switch (input) {

				case 1:
					client.registerFile();
					break;

				case 2:
					System.out.println("Enter file you want to search\n");
					String fsearch = scanner.nextLine();
					client.searchFile(fsearch);
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
			Ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private void searchFile(String fsearch) {

		try {
			System.out.println("Connecting");
			Socket clientsocket = new Socket(serverIP, PORT);
			System.out.println("Connected");
			ObjectOutputStream oos = new ObjectOutputStream(
					clientsocket.getOutputStream());
			// sending file search req
			oos.writeObject(fsearch);
			oos.flush();
			System.out.println("request sent to search");
			// reading file search response
			ObjectInputStream ois = new ObjectInputStream(
					clientsocket.getInputStream());
			ArrayList<PeerEntity> peerEntities = (ArrayList<PeerEntity>) ois
					.readObject();
			if (peerEntities.get(0).getPort() == 0
					&& peerEntities.get(0).getIpAddress()
							.equalsIgnoreCase("FileNotFound")) {
				System.out.println("File Not found");
			} else {
				System.out.println("File found!!");
				System.out.println("Peers having the file : ");
				for (PeerEntity peer : peerEntities) {
					System.out.println("IP :" + peer.getIpAddress() + " Port :"
							+ peer.getPort());
				}
				System.out.println("Do you want to download the file?(Y or N)");
				Scanner scanner = new Scanner(System.in);
				String input = scanner.next();
				if (input.equalsIgnoreCase("Y")) {
					downloadFile(peerEntities, fsearch);
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void downloadFile(ArrayList<PeerEntity> peerEntities, String fname) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		int bytesRead, size = 6022386;
		
		Socket clientsocket = null;
		try {

			String ipAdd = peerEntities.get(0).getIpAddress();
			ipAdd = ipAdd.substring(1, ipAdd.length());
			System.out.println("Calculated IP : " + ipAdd);
			clientsocket = new Socket(ipAdd, peerEntities.get(0).getPort());

			System.out.println("Connected");
			ObjectOutputStream oos = new ObjectOutputStream(
					clientsocket.getOutputStream());
			// sending filename to download
			oos.writeObject(fname);
			oos.flush();
			System.out.println("File Name sent to the downloder");

			
			int current = 0;
			System.out.println("Connecting...");

			// receive file
			byte[] mybytearray = new byte[size];
			InputStream is = clientsocket.getInputStream();
			fos = new FileOutputStream(fname);
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(mybytearray, current,
						(mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);

			bos.write(mybytearray, 0, current);
			bos.flush();
			System.out.println("File " + fname + " downloaded (" + current
					+ " bytes read)");

			// Start downloading code--
			// complete file path donload folder

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception exception){
			exception.printStackTrace();
		}
		finally {
			if (fos != null)
				try {
					fos.close();
					if (bos != null)
						bos.close();
					if (clientsocket != null)
						clientsocket.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
	}

	private void registerFile() {

		String IP = null;

		ArrayList<String> fileList = new ArrayList<String>();
		try {

			@SuppressWarnings("resource")
			Socket clientsocket = new Socket(serverIP, PORT);
			ObjectOutputStream oos = new ObjectOutputStream(
					clientsocket.getOutputStream());

			String ipaddress = getMyIP();
			// System.out.println(ipaddress);

			File folder = new File(clientFolderLocation);
			File[] listOfFiles = folder.listFiles();

			fileList.add(ipaddress);
			fileList.add(clientPort);

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {
					String fname = listOfFiles[i].getName();
					System.out.println(fname);
					fileList.add(fname);
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println(fileList);
				}
			}

			oos.writeObject(fileList);
			oos.flush();
			System.out.println("\n\nFiles Registered");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getMyIP() throws SocketException {
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
