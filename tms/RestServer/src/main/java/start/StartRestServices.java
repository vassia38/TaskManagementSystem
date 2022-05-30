package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

@ComponentScan("tms")
@SpringBootApplication
public class StartRestServices {

    @Bean(name = "props")
    public static Properties createProps() {
        Properties props =new Properties();
        try {
            props.load(StartRestServices.class.getResourceAsStream("/tmsserver.properties"));
            System.out.println("Server properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find tmsserver.properties "+e);
        }
        return props;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StartRestServices.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "55557"));
        app.run(args);
        //SpringApplication.run(StartRestServices.class, args);
    }
}

