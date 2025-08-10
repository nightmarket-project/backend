package store.nightmarket.application.appuser.auth.model.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import store.nightmarket.application.appuser.auth.dto.GoogleUserDto;

@FeignClient(value = "googleUser", url = "https://www.googleapis.com")
public interface GoogleUserApi {

	@GetMapping("/oauth2/v3/userinfo")
	GoogleUserDto getUserInfo(@RequestHeader("Authorization") String authorization);

}