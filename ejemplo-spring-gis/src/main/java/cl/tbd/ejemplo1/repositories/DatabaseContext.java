package cl.tbd.ejemplo1.repositories;

import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;

@Configuration
@Component
public class DatabaseContext {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;

    //static String dbUrl1 = "jdbc:postgresql://127.0.0.1:5432/tarea";
    //static String dbUser = "postgres";
    //static String dbPass = "postgres";

    @Bean
    public static Sql2o sql2o() {
        return new Sql2o(dbUrl, dbUser, dbPass);
    }
    @Autowired
    public void GetProperties(@Value("${database.url}") String dbUrl, @Value("${database.user}") String dbUser, @Value("${database.password}") String dbPass) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }
}
