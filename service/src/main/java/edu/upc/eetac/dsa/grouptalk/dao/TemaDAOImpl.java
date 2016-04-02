package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.TemasCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by carlos on 2/04/16.
 */
public class TemaDAOImpl implements TemaDAO {
    @Override
    public Tema createTema(String userId, String grupoId, String titulo, String contenido) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(TemaDAOQuery.CREATE_TEMA);
            stmt.setString(1, id);
            stmt.setString(2, userId);
            stmt.setString(3, grupoId);
            stmt.setString(4, titulo);
            stmt.setString(5, contenido);
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
        return getTemaById(id);
    }

    @Override
    public Tema getTemaById(String id) throws SQLException {
        // Modelo a devolver
        Tema tema = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMA_BY_ID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                tema = new Tema();
                tema.setId(rs.getString("id"));
                tema.setUserid(rs.getString("userid"));
                tema.setGrupoid(rs.getString("grupoid"));
                tema.setTitulo(rs.getString("titulo"));
                tema.setContenido(rs.getString("contenido"));
                tema.setCreation_timestamp(rs.getString("creation_timestamp"));
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
        return tema;
    }

    @Override
    public Tema updateTema(String id, String contenido) throws SQLException {
        Tema tema= null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.UPDATE_TEMAS);
            stmt.setString(1, contenido);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                tema = getTemaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return tema;
    }

    @Override
    public TemasCollection getTemasFromGrupo(String grupoId) throws SQLException {
        // Modelo a devolver
        TemasCollection temasCollection = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMAS_FROM_GRUPO);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, grupoId);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                Tema tema = new Tema();
                tema.setId(rs.getString("id"));
                tema.setUserid(rs.getString("userid"));
                tema.setGrupoid(rs.getString("grupoid"));
                tema.setTitulo(rs.getString("titulo"));
                tema.setContenido(rs.getString("contenido"));
                tema.setCreation_timestamp(rs.getString("creation_timestamp"));
                temasCollection.getTemas().add(tema);
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
        return temasCollection;
    }

    @Override
    public boolean deleteTema(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.DELETE_TEMA);
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
