package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestasCollection;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.TemasCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by carlos on 2/04/16.
 */
public class RespuestaDAOImpl implements RespuestaDAO {

    @Override
    public Respuesta createRspuesta(String temaId, String userId, String contenido) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(RespuestaDAOQuery.CREATE_RESPUESTA);
            stmt.setString(1, id);
            stmt.setString(2, userId);
            stmt.setString(3, temaId);
            stmt.setString(4, contenido);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getResputestaById(id);
    }

    @Override
    public Respuesta getResputestaById(String id) throws SQLException {
        Respuesta respuesta = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.GET_RESPUESTA_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setUserid(rs.getString("userid"));
                respuesta.setTemaid(rs.getString("temaid"));
                respuesta.setContenido(rs.getString("contenido"));
                respuesta.setCreation_timestamp(rs.getString("creation_timestamp"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return respuesta;
    }

    @Override
    public RespuestasCollection getRespuestasFromTema(String temaId) throws SQLException {
        // Modelo a devolver
        RespuestasCollection respuestasCollection = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMAS_FROM_GRUPO);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, temaId);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                Respuesta respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setUserid(rs.getString("userid"));
                respuesta.setTemaid(rs.getString("temaid"));
                respuesta.setContenido(rs.getString("contenido"));
                respuesta.setCreation_timestamp(rs.getString("creation_timestamp"));
                respuestasCollection.getRespuestas().add(respuesta);
            }
        } catch (SQLException e) {
            // Relanza la excepción
            throw e;
        } finally {
            // Libera la conexión
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        // Devuelve el modelo
        return respuestasCollection;
    }

    @Override
    public Respuesta updateRespuesta(String id, String contenido) throws SQLException {
        Respuesta respuesta = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.UPDATE_RESPUESTAS);
            stmt.setString(1, contenido);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                respuesta = getResputestaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return respuesta;
    }

    @Override
    public boolean deleteRespuesta(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.DELETE_RESPUESTA);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
