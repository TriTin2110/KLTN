package vn.kltn.KLTN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (exclude = SecurityAutoConfiguration.class)
public class KltnApplication {

	public static void main(String[] args) {
		SpringApplication.run(KltnApplication.class, args);
	}

}
