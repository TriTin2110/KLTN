package vn.kltn.KLTN.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.kltn.KLTN.service.CaptchaService;

@Component
public class CaptchaConfig extends OncePerRequestFilter {
	@Value("${recaptcha.enabled}")
	private boolean captchaEnable;
	private CaptchaService service;

	@Autowired
	public CaptchaConfig(CaptchaService service) {
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if ("/authenticateTheUser".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
			if (captchaEnable) {
				String captchaResponse = request.getParameter("g-recaptcha-response");
				if (!service.verify(captchaResponse)) {
					response.sendRedirect("/user/sign-in?captchaError=true");
					return;
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
