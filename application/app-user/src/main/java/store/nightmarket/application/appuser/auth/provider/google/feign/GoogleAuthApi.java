package store.nightmarket.application.appuser.auth.provider.google.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.nightmarket.application.appuser.auth.provider.google.feign.dto.GoogleAccessTokenDto;

@FeignClient(name = "googleAuth", url = "https://oauth2.googleapis.com")
public interface GoogleAuthApi {

	@PostMapping(value = "/token")
	GoogleAccessTokenDto.Response getAccessToken(@RequestBody GoogleAccessTokenDto.Request request);

}
