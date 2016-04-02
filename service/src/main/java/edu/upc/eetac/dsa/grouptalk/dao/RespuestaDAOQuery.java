package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 30/03/16.
 */
public interface RespuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_RESPUESTA = "insert into respuestas (id, userid, temaid, contenido) values (UNHEX(?), unhex(?), unhex(?), ?)";
    public final static String GET_RESPUESTA_BY_ID = "select hex(r.id) as id, hex(r.userid) as userid, hex(r.temaid) as temaid, r.contenido, r.creation_timestamp, u.fullname from respuestas r, users u where r.id=unhex(?) and u.id=r.userid";
    public final static String GET_RESPUESTAS_FROM_TEMA = "select hex(id) as id, hex(userid) as userid, hex(temaid), contenido, creation_timestamp from stings where temaid=unhex(?) order by creation_timestamp desc";
    public final static String UPDATE_RESPUESTAS = "update respuestas set contenido=? where id=unhex(?) ";
    public final static String DELETE_RESPUESTA = "delete from respuestas where id=unhex(?)";
}
