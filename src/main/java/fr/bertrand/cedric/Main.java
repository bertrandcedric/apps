package fr.bertrand.cedric;

import java.util.ArrayList;
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
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class Main {

	public static void main(String[] args) throws Exception {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();

		String webappDirLocation = "src/main/webapp/";

		String webPort = System.getenv("PORT");
		if (StringUtil.nonNull(webPort).equals("")) {
			webPort = "8080";
		}

		Server server = new Server(Integer.valueOf(webPort));

		List<ServerAddress> addr = new ArrayList<ServerAddress>();
		for (String s : System.getenv("MONGOHQ_URL").split(",")) {
			addr.add(new ServerAddress(s));
		}
		Mongo mongo = new Mongo(addr);
		mongo.setReadPreference(ReadPreference.secondaryPreferred());

		Random rand = new Random((new Date()).getTime());
		int workerNum = 1000 + rand.nextInt(8999);

		final DB db = mongo.getDB("test");
		db.authenticate("", "".toCharArray());
		MongoSessionIdManager idMgr = new MongoSessionIdManager(server, db.getCollection("sessions"));
		idMgr.setWorkerName(String.valueOf(workerNum));
		server.setSessionIdManager(idMgr);

		SessionHandler sessionHandler = new SessionHandler();
		MongoSessionManager mongoMgr = new MongoSessionManager();
		mongoMgr.setSessionIdManager(server.getSessionIdManager());
		sessionHandler.setSessionManager(mongoMgr);

		WebAppContext root = new WebAppContext();
		root.setSessionHandler(sessionHandler);
		root.setContextPath("/");
		root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
		root.setResourceBase(webappDirLocation);
		root.setParentLoaderPriority(true);
		server.setHandler(root);

		server.start();
		server.join();
	}
}