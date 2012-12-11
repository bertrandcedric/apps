package fr.bertrand.cedric.db;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.MongoURI;
import com.mongodb.ReadPreference;

public class MyDB {

	public final static String SESSIONS = "sessions";

	private final static Logger LOGGER = LoggerFactory.getLogger(MyDB.class);
	private static DB db;

	private MyDB() {
	}

	public static DB getInstance() {
		if (db == null) {
			try {
				MongoURI mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
				mongoURI.getOptions().setReadPreference(ReadPreference.secondaryPreferred());
				db = mongoURI.connectDB();
				if (mongoURI.getUsername() != null) {
					db.authenticate(mongoURI.getUsername(), mongoURI.getPassword());
				}
			} catch (UnknownHostException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return db;
	}
}
