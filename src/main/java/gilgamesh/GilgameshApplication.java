package gilgamesh;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gilgamesh API", version = "1.0.0", description = "API para documentação automática de código."))
public class GilgameshApplication {

    public static void main(String[] args) {
        SpringApplication.run(GilgameshApplication.class, args);
    }

}