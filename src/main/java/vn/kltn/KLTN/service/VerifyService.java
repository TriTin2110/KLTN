package vn.kltn.KLTN.service;

public interface VerifyService {
	public void sendMail(String to);

	public boolean checkVerifyCode(String email, String code);
}
