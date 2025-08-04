package store.nightmarket.application.appuser.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MockOAuthRequest {

	private String email;
	private String name;
	private String provider; // "google", "apple" 등
	
}