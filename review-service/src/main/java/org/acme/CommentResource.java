package org.acme;

import java.util.List;

import org.acme.model.Comment;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class CommentResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("comments")
    public List<Comment> comments() {
        return Comment.listAll();
    }

}
