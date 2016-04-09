package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.GruposCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 30/03/16.
 */
public interface GrupoDAO {
    public Grupo createGrupo(String nombre) throws SQLException;

    public Grupo updateGrupo(String id, String nombre) throws SQLException;

    public Grupo getGrupoById(String id) throws SQLException;

    public boolean deleteGrupo(String id) throws SQLException;

    public GruposCollection getGrupos() throws SQLException;

    public void joinGrupo(String userid, String grupoid) throws SQLException;

    public boolean leaveGrupo(String userid, String grupoid) throws SQLException;

    public boolean checkMembership(String userid, String grupoid) throws SQLException;
}
