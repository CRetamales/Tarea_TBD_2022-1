package cl.tbd.ejemplo1.repositories;
import java.util.List;
import cl.tbd.ejemplo1.models.Dog;
import cl.tbd.ejemplo1.models.Resultados;
import cl.tbd.ejemplo1.models.Regionname;

public interface DogRepository {
    public int countDogs();
    public List<Dog> getAllDogs();
    public Dog createDog(Dog dog);
    public String getJson();
    public List<Dog> getAllDogsRegion(int cod_regi);
    public List<Regionname> getAllRegion();
    public List<Dog> getDogsByNameLimit (String nombrePerro, int cantidadPerros);
    public List<Dog> getDogsByRadio(String nombrePerro, int radio);
}
