package br.com.vitta.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import br.com.vitta.controller.TerritoryController;
import br.com.vitta.helper.PathRedirect;
import br.com.vitta.model.Territory;

@Path("/territories")
public class TerritoryResource {
	
	@Context
	 private UriInfo uri;
	
	// @Path("/listarTodos")
	@GET
	@Produces("application/json")
	public String listarTodos() {
		ArrayList<Territory> listTerritory = new TerritoryController()
				.listarTodos();

		Map<String, Object> map = new HashMap<String, Object>();

		if (listTerritory != null && listTerritory.size() > 0) {
			map.put("count", listTerritory.size());
			map.put("data", listTerritory);
		} else {
			map.put("error", true);
		}

		JSONObject json = new JSONObject(map);

		return json.toString();
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public String getTerritory(@PathParam("id") int id, @QueryParam("withpainted") boolean withpainted) throws Exception {
		Territory territory = new TerritoryController().getTerritory(id, withpainted);

		Map<String, Object> map = new HashMap<String, Object>();

		if (territory != null) {
			map.put("error", false);
			map.put("data", territory);
		} else {
			redirect(PathRedirect.PATH_NOT_FOUND);
		}

		JSONObject json = new JSONObject(map);

		return json.toString();
	}

	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public String deleteTerritory(@PathParam("id") int id) throws Exception {
		boolean error = new TerritoryController().removeTerritory(id);

		if (error) {
			redirect(PathRedirect.PATH_NOT_FOUND);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", error);

		JSONObject json = new JSONObject(map);
		return json.toString();
	}

	// @Path("/territories")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String addTerritory(Territory territory) throws Exception {
		if (territory.getStart() == null || territory.getEnd() == null || emptyAsNull(territory.getName()) == null) {
			redirect(PathRedirect.PATH_INCOMPLETE_DATA);
		}
		
		TerritoryController controller = new TerritoryController();
		
		if (controller.isTerritoryOverlay(territory)) {
			redirect(PathRedirect.PATH_TERRITORY_OVERLAY);
		}
		
		territory = controller.addTerritory(territory);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", territory.getId() < 0 ? true : false);
		map.put("data", territory);

		JSONObject json = new JSONObject(map);
		System.out.println(json.toString());

		return json.toString();
	}
	
	public String emptyAsNull(String string) {
		if (string != null && "".equals(string.trim())) {
			return null;
		}
		
		return string;
	}
	
	@GET
	@Path("/not-found")
	@Produces("text/plain")
	public String notFound() throws Exception {
		return "This territory was not found.";
	}

	@GET
	@Path("/territory-overlay")
	@Produces("text/plain")
	public String territoryOverlay() throws Exception {
		return "This new territory overlays another territory.";
	}

	@GET
	@Path("/incomplete-data")
	@Produces("text/plain")
	public String incompleteData() throws Exception {
		return "If it misses the start, end or name fields.";
	}
	
	private void redirect(String path) throws Exception {
		URI targetURIForRedirection = new URI(uri.getBaseUri().toString() + "territories" + path);
		
		if (targetURIForRedirection != null) {
			throw new WebApplicationException(Response.seeOther(targetURIForRedirection).build());
		}
	}
	
}
