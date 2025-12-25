package AsMoney;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "AsMooney",
                description = "API for  finances managing",
                version = "1"
        )
)
public class AsMoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsMoneyApplication.class, args);
    }

}
