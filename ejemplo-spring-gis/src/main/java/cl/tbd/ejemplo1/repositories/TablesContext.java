package cl.tbd.ejemplo1.repositories;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class TablesContext {
    public Connection con;

    public TablesContext(Connection con) {
        this.con = con;
    }

    public void crearTablas() {
        // CREACION DE TABLAS
        // DOG MODEL id,name, longitude, latitude
        con.createQuery("create table if not exists dog"
                + "(id serial primary key, name text, longitude NUMERIC(5,3), latitude NUMERIC(5,3));")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('juanito', -70.588441, -33.542294);")
                .executeUpdate();

    }

}
