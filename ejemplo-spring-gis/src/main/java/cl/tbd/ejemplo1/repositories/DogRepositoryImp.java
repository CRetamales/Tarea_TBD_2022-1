package cl.tbd.ejemplo1.repositories;

import cl.tbd.ejemplo1.models.Dog;
import cl.tbd.ejemplo1.models.Resultados;
import cl.tbd.ejemplo1.models.Regionname;

import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


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
    public List<Regionname> getAllRegion(){
        try(Connection conn = sql2o.open())
        {
            String query = "SELECT DISTINCT nom_reg, cod_regi FROM division_regional WHERE nom_reg IS NOT NULL AND NOT nom_reg = 'Zona sin demarcar' ORDER BY nom_reg ASC;";
            return conn.createQuery(query).executeAndFetch(Regionname.class);
        }catch (Exception e) {
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
    /*
    @Override
    public List<Dog> getAllDogsRegion(int cod_regi){
        
        try(Connection conn = sql2o.open())
        {
            
            final String query = "SELECT dog.id, dog.name, dog.longitude, dog.latitude, dog.location FROM dog JOIN division_regional as regiones ON ST_Intersects(regiones.geom, ST_Transform(dog.location, 32719)) WHERE regiones.cod_regi=13;";
            
            return conn.createQuery(query)
                .addParameter("region",cod_regi)
                .executeAndFetch(Dog.class);
        
            
            System.out.println("Hola Mundo");
            List<Dog> a = conn.createQuery(query).executeAndFetch(Dog.class);
            System.out.println("Hola Mundo");
            return a;
            

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    */

    @Override
    public List<Dog> getAllDogsRegion(int cod_regi) {
        try(Connection conn = sql2o.open()){
            final String query = "SELECT dog.id, dog.name, dog.longitude, dog.latitude  FROM dog JOIN division_regional as regiones ON ST_Intersects(regiones.geom, ST_Transform(dog.location, 32719)) WHERE regiones.cod_regi=:valor;";
            return conn.createQuery(query)
                    .addParameter("valor",cod_regi)
                    .executeAndFetch(Dog.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public List<Dog> getDogsByNameLimit(String nombrePerro, int cantidadPerros) {
        try(Connection conn = sql2o.open()){
            String query2 = "SELECT id, name, longitude, latitude FROM dog WHERE name=:nombrePerro";

            String query = "Select id, nombre as name, longitude, latitude from (SELECT d2.id AS id, d2.name AS nombre, d2.latitude AS latitude, d2.longitude AS longitude, (SELECT ST_Distance(ST_GeogFromText('SRID=4326;POINT('||d1.latitude||' '||d1.longitude||')'),ST_GeogFromText('SRID=4326;POINT('||d2.latitude||' '||d2.longitude||')'))) as distancia FROM dog AS d1 JOIN dog AS d2 ON d1.id <> d2.id WHERE d1.name=:nombrePerro ORDER BY distancia LIMIT :cantidadPerros) as resultado;";
            
            List<Dog> a1 = conn.createQuery(query)
                .addParameter("nombrePerro",nombrePerro)
                .addParameter("cantidadPerros",cantidadPerros)
                .executeAndFetch(Dog.class);
                
            List<Dog> a2 = conn.createQuery(query2)
                .addParameter("nombrePerro",nombrePerro)
                .executeAndFetch(Dog.class);

            
            List<Dog> resultado = new ArrayList<Dog>();

            resultado.addAll(a1);
            resultado.addAll(a2);

            return resultado;

        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public List<Dog> getDogsByRadio(String nombrePerro, int radio){
        try(Connection conn = sql2o.open()){
            String query2 = "SELECT id, name, longitude, latitude FROM dog WHERE name=:nombrePerro";

            String query = "Select id, nombre as name, longitude, latitude FROM (SELECT d1.id AS id1, d2.id AS id, d2.name AS nombre, d2.latitude AS latitude, d2.longitude AS longitude, (SELECT ST_Distance(ST_GeogFromText('SRID=4326;POINT('||d1.latitude||' '||d1.longitude||')'),ST_GeogFromText('SRID=4326;POINT('||d2.latitude||' '||d2.longitude||')'))) AS distancia FROM dog AS d1 JOIN dog AS d2 ON d1.id <> d2.id WHERE d1.name=:nombrePerro ORDER BY distancia) AS resultados WHERE distancia <= :radio;";
            
            List<Dog> a1 = conn.createQuery(query)
                .addParameter("nombrePerro",nombrePerro)
                .addParameter("radio",radio)
                .executeAndFetch(Dog.class);
                
            List<Dog> a2 = conn.createQuery(query2)
                .addParameter("nombrePerro",nombrePerro)
                .executeAndFetch(Dog.class);

            
            List<Dog> resultado = new ArrayList<Dog>();

            resultado.addAll(a1);
            resultado.addAll(a2);

            return resultado;

        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
