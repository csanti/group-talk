package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 30/03/16.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER = "insert into users (id, loginid, password, email, fullname) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?);";
    public final static String UPDATE_USER = "update users set email=?, fullname=? where id=unhex(?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), ?)";
    public final static String GET_USER_BY_ID = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where id=unhex(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where u.loginid=?";
    public final static String DELETE_USER = "delete from users where id=unhex(?)";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
    public final static String JOIN_GRUPO = "insert into user_grupo (userid, grupoid) values (UNHEX(?), UNHEX(?))";
    public final static String LEAVE_GRUPO = "delete from user_grupo where userid=UNHEX(?) AND grupoid=UNHEX(?)";
}
