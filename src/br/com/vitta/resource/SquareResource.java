package br.com.vitta.resource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import br.com.vitta.controller.TerritoryController;
import br.com.vitta.helper.PathRedirect;
import br.com.vitta.model.Territory.Square;

@Path("/squares")
public class SquareResource {
	
	@Context
	 private UriInfo uri;
	
	@GET
	@Path("/{x}/{y}")
	@Produces("application/json")
	public String getSquare(@PathParam("x") int x, @PathParam("y") int y) throws Exception {
		Square square = new TerritoryController().getSquare(x, y);
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (square != null) {
			map.put("error", false);
			map.put("data", square);
		} else {
			redirect(PathRedirect.PATH_NOT_FOUND);
		}

		JSONObject json = new JSONObject(map);

		return json.toString();
	}
	
	@PATCH
	@Path("/{x}/{y}/paint")
	@Produces("application/json")
	public String paintSquare(@PathParam("x") int x, @PathParam("y") int y) throws Exception {
		Square square = new TerritoryController().paintSquare(x, y);
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (square != null) {
			map.put("error", false);
			map.put("data", square);
		} else {
			redirect(PathRedirect.PATH_NOT_FOUND);
		}

		JSONObject json = new JSONObject(map);

		return json.toString();
	}
	
	@GET
	@Path("/not-found")
	@Produces("text/plain")
	public String notFound() throws Exception {
		return "This square does not belong to any territory.";
	}

	private void redirect(String path) throws Exception {
		URI targetURIForRedirection = new URI(uri.getBaseUri().toString() + "squares" + path);
		
		if (targetURIForRedirection != null) {
			throw new WebApplicationException(Response.seeOther(targetURIForRedirection).build());
		}
	}

}
