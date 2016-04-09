package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 9/04/16.
 */
@Path("temas")
public class TemaResource {
    @Context
    private SecurityContext securityContext;

    @Path("/{id}/respuestas")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_RESPUESTAS_COLLECTION)
    public RespuestasCollection getRespuestas(@PathParam("id") String id) {
        RespuestasCollection respuestasCollection = null;

        RespuestaDAO resputasDAO = new RespuestaDAOImpl();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        TemaDAO temaDAO = new TemaDAOImpl();

        String userid = securityContext.getUserPrincipal().getName();

        try{
            if(!grupoDAO.checkMembership(userid, temaDAO.getTemaById(id).getGrupoid()) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");

            respuestasCollection = resputasDAO.getRespuestasFromTema(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return respuestasCollection;
    }

    @Path("/{idtema}/respuestas")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Response createRespuesta(@PathParam("idtema") String idtema, @FormParam("contenido") String contenido, @Context UriInfo uriInfo) throws URISyntaxException {
        if(contenido== null)
            throw new BadRequestException("all parameters are mandatory");

        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        TemaDAO temaDAO = new TemaDAOImpl();
        Respuesta respuesta = null;

        String userid = securityContext.getUserPrincipal().getName();

        try {
            //por seguridad, se comprueba que el usuario pertenezca al grupo del tema

            if(!grupoDAO.checkMembership(userid, temaDAO.getTemaById(idtema).getGrupoid()) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");

            respuesta = respuestaDAO.createRspuesta(idtema,userid,contenido);

        } catch (SQLException e){
            throw new InternalServerErrorException();
        }

        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + respuesta .getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_RESPUESTA).entity(respuesta).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_TEMA)
    @Produces(GroupTalkMediaType.GROUPTALK_TEMA)
    public Tema updateTema(@PathParam("id") String id, Tema tema) {
        if(tema == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(tema.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();

        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            String ownerid = temaDAO.getTemaById(id).getUserid();
            if(!userid.equals(ownerid) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            tema = temaDAO.updateTema(id, tema.getContenido());
            if(tema == null)
                throw new NotFoundException("Tema with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return tema;
    }

    @Path("/{id}")
    @DELETE
    public void deleteTema(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            String ownerid = temaDAO.getTemaById(id).getUserid();
            //solo puede borrar el autor o un administrador
            if(!userid.equals(ownerid) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            if(!temaDAO.deleteTema(id))
                throw new NotFoundException("Tema with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
