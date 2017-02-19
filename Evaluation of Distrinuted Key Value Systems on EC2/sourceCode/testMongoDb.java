import java.util.ArrayList;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class testMongoDb {

	List<String> nodeIP;
	private static Properties properties;
	static int numPeer;
	static int requests;
	
	static ArrayList<DBCollection> collList;
	ArrayList<BasicDBObject> dbObj;
	DBCollection collection;
	
	static Logger log;
	FileHandler fh;
	SimpleFormatter sf;
	
	public testMongoDb(){
		
		
		try {
			properties = new Properties();
			log = Logger.getLogger("MongoTest.txt");
			fh = new FileHandler("MongoTest.log");
			log.addHandler(fh);
			sf = new SimpleFormatter();
			fh.setFormatter(sf);
			
			nodeIP = new ArrayList<String>();
			collList = new ArrayList<DBCollection>();
			dbObj = new ArrayList<BasicDBObject>();
			properties.load(new FileInputStream("config.properties"));
			
			numPeer = Integer.parseInt(properties.getProperty("numPeer"));
			requests = Integer.parseInt(properties.getProperty("requests"));
						
		} catch (SecurityException e) {
			
		} catch (IOException e) {
			
		}
		
		for(int i = 0; i < numPeer; i++ ){
			nodeIP.add(properties.getProperty("nodeIP" + i));
		}
		
		for(int i=0; i< numPeer; i++){
			collList.add(new MongoClient(nodeIP.get(i)+":27017").getDB("TestDB").getCollection("table1"));
			collList.get(i).insert(new BasicDBObject("key","value"));
			//dbObjList.add((BasicDBObject)collectionList.get(i).find().next());
			System.out.println(collList);
		}
		
		
	}
	public static void main(String[] args) {
		
		testMongoDb performance = new testMongoDb();
		
		   performance.putValue();
		   performance.getValue();
		   performance.deleteValue();
		
	}
	public static void putValue() {
		
		   log.info("Put operation started");
			
			long begintime = System.currentTimeMillis();
			
			for (int i = 0; i <= requests; i++) {

				String key = "a" + i;

				String putvalue = "test"+ i;

				int hash = myHashFunction(key);

				BasicDBObject basicDBObject = (BasicDBObject)collList.get(hash).find().next();
				basicDBObject.put(key, putvalue);
				collList.get(hash).save(basicDBObject);						
			}
		
			log.info("Time Taken for " +requests +" Put :"+(System.currentTimeMillis() - begintime));
			
		}
	   
	   
	   public void getValue() {
			log.info("Get operation started");
			
					
			long begintime = System.currentTimeMillis();

			for (int g = 1; g <= requests; g++) {

				String key = "a" + g;

				int hash = myHashFunction(key);

				BasicDBObject basicDBObject = (BasicDBObject)collList.get(hash).find().next();
				basicDBObject.get(key);
			}

			log.info("Time Taken for " +requests +" get :"+(System.currentTimeMillis() - begintime));
			
		}
	   
		public void deleteValue() {
			log.info("Delete operation started");

			long begintime = System.currentTimeMillis();
			
			for (int d = 1; d <= requests; d++) {

				String key = "a" + d;

				int hash = myHashFunction(key);

				BasicDBObject basicDBObject = (BasicDBObject)collList.get(hash).find().next();
				basicDBObject.remove(key);
				collList.get(hash).save(basicDBObject);
				
			}

			log.info("Time Taken for " +requests +" delete :"+(System.currentTimeMillis() - begintime));
			
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
