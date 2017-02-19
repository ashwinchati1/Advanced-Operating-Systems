import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * 
 */

/**
 * @author ashwin
 *
 */
public class FileDownloader implements Runnable{

	Socket client;
	public FileDownloader(Socket socket){
		client = socket;
	}

	@Override
	public void run() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(client.getInputStream());
			String fname = (String) ois.readObject();
			System.out.println("FileName recived: " + fname);
			Properties props = new Properties();
			props.load(new FileInputStream("config.properties"));
			String clientFolderLocation = props.getProperty("clientFolderLocation");


			FileInputStream stream = null;
			BufferedInputStream bufferedInputStream = null;
			OutputStream outputStream = null;
			try {

					System.out.println("Waiting...");
					try {
					System.out.println("Accepted connection : " + client);
						
						File file = new File(clientFolderLocation+"/"+fname); 

						byte[] mybytearray = new byte[(int) file.length()];
						stream = new FileInputStream(file);
						bufferedInputStream = new BufferedInputStream(stream);
						System.out.println("Size : " + (int) file.length());
						bufferedInputStream.read(mybytearray, 0, (int) file.length());
						outputStream = client.getOutputStream();
						System.out.println("Sending " + fname + "("
								+ (int) file.length() + " bytes)");
						System.out.println(mybytearray);
						outputStream.write(mybytearray, 0, (int) file.length());
						outputStream.flush();
						System.out.println("Done.");
					}catch(FileNotFoundException exception){
						System.out.println("File does not exists");
					}
					finally {
						if (bufferedInputStream != null)
							bufferedInputStream.close();
						if (outputStream != null)
							outputStream.close();
						if (client != null)
							client.close();
					}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
	}
}
