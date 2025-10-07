package vn.kltn.KLTN.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloudfly")
public class CloudFlyProperties {
	private String accessKey;
	private String secretKey;
	private String endpoint;
	private String region;
	private String bucketName;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	@Override
	public String toString() {
		return "CloudFlyProperties [accessKey=" + accessKey + ", secretKey=" + secretKey + ", endpoint=" + endpoint
				+ ", region=" + region + ", bucketName=" + bucketName + "]";
	}
}
