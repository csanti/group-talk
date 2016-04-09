package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 2/04/16.
 */
@Path("grupos")
public class GrupoResource {
    @Context
    private SecurityContext securityContext;

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_GRUPOS_COLLECTION)
    public GruposCollection getGrupos() {
        GruposCollection gruposCollection = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();

        try{
            gruposCollection = grupoDAO.getGrupos();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return gruposCollection;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_GRUPO)
    public Response createGrupo(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException {
        if(nombre == null)
            throw new BadRequestException("all parameters are mandatory");

        //Solo puede crear grupos usuario con rol admin
        if(!securityContext.isUserInRole("admin"))
            throw new ForbiddenException("operation not allowed");

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;

        try {
            grupo = grupoDAO.createGrupo(nombre);
        } catch (SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + grupo.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_GRUPO).entity(grupo).build();
    }

    @Path("/{id}/members")
    @PUT
    public Response joinGroup(@PathParam("id") String id, @Context UriInfo uriInfo) throws URISyntaxException{
        String userid = securityContext.getUserPrincipal().getName();
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();

        try {
            grupo = grupoDAO.getGrupoById(id);
            if(grupo == null)
                throw new BadRequestException("grupo id doesn't exist");
            if(grupoDAO.checkMembership(userid,id))
                throw new WebApplicationException("user is already a member", Response.Status.CONFLICT);
            grupoDAO.joinGrupo(userid, id);

        } catch (SQLException e){
            throw new InternalServerErrorException();
        }

        URI uri = new URI(uriInfo.getBaseUri().toString() + "/grupos/" + grupo.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_GRUPO).entity(grupo).build();
    }

    @Path("/{id}/members")
    @DELETE
    public void leaveGroup(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();

        try {
            grupoDAO.leaveGrupo(userid, id);

        } catch (SQLException e){
            throw new InternalServerErrorException();
        }

    }

    @Path("/{id}/temas")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_TEMAS_COLLECTION)
    public TemasCollection getTemas(@PathParam("id") String id) {
        TemasCollection temasCollection = null;

        TemaDAO temaDAO = new TemaDAOImpl();
        GrupoDAO grupoDAO = new GrupoDAOImpl();

        String userid = securityContext.getUserPrincipal().getName();

        try{
            if(!grupoDAO.checkMembership(userid, id) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            temasCollection = temaDAO.getTemasFromGrupo(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return temasCollection;
    }

    @Path("/{id}/temas")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Response createTema(@PathParam("id") String id, @FormParam("titulo") String titulo, @FormParam("contenido") String contenido, @Context UriInfo uriInfo) throws URISyntaxException {
        if(titulo==null || contenido== null)
            throw new BadRequestException("all parameters are mandatory");


        TemaDAO temaDAO = new TemaDAOImpl();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Tema tema = null;

        String userid = securityContext.getUserPrincipal().getName();

        try {
            if(!grupoDAO.checkMembership(userid, id) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            tema = temaDAO.createTema(userid, id, titulo, contenido);
        } catch (SQLException e){
            throw new InternalServerErrorException();
        }

        URI uri = new URI(uriInfo.getBaseUri().toString() + "/temas/" + tema.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_TEMA).entity(tema).build();
    }





}