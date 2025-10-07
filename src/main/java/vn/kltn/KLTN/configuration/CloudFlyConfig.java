package vn.kltn.KLTN.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

@Configuration
public class CloudFlyConfig {
	@Value("${cloudfly.accessKey}")
	private String ACCESS_KEY;
	@Value("${cloudfly.secretKey}")
	private String SECRET_KEY;
	@Value("${cloudfly.endpoint}")
	private String END_POINT;

	@Bean
	public AmazonS3 createAmazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonS3 client = new AmazonS3Client(credentials);
		client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
		client.setEndpoint(END_POINT);
		return client;
	}
}
