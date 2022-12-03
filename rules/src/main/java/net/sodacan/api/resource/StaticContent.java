package net.sodacan.api.resource;

import java.io.File;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("")
public class StaticContent {
	
	@Path("{filename}")
	@GET
	public Response getFile(@PathParam("filename") String filename ) {
		File file = new File("html/" + filename);
//		System.out.println(file.getAbsolutePath());
		return Response.ok(file).build();

	}
}
