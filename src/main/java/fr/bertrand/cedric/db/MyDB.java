package fr.bertrand.cedric.db;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class MyDB {

	public final static String SESSIONS = "sessions";

	private final static Logger LOGGER = LoggerFactory.getLogger(MyDB.class);
	private static DB db;

	private MyDB() {
	}

	public static DB getInstance() {
		if (db == null) {
			Mongo mongo = new Mongo(getServerAddress());
			mongo.setReadPreference(ReadPreference.secondaryPreferred());
			db = mongo.getDB("test");
			db.authenticate("", "".toCharArray());
		}
		return db;
	}

	private static List<ServerAddress> getServerAddress() {
		return Lists.transform(Arrays.asList(System.getenv("MONGOHQ_URL").split(",")), new Function<String, ServerAddress>() {
			@Override
			public ServerAddress apply(String address) {
				try {
					return new ServerAddress(address);
				} catch (UnknownHostException e) {
					LOGGER.error(e.getMessage());
					return null;
				}
			}
		});
	}
}
