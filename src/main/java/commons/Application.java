package commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by manas on 8/26/2017.
 * Runner
 */
@SpringBootApplication
public class Application {

    /**
     * @param args
     * Runner for running on localhost
     */
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
