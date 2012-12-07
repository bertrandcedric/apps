package fr.bertrand.cedric;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.LogManager;

import org.eclipse.jetty.nosql.mongodb.MongoSessionIdManager;
import org.eclipse.jetty.nosql.mongodb.MongoSessionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class Main {

	private final static String webappDirLocation = "src/main/webapp/";

	private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();

		String webPort = System.getenv("PORT");
		if (StringUtil.nonNull(webPort).equals("")) {
			webPort = "8080";
		}

		Server server = new Server(Integer.valueOf(webPort));
		server.setSessionIdManager(getMongoSessionIdManager(server, getDb(getMongodb(getServerAddress()))));

		SessionHandler sessionHandler = new SessionHandler();
		sessionHandler.setSessionManager(getMongoSessionManager(server));

		server.setHandler(setWebAppContext(sessionHandler));

		server.start();
		server.join();
	}

	private static WebAppContext setWebAppContext(SessionHandler sessionHandler) {
		WebAppContext root = new WebAppContext();
		root.setSessionHandler(sessionHandler);
		root.setContextPath("/");
		root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
		root.setResourceBase(webappDirLocation);
		root.setParentLoaderPriority(true);
		return root;
	}

	private static MongoSessionManager getMongoSessionManager(Server server) throws UnknownHostException {
		MongoSessionManager mongoMgr = new MongoSessionManager();
		mongoMgr.setSessionIdManager(server.getSessionIdManager());
		return mongoMgr;
	}

	private static MongoSessionIdManager getMongoSessionIdManager(Server server, DB db) {
		Random rand = new Random((new Date()).getTime());
		int workerNum = 1000 + rand.nextInt(8999);
		MongoSessionIdManager idMgr = new MongoSessionIdManager(server, db.getCollection("sessions"));
		idMgr.setWorkerName(String.valueOf(workerNum));
		return idMgr;
	}

	private static Mongo getMongodb(List<ServerAddress> serveAddress) {
		Mongo mongo = new Mongo(serveAddress);
		mongo.setReadPreference(ReadPreference.secondaryPreferred());
		return mongo;
	}

	private static DB getDb(Mongo mongo) {
		final DB db = mongo.getDB("test");
		db.authenticate("", "".toCharArray());
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