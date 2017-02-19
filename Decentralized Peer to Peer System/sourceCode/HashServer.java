import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class HashServer implements Runnable {
	
	Properties props = new Properties();

	public static String clientFolderLocation;
	public static String downloadFolder;

	Socket peerSocket;

	static HashMap<String, String> hm = new HashMap<String, String>();

	public HashServer(Socket socket) {

		peerSocket = socket;
		
		try {
		
			props.load(new FileInputStream("config.properties"));
	
		} catch (FileNotFoundException e) {
			
			
		} catch (IOException e) {
			
			
		}
		
		clientFolderLocation = props.getProperty("clientFolderLocation");
		downloadFolder = props.getProperty("downloadFolder");
	}

	@Override
	public void run() {

		ObjectInputStream ois;
		ObjectOutputStream oos;
		String key, task;
		String value;
		String type;

		try {
			ois = new ObjectInputStream(peerSocket.getInputStream());
			oos = new ObjectOutputStream(peerSocket.getOutputStream());

			while (true) {
				String string = (String) ois.readObject();

				String[] parts = string.split(":");
				task = parts[0];
				
				if (task.contains("p")) {

					key = parts[2];
					value = parts[1];   
					type = parts[3];
					
					
					if (hm.containsKey(key)) {

						String file = hm.get(key);

						if (!file.contains(value)) {

							file = value+ "#" + file;

							hm.put(key, file);

							System.out.println("Register request\n");
							System.out.println(hm);
							System.out.println("Files Registered\n");
							
							String response = "File Registered Successfully";

							if(type.equals("replicaserver")){
								
								value = hm.get(key);
								
								String[] part = value.split("#");
								
								String downloadPort = part[0];
								
								String[] details = downloadPort.split("\\|");
								
								String IP = details[0];
								IP = IP.substring(1, IP.length());
								String Port = details[1];
								
								File f = new File(clientFolderLocation +key);
								
								if (!f.exists()){
									
									System.out.println("File Replication Starting");
									Peer.downloadFile(IP, Integer.parseInt(Port), key);
									File a =new File(downloadFolder+key);
									a.renameTo(new File(clientFolderLocation + a.getName()));
									System.out.println("File Replication Finished");
								}
								
							}					
							
							oos.writeObject(response);
							oos.flush();
						}

					} else {

						hm.put(key, value);
					
						System.out.println("Register request\n");
						System.out.println(hm);
						System.out.println("Files Registered\n");
						
						if(type.equals("replicaserver")){
							
							value = hm.get(key);
							
							String[] part = value.split("#");
							
							String downloadPort = part[0];
							
							String[] details = downloadPort.split("\\|");
							
							String IP = details[0];
							IP = IP.substring(1, IP.length());
							String Port = details[1];
							
							File f = new File(clientFolderLocation +key);
							
							if (!f.exists()){
								System.out.println("File Replication Starting");
								Peer.downloadFile(IP, Integer.parseInt(Port), key);
								File afile =new File(downloadFolder+key);
								afile.renameTo(new File(clientFolderLocation + afile.getName()));
								System.out.println("File Replication Fininshed");
							}
							
						}		
						
						String response = "File Registered Successfully";
						oos.writeObject(response);
						oos.flush();

					}
			
				}
					 if (task.contains("g")){
					 
					 key = parts[1]; 
					 System.out.println("Search Request for:"+key);
					 value = hm.get(key);
					 System.out.println("File found");
					 oos.writeObject(value); 
					 oos.flush();
					
					  }
				

			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			
		}

	}

}
