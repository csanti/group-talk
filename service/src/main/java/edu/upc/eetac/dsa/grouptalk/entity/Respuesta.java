package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by carlos on 30/03/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Respuesta {

    @InjectLinks({})
    private List<Link> links;

    private String id;
    private String userid;
    private String temaid;
    private String contenido;
    private String creation_timestamp;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTemaid() {
        return temaid;
    }

    public void setTemaid(String temaid) {
        this.temaid = temaid;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(String creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }
}
