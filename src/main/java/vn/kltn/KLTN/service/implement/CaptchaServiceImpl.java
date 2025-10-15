package vn.kltn.KLTN.service.implement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import vn.kltn.KLTN.service.CaptchaService;

@Service
public class CaptchaServiceImpl implements CaptchaService {
	@Value("${google.recaptcha.secret}")
	private String secretKey;
	@Value("${google.recaptcha.verify.url}")
	private String recaptchaVerifyUrl;

	@Override
	public boolean verify(String captchaResponse) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("secret", secretKey);
		params.add("response", captchaResponse);
		Map response = restTemplate.postForObject(recaptchaVerifyUrl, params, Map.class);
		return (Boolean) response.get("success");
	}

}
