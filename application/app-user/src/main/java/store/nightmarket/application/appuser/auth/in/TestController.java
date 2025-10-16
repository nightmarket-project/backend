package store.nightmarket.application.appuser.auth.in;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appuser.auth.dto.MockOAuthRequest;
import store.nightmarket.application.appuser.user.out.ReadUserPort;
import store.nightmarket.application.appuser.user.out.SaveUserPort;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.valueobject.AuthProvider;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.domain.user.valueobject.UserId;
import store.nightmarket.domain.user.valueobject.UserRole;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

	private final ReadUserPort readUserPort;
	private final SaveUserPort saveUserPort;

	@PostMapping("/login")
	public ResponseEntity<?> mockOAuthLogin(@RequestBody MockOAuthRequest request,
		HttpServletRequest httpRequest) {
		try {
			// 가짜 사용자 정보로 세션 생성
			User mockUser = createMockUser(request);

			Authentication authentication = createMockAuthentication(mockUser);

			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);

			HttpSession session = httpRequest.getSession(true);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
			session.setAttribute("userId", mockUser.getUserId().getId());
			session.setMaxInactiveInterval(3600);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Mock login successful");
			response.put("sessionId", session.getId());
			response.put("user", mockUser);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
				.body(Map.of("error", "Mock login failed: " + e.getMessage()));
		}
	}

	@GetMapping("/auth-check")
	public ResponseEntity<?> check() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(auth.getAuthorities());
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	private User createMockUser(MockOAuthRequest request) {

		return readUserPort.readByEmail(request.getEmail())
			.orElseGet(() -> {
				User newUser = User.newInstance(
					new UserId(UUID.randomUUID()),
					new Name(request.getName()),
					request.getEmail(),
					"image.url",
					new Point(0L),
					UserRole.valueOf(request.getRole()),
					AuthProvider.valueOf(request.getProvider()),
					"test_" + System.currentTimeMillis()
				);
				return saveUserPort.save(newUser);
			});
	}

	private Authentication createMockAuthentication(User user) {
		return new UsernamePasswordAuthenticationToken(
			user,
			null,
			Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
		);
	}

}

