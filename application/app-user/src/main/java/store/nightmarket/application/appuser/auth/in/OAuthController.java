package store.nightmarket.application.appuser.auth.in;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.dto.OAuthProviderProperties;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthProviderProperties properties;

	@GetMapping("/authorization/{oauth}")
	public void redirectToSocialLogin(HttpServletResponse response, HttpSession session,
		@PathVariable("oauth") String oauth) throws IOException {
		String state = generateState();

		String authorizationUrl = properties.getProviders().get(oauth).getSocialLoginUri() +
			"?client_id=" + properties.getProviders().get(oauth).getClientId() +
			"&redirect_uri=" + properties.getProviders().get(oauth).getRedirectUri() +
			"&response_type=code" +
			"&scope=email profile" +
			"&state=" + state;

		session.setAttribute("session_state", state);
		session.setMaxInactiveInterval(300);

		response.sendRedirect(authorizationUrl);
	}

	@GetMapping("/session")
	public ResponseEntity<?> checkLogin(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Object sessionAttr = session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (sessionAttr == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private String generateState() {
		return UUID.randomUUID().toString();
	}

}
