package cl.ey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableKafka No es nec4esario para el funcionamiento de KAFKA
public class EvaluacionEy extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(EvaluacionEy.class, args);
	}

}
