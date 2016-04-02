package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestasCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 30/03/16.
 */
public interface RespuestaDAO {
    public Respuesta createRspuesta(String temaId, String userId, String contenido) throws SQLException;

    public Respuesta getResputestaById(String id) throws SQLException;

    public RespuestasCollection getRespuestasFromTema(String temaId) throws SQLException;

    public Respuesta updateRespuesta(String id, String contenido) throws SQLException;

    public boolean deleteRespuesta(String id) throws SQLException;


}
