package fr.bertrand.cedric.resource;

import com.mongodb.DBObject;
import fr.bertrand.cedric.db.MyDB;
import fr.bertrand.cedric.model.CountHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
@Produces({ APPLICATION_JSON })
public class Resource {

	@GET
	@Path("/get")
	public Response doGet(@Context HttpServletRequest req) {
		HttpSession session = req.getSession();
		CountHolder count = session.getAttribute("count") != null ? (CountHolder) session.getAttribute("count") : new CountHolder();
		count.count++;
		session.setAttribute("count", count);
		return Response.ok(count).build();
	}

	@GET
	@Path("/all")
	public Response all(@Context HttpServletRequest req) {
		final List<DBObject> array = MyDB.getInstance().getCollection(MyDB.SESSIONS).find().toArray();
		return Response.ok(array).build();
	}
}