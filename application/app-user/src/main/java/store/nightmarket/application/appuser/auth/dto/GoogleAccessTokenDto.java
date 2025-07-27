package store.nightmarket.application.appuser.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class GoogleAccessTokenDto {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Request {
		private String code;
		private String client_id;
		private String clientSecret;
		private String redirect_uri;
		private String grant_type;
	}

	@Getter
	@Setter
	public static class Response {
		@JsonProperty("access_token")
		private String accessToken;

		@JsonProperty("expires_in")
		private Integer expiresIn;

		@JsonProperty("refresh_token")
		private String refreshToken;

		@JsonProperty("scope")
		private String scope;

		@JsonProperty("token_type")
		private String tokenType;

		@JsonProperty("id_token")
		private String idToken;
	}

}
