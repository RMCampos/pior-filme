package awards.raspberry.golden;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "awards.raspberry.golden")
public class App {

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
