package dev.e23.BlogApplication.handler;

import dev.e23.BlogApplication.model.Article;
import dev.e23.BlogApplication.model.Comment;
import dev.e23.BlogApplication.model.User;
import dev.e23.BlogApplication.repository.ArticleRepository;
import dev.e23.BlogApplication.repository.CommentRepository;
import dev.e23.BlogApplication.repository.UserRepository;
import dev.e23.BlogApplication.security.Secured;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.Map;

@Path("/comments")
public class CommentHandler {

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ArticleRepository articleRepository;

    @POST
    @Path("/")
    @Secured({"user", "admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(Comment comment, @Context SecurityContext securityContext) {
        User user = userRepository.findByID(Integer.valueOf(securityContext.getUserPrincipal().getName()));
        comment.setUser(user);
        Article article = articleRepository.findByID(comment.getArticleId());
        comment.setArticle(article);
        comment.setCreatedAt(System.currentTimeMillis() / 1000);
        commentRepository.create(comment);
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }
    @DELETE
    @Path("/{commentId}")
    @Secured({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("commentId") Long commentId, @Context SecurityContext securityContext) {
        User user = userRepository.findByID(Integer.valueOf(securityContext.getUserPrincipal().getName()));
        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("评论未找到").build();
        }
        if (comment.getUser().getId().equals(user.getId()) || user.hasRole("admin")) {
            commentRepository.delete(commentId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("权限不足，无法删除此评论").build();
        }
    }
}
