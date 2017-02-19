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

import redis.clients.jedis.*;

public class testRedis {

	private static Jedis jedis[];

	List<String> nodeIP;
	private static Properties properties;
	static int numPeer;
	static int requests;

	static Logger log;
	FileHandler fh;
	SimpleFormatter sf;

	public testRedis() {

		try {
			properties = new Properties();
			log = Logger.getLogger("RedisTest.txt");
			fh = new FileHandler("RedisTest.log");
			log.addHandler(fh);
			sf = new SimpleFormatter();
			fh.setFormatter(sf);

			nodeIP = new ArrayList<String>();
			properties.load(new FileInputStream("config.properties"));

			numPeer = Integer.parseInt(properties.getProperty("numPeer"));
			requests = Integer.parseInt(properties.getProperty("requests"));
			jedis = new Jedis[numPeer];

		} catch (SecurityException e) {

		} catch (IOException e) {

		}

		for (int i = 0; i < numPeer; i++) {
			nodeIP.add(properties.getProperty("nodeIP" + i));
		}

		for (int i = 0; i < numPeer; i++) {

			String s = nodeIP.get(i);
			jedis[i] = new Jedis(s);

		}

	}

	public static void main(String[] args) {

		testRedis performance = new testRedis();

		performance.putValue();
		performance.getValue();
		performance.deleteValue();

	}

	public static void putValue() {

		log.info("Put operation started");

		long begintime = System.currentTimeMillis();

		for (int i = 0; i <= requests; i++) {

			String key = "a" + i;

			String putvalue = "test" + i;

			int hash = myHashFunction(key);

			jedis[hash].set(key, putvalue);
		}

		log.info("Time Taken for " + requests + " Put :"
				+ (System.currentTimeMillis() - begintime));

	}

	public void getValue() {
		log.info("Get operation started");

		long begintime = System.currentTimeMillis();

		for (int g = 1; g <= requests; g++) {

			String key = "a" + g;

			int hash = myHashFunction(key);

			jedis[hash].get(key);
		}

		log.info("Time Taken for " + requests + " get :"
				+ (System.currentTimeMillis() - begintime));

	}

	public void deleteValue() {
		log.info("Delete operation started");

		long begintime = System.currentTimeMillis();

		for (int d = 1; d <= requests; d++) {

			String key = "a" + d;

			int hash = myHashFunction(key);

			jedis[hash].del(key);

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
