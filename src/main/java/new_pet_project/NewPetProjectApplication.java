package new_pet_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewPetProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewPetProjectApplication.class, args);
    }
}
