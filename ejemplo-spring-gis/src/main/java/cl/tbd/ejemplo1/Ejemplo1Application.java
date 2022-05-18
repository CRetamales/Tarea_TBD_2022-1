package cl.tbd.ejemplo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import cl.tbd.ejemplo1.repositories.DatabaseContext;
import cl.tbd.ejemplo1.repositories.TablesContext;

import org.sql2o.Connection;

@SpringBootApplication
@RestController
public class Ejemplo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ejemplo1Application.class, args);
		Sql2o sql2o = DatabaseContext.sql2o();
		try {
			Connection con = sql2o.open();
			
			TablesContext tablas = new TablesContext(con);
			try{
				tablas.crearTablas();
				System.out.println("Se creo la tabla dog");
			}catch(Sql2oException e){
				System.out.println("La tabla dog ya existia");
			}
			System.out.println("si funciona");
		} catch (Sql2oException e) {
			System.out.println("no funciona");
		}
	}

}
