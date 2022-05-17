package cl.tbd.ejemplo1.repositories;

import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class DatabaseContext {
    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.user}")
    private String dbUser;

    @Value("${database.password}")
    private String dbPass;

    // static String url = "jdbc:postgresql://127.0.0.1:5432/tarea-gis";
    // static String name = "postgres";
    // static String contra = "postgres";

    @Bean
    public static Sql2o sql2o() {
        return new Sql2o(dbUrl, dbUser, dbPass);
        // return new Sql2o(url, name, contra);
    }
}
