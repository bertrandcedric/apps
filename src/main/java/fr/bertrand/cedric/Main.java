package fr.bertrand.cedric;

import com.mongodb.DB;
import fr.bertrand.cedric.db.MyDB;
import org.eclipse.jetty.nosql.mongodb.MongoSessionIdManager;
import org.eclipse.jetty.nosql.mongodb.MongoSessionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.net.UnknownHostException;
import java.util.logging.LogManager;

public class Main {

    private final static String webappDirLocation = "src/main/webapp/";

	public static void main(String[] args) throws Exception {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

		String webPort = System.getenv("PORT");
		if (StringUtil.nonNull(webPort).equals("")) {
			webPort = "8080";
		}

		Server server = new Server(Integer.valueOf(webPort));
		final DB db = MyDB.getInstance();
		if (db != null) {
			server.setSessionIdManager(getMongoSessionIdManager(server, db));

			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.setSessionManager(getMongoSessionManager(server));

			final WebAppContext webAppContext = setWebAppContext(sessionHandler);
			server.setHandler(webAppContext);

			server.start();
			server.join();
		} else {
			server.stop();
		}
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
		return new MongoSessionIdManager(server, db.getCollection(MyDB.SESSIONS));
	}
}