package software.renato.rkablogquarkus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class PostResource {

    @Inject
    PostService postService;

    @Path("/")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
        return Response.ok()
                .entity(postService.getPage("/"))
                .build();
    }

    @Path("/{post}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getPage(@PathParam("post") String post) {
        String content = postService.getPage(post);
        if (content == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok()
                .entity(content)
                .build();
    }

}
