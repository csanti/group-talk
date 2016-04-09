package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.RespuestaDAO;
import edu.upc.eetac.dsa.grouptalk.dao.RespuestaDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.GroupTalkMediaType;
import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;

/**
 * Created by carlos on 9/04/16.
 */
@Path("respuestas")
public class RespuestaResource {
    @Context
    private SecurityContext securityContext;

    @Path("/{idrespuesta}")
    @DELETE
    public void deleteRespuesta(@PathParam("idrespuesta") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        try {
            String ownerid = respuestaDAO.getResputestaById(id).getUserid();
            if(!userid.equals(ownerid) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            if(!respuestaDAO.deleteRespuesta(id))
                throw new NotFoundException("Respuesta with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_RESPUESTA)
    @Produces(GroupTalkMediaType.GROUPTALK_RESPUESTA)
    public Respuesta updateRespuesta(@PathParam("id") String id, Respuesta respuesta) {
        if(respuesta == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(respuesta.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();

        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        try {

            String ownerid = respuestaDAO.getResputestaById(id).getUserid();
            if(!userid.equals(ownerid) && !securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");

            respuesta = respuestaDAO.updateRespuesta(id, respuesta.getContenido());
            if(respuesta == null)
                throw new NotFoundException("Respuesta with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return respuesta;
    }
}
