import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang.RandomStringUtils;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.ViewResults;

public class testCouchDb {
	
	List<String> nodeIP;
	private static Properties properties;
	static int numPeer;
	static int requests;
	private static Session databaseSession[];
	static Database databaseName[];
	String dbname;
	static List<String> keyArray = new ArrayList<String>();

	static Logger log;
	FileHandler fh;
	SimpleFormatter sf;
	
	public testCouchDb(){
		
		try {
			properties = new Properties();
			log = Logger.getLogger("CouchTest.txt");
			fh = new FileHandler("CouchTest.log");
			log.addHandler(fh);
			sf = new SimpleFormatter();
			fh.setFormatter(sf);

			nodeIP = new ArrayList<String>();
			properties.load(new FileInputStream("config.properties"));

			numPeer = Integer.parseInt(properties.getProperty("numPeer"));
			requests = Integer.parseInt(properties.getProperty("requests"));
			
			databaseSession=new Session[numPeer];
			databaseName=new Database[numPeer];
			
		} catch (SecurityException e) {

		} catch (IOException e) {
	
		}

		for (int i = 0; i < numPeer; i++) {
			nodeIP.add(properties.getProperty("nodeIP" + i));
		}
		
		for (int i = 0; i < numPeer; i++) {
		String str=nodeIP.get(i);
		//String dbname=RandomStringUtils.randomAlphabetic(5).toLowerCase();
		databaseSession[i] = new Session(str,5984);
		databaseSession[i].createDatabase("ashwindb2");
		databaseName[i]=databaseSession[i].getDatabase("ashwindb2");
		}
		
	}


	public static void main(String[] args) {
		
		testCouchDb performance = new testCouchDb();
		
		performance.putValue();
		performance.getValue();
		performance.deleteValue();
	

	}

	public static void putValue() {

		log.info("Put operation started");

		long begintime = System.currentTimeMillis();

		for (int i = 0; i <= requests; i++) {

			String kname=RandomStringUtils.randomAlphabetic(5).toLowerCase();

			String key = kname + i;

			keyArray.add(key);

			String putvalue = "test" + i;

			int hash = myHashFunction(key);

			Document doc = new Document();
			doc.setId(key);
    		        doc.put("value", putvalue);
    		        databaseName[hash].saveDocument(doc);
		}

		log.info("Time Taken for " + requests + " Put :"
				+ (System.currentTimeMillis() - begintime));

	}

	public void getValue() {
		log.info("Get operation started");

		long begintime = System.currentTimeMillis();

		for (int g = 1; g < keyArray.size(); g++) {

			String key = keyArray.get(g);

			int hash = myHashFunction(key);

			Document d = databaseName[hash].getDocument(key);
			String str= (String)d.get("value");
			
		}

		log.info("Time Taken for " + requests + " get :"
				+ (System.currentTimeMillis() - begintime));

	}

	public void deleteValue() {
		log.info("Delete operation started");

		long begintime = System.currentTimeMillis();

		for (int r = 1; r < keyArray.size(); r++) {

			String key = keyArray.get(r);

			int hash = myHashFunction(key);

			Document d = databaseName[hash].getDocument(key);
			databaseName[hash].deleteDocument(d);

		}

		log.info("Time Taken for " + requests + " delete :"
				+ (System.currentTimeMillis() - begintime));

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
