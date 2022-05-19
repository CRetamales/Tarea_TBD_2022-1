package cl.tbd.ejemplo1.services;

import cl.tbd.ejemplo1.models.Dog;
import cl.tbd.ejemplo1.models.Resultados;
import cl.tbd.ejemplo1.models.Regionname;
import cl.tbd.ejemplo1.repositories.DogRepository;

import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class DogService {

    private final DogRepository dogRepository;

    DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        System.out.println("getAllDogs()");
        try {
            List<Dog> dogs = dogRepository.getAllDogs();
            System.out.println("1");
            for (Dog d : dogs) {
                System.out.println("ID: " + d.getId());
                /*PGgeometry geom = d.getLocation();
                Geometry geometry = geom.getGeometry();
                
                System.out.println("Point: "+ geometry.getValue());*/
            }
            System.out.println("2");
            return dogs;
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }
         
    }

    @GetMapping("/dogs/regions")
    public List<Regionname> getAllRegion()
    {
        return dogRepository.getAllRegion();
    }

    @GetMapping("/dogs/count")
    public String countDogs(){
        int total = dogRepository.countDogs();
        return String.format("Tienes %s perros!!", total);
    }

    @PostMapping("/dogs")
    @ResponseBody
    public Dog createDog(@RequestBody Dog dog){
        Dog result = dogRepository.createDog(dog);
        return result;
    }

    @RequestMapping(value = "/dogs/getAllDogsRegion/{cod_regi}", method = RequestMethod.GET)
    public List<Dog> getAllDogsRegion(@PathVariable int cod_regi){
        return dogRepository.getAllDogsRegion(cod_regi);
    }
    
    @RequestMapping(value = "/dogs/getDogsByNameLimit/{name}/{limit}", method = RequestMethod.GET)
    public List<Dog> getDogsByNameLimit(@PathVariable String name, @PathVariable int limit){
        return dogRepository.getDogsByNameLimit(name, limit);
    }

    @RequestMapping(value = "/dogs/getDogsByRadio/{name}/{radio}", method = RequestMethod.GET)
    public List<Dog> getDogsByRadio(@PathVariable String name, @PathVariable int radio){
        return dogRepository.getDogsByRadio(name, radio);
    }
}
