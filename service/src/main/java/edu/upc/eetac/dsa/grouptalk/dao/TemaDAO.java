package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.TemasCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 30/03/16.
 */
public interface TemaDAO {
    public Tema createTema(String userId, String grupoId, String titulo, String contenido) throws SQLException;

    public Tema updateTema(String id, String contenido) throws SQLException;

    public TemasCollection getTemasFromGrupo(String grupoId) throws SQLException;

    public boolean deleteTema(String id) throws SQLException;

    public Tema getTemaById(String id) throws SQLException;
}
