package fr.bertrand.cedric.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import fr.bertrand.cedric.model.CountHolder;


@Path("/")
@Produces({ APPLICATION_JSON })
public class Resource {

	@GET
	@Path("/get")
	public Response doGet(@Context HttpServletRequest req) {
		HttpSession session = req.getSession();
		CountHolder count;
		if (session.getAttribute("count") != null) {
			count = (CountHolder) session.getAttribute("count");
		} else {
			count = new CountHolder();
		}
		count.count++;
		session.setAttribute("count", count);
		return Response.ok(count).build();
	}
}