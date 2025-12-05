package store.nightmarket.application.appuser.auth.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import store.nightmarket.application.appuser.auth.constant.Constant;
import store.nightmarket.application.appuser.auth.provider.LoginUrlProvider;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController {

	private final Map<String, LoginUrlProvider> urlProviderMap;
	private final RedisTemplate<String, String> redisTemplate;

	public OAuthController(List<LoginUrlProvider> urlProviderList, RedisTemplate<String, String> redisTemplate) {
		this.urlProviderMap = urlProviderList.stream()
			.collect(Collectors.toMap(
				LoginUrlProvider::getProviderName,
				urlProvider -> urlProvider
			));
		this.redisTemplate = redisTemplate;
	}

	@GetMapping("/authorization/{oauth}")
	public void redirectToSocialLogin(
		HttpServletResponse response,
		@PathVariable("oauth") String oauth
	) throws IOException {
		LoginUrlProvider loginUrlProvider = urlProviderMap.get(oauth.toUpperCase());
		String loginUrl = loginUrlProvider.provide();

		redisTemplate.opsForValue().set(Constant.SESSION_STATE, loginUrlProvider.getState());
		response.sendRedirect(loginUrl);
	}

	@GetMapping("/session")
	public ResponseEntity<?> checkLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Object sessionAttr = session.getAttribute(Constant.SESSION_KEY);
		if (sessionAttr == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
