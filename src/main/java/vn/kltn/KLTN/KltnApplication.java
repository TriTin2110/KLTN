package vn.kltn.KLTN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

// exclude = RedisAutoConfiguration.class Tắt cổng mặc định của redis
@SpringBootApplication(exclude = RedisAutoConfiguration.class) // (exclude = SecurityAutoConfiguration.class)
public class KltnApplication {

	public static void main(String[] args) {
		SpringApplication.run(KltnApplication.class, args);
	}

}
