package cl.tbd.ejemplo1.repositories;

import cl.tbd.ejemplo1.models.Dog;

import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DogRepositoryImp implements DogRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int countDogs() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM dog").executeScalar(Integer.class);
        }
        return total;
    }

    @Override
    public List<Dog> getAllDogs() {
        try(Connection conn = sql2o.open()){
            final String query = "SELECT id, name, st_x(st_astext( location)) AS longitude, st_y(st_astext(location)) AS latitude FROM dog;";
            return conn.createQuery(query)
                    .executeAndFetch(Dog.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Dog createDog(Dog dog) {
        try(Connection conn = sql2o.open()){
            String query = "INSERT INTO DOG (name, location) " +
            "VALUES (:dogName, ST_GeomFromText(:point, 4326))";

            String point = "POINT("+dog.getLongitude()+" "+dog.getLatitude()+")";
            System.out.println("point: "+point);
            
            int insertedId = (int) conn.createQuery(query, true)
                    .addParameter("dogName", dog.getName())
                    .addParameter("point", point)
                    .executeUpdate().getKey();
            dog.setId(insertedId);
            return dog;        
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @Override
    public String getJson() {
        // TODO Auto-generated method stub
        final String query = "SELECT json_build_object("+
            "'type', 'FeatureCollection',"+
            "'features', json_agg(ST_AsGeoJSON(t.geom)::json)"+
            ")"+
        "FROM division_regional_4326 AS t WHERE t.gid = 5;";
        return null;
    }
}
