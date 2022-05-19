package cl.tbd.ejemplo1.repositories;
import java.util.List;
import cl.tbd.ejemplo1.models.Dog;
import cl.tbd.ejemplo1.models.Regionname;

public interface DogRepository {
    public int countDogs();
    public List<Dog> getAllDogs();
    public Dog createDog(Dog dog);
    public String getJson();
    public List<Dog> getAllDogsRegion(String region);
    public List<Regionname> getAllRegion();
}
