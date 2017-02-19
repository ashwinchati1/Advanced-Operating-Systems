import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

class ProxyServer extends Thread
{
	Socket socket;
	 
	 private static ConcurrentHashMap<String, ArrayList<PeerEntity>> registryMap;
	 
	public ProxyServer(Socket serversocket)
	{
		socket = serversocket;
		if (null == registryMap){
			registryMap = new ConcurrentHashMap<String, ArrayList<PeerEntity>>();
		}
	}

	public void run()
	{
		try	
		{
			String str;
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			Object o = ois.readObject();
			
			if (o instanceof String){
				System.out.println("Search request!!");
				
				searchFile(o);
			}else{
				System.out.println("Register request!!");
				registerFile(o);
			}
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void searchFile(Object o) {
		String fileName = (String) o;
		try {
			ArrayList<PeerEntity> peerEntities = registryMap.get(fileName);
			ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
			if (peerEntities == null){
				System.out.println("File not found");
				peerEntities = new ArrayList<PeerEntity>();
				PeerEntity peerEntity = new PeerEntity(0,"FileNotFound");
				peerEntities.add(peerEntity);
				
			}
			ois.writeObject(peerEntities);
			ois.flush();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

	@SuppressWarnings("unchecked")
	private void registerFile(Object o) {
		
		ArrayList<String> fileNames = (ArrayList<String>) o;
		
		String ipAddress = fileNames.get(0);
		String port = fileNames.get(1);
		PeerEntity peer = new PeerEntity(Integer.parseInt(port), ipAddress);
		
		System.out.println("No of Files : " + (fileNames.size()-2));
		
		for (int i=2;  i< (fileNames.size()) ; i++){
			String fileName = fileNames.get(i);
			if (registryMap.contains(fileName)){
				ArrayList <PeerEntity> peerEntities = registryMap.get(fileName);
				peerEntities.add(peer);
			}else{
				ArrayList <PeerEntity> peerEntities = new ArrayList<PeerEntity>();
				peerEntities.add(peer);
				registryMap.put(fileName, peerEntities);
			}
		}
	}
}

