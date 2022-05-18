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
        con.createQuery("CREATE EXTENSION IF NOT EXISTS postgis;").executeUpdate();
        con.createQuery("create table dog"
                + "(id serial primary key, name text, longitude NUMERIC(5,3), latitude NUMERIC(5,3),location geometry(Point, 4326));")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('juanito', -70.588441, -33.542294);")
                .executeUpdate();
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('blanca', -70.572635, -33.605311);")
                .executeUpdate();
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('cachupin', -70.652494, -33.585966);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('rocky', -70.557666, -33.406346);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('luna', -70.577235, -33.384298);")
                .executeUpdate();  

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('pelusa', -70.736007, -33.356336);")
                .executeUpdate();
        //Pomaire, melipilla, buin y tiltil
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('campeon', -71.151683, -33.649114);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('kiara', -71.210585, -33.686535);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('simba', -70.740557, -33.739214);")
                .executeUpdate();
        
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('corazon', -70.930115, -33.085271);")
                .executeUpdate();

        //Regiones
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('snoopy', -70.293878, -18.474162);")
                .executeUpdate();
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('leona', -71.512124, -31.916272);")
                .executeUpdate();
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('guardian', -71.457410, -31.397671);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('scoobydoo', -70.890551, -53.141895);")
                .executeUpdate();
        
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('pluto', -73.060376, -36.827106);")
                .executeUpdate();
           
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('milo', -70.142669, -20.236112);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('reina', -71.666043, -35.431311);")
                .executeUpdate();

        con.createQuery(
                "insert into dog(name,longitude,latitude) values('cobarde', -72.958259, -41.473424);")
                .executeUpdate();    
        
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('clifford', -72.227936, -39.283742);")
                .executeUpdate();   
        con.createQuery(
                "insert into dog(name,longitude,latitude) values('rouge', -71.615883, -33.046110);")
                .executeUpdate();   

        con.createQuery("UPDATE dog SET location = ST_MakePoint(longitude, latitude);").executeUpdate();


    }

}
