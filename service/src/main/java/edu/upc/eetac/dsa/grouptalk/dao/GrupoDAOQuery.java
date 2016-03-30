package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 30/03/16.
 */
public interface GrupoDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_GRUPO = "insert into grupos (id, nombre) values (UNHEX(?), ?)";
    public final static String GET_GRUPO_BY_ID = "select hex(g.id) as id, g.nombre, g.creation_timestamp from grupos g where g.id=unhex(?)";
    public final static String GET_GRUPOS = "select hex(g.id) as id, g.nombre, g.creation_timestamp from grupos as g";
    public final static String UPDATE_GRUPO = "update grupos set nombre=? where id=unhex(?)";
    public final static String DELETE_GRUPO = "delete from grupos where id = unhex(?)";
}
