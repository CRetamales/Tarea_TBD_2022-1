package cl.tbd.ejemplo1.repositories;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class TablesContext {
    public Connection con;
    private Sql2o sql2o;

    public TablesContext(Connection con){
        this.con = con;
    } 

    public void crearTablas(){
        //CREACION DE TABLAS
        //DOG MODEL id,name, longitude, latitude
        con.createQuery("create table dog" + "(id serial primary key, name text, longitude NUMERIC(5,3), latitude NUMERIC(5,3)) ").executeUpdate();

        
    }
    
}
