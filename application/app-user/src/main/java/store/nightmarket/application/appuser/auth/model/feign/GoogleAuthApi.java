package store.nightmarket.application.appuser.auth.model.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.nightmarket.application.appuser.auth.dto.GoogleAccessTokenDto;

@Component
@FeignClient(name = "googleAuth", url = "https://oauth2.googleapis.com")
public interface GoogleAuthApi {

	@PostMapping(value = "/token")
	GoogleAccessTokenDto.Response getAccessToken(@RequestBody GoogleAccessTokenDto.Request request);

}
