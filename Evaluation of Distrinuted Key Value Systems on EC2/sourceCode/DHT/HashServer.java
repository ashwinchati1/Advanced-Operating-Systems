import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;


public class HashServer implements Runnable{

	Socket peerSocket;
	
	static HashMap<String, String> hm = new HashMap<String, String>();
	
	public HashServer(Socket socket) {
	
		peerSocket = socket;
	}

	@Override
	public void run() {
		
		ObjectInputStream ois;
		ObjectOutputStream oos;
		String key, value, task;
		
		try {
			ois = new ObjectInputStream(peerSocket.getInputStream());
			oos = new ObjectOutputStream(peerSocket.getOutputStream());
			
			while (true){
				String string = (String) ois.readObject();
				
				String[] parts = string.split("_");
				task = parts[0];
				
				if (task.contains("p")){
					key = parts[1];
					value = parts[2];
					
					
					
													
					hm.put(key, value);
					
										
					String response = "Put successful";
					
					oos.writeObject(response);
					oos.flush();
				
				}
				
				if (task.contains("g")){
				
					key = parts[1];
					value = hm.get(key);
					
					oos.writeObject(value);
					oos.flush();
					
				}
				
				if (task.contains("d")){
					key = parts[1];
					hm.remove(key);
					
					String response = "Delete successful";
					
					oos.writeObject(response);
					oos.flush();
				}	
			}
			
		} catch (ClassNotFoundException e) {
			
			//e.printStackTrace();
		} catch (IOException e) {
		
			//e.printStackTrace();
		}
		
	}

}
