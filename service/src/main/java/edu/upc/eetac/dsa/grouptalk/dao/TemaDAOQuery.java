package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 30/03/16.
 */
public interface TemaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TEMA = "insert into temas (id, userid, grupoid, titulo, contenido) values (UNHEX(?), unhex(?), unhex(?), ?, ?)";
    public final static String GET_TEMA_BY_ID = "select hex(t.id) as id, hex(t.userid) as userid, hex(t.grupoid) as grupoid, t.titulo, t.contenido, t.creation_timestamp, u.fullname from temas t, users u where t.id=unhex(?) and u.id=t.userid";
    public final static String GET_TEMAS_FROM_GRUPO = "select hex(id) as id, hex(userid) as userid, hex(grupoid), titulo, contenido, creation_timestamp from temas WHERE grupoid=unhex(?) order by creation_timestamp desc ";
    public final static String UPDATE_TEMAS = "update temas set contenido=?, where id=unhex(?) ";
    public final static String DELETE_TEMA = "delete from temas where id=unhex(?)";
}
