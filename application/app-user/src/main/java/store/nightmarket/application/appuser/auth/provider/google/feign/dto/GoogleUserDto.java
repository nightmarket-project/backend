package store.nightmarket.application.appuser.auth.provider.google.feign.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserDto {

	private String sub; //v3에서 사용자의 ID를 의미
	private String email;
	private String name;
	private String picture;

}
