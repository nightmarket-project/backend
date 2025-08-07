package store.nightmarket.application.appuser.auth.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {

	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		ObjectMapper objectMapper = new ObjectMapper();

		// Spring Security 관련 Mixin 등록
		objectMapper.addMixIn(UsernamePasswordAuthenticationToken.class,
			UsernamePasswordAuthenticationTokenMixin.class);
		objectMapper.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
		// objectMapper.addMixIn(SecurityContextImpl.class, SecurityContextMixin.class);
		// objectMapper.addMixIn(User.class, UserMixin.class);
		// objectMapper.addMixIn(Name.class, NameMixin.class);
		// objectMapper.addMixIn(Point.class, PointMixin.class);

		// JSON 에 있지만 Java 클래스에는 없는 속성이 있어도 에러를 발생시키지 않음
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// getter 나 setter 가 없으면 직렬화가 안되는데, private 필드에 직접 접근해서 직렬화/역직렬화 가능
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// JSON 에 클래스 타입 정보를 포함시켜서 정확한 타입으로 역직렬화
		objectMapper.activateDefaultTyping(
			// LaissezFaireSubTypeValidator → "모든 클래스 허용" (관대한 정책)
			// BasicPolymorphicTypeValidator → 특정 패키지/클래스만 허용 (엄격한 정책)
			// Spring Security 의 다양한 내부 클래스들이 사용되기 때문에 해당 정책 사용
			LaissezFaireSubTypeValidator.instance,
			ObjectMapper.DefaultTyping.NON_FINAL,
			JsonTypeInfo.As.PROPERTY
		);

		return new GenericJackson2JsonRedisSerializer(objectMapper);
	}

	// 클래스 속성 사용
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	// ANY의 경우 private 까지 접근 가능
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	@JsonIgnoreProperties({"authenticated"}) // authenticated 속성 무시
	public static abstract class UsernamePasswordAuthenticationTokenMixin {

		@JsonCreator
		public UsernamePasswordAuthenticationTokenMixin(
			@JsonProperty("principal") Object principal,
			@JsonProperty("credentials") Object credentials,
			@JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities) {
		}

	}

	// SimpleGrantedAuthority Mixin
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	public static abstract class SimpleGrantedAuthorityMixin {

		@JsonCreator
		public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String authority) {
		}

	}

	// // SecurityContext Mixin
	// @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	// public static abstract class SecurityContextMixin {
	//
	// 	@JsonCreator
	// 	public SecurityContextMixin(@JsonProperty("authentication") Authentication authentication) {
	// 	}
	//
	// }
	//
	// // User Mixin (Domain User 클래스용)
	// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	// public static abstract class UserMixin {
	//
	// 	@JsonCreator
	// 	public UserMixin(
	// 		@JsonProperty("name") Name name,
	// 		@JsonProperty("email") String email,
	// 		@JsonProperty("imageUrl") String imageUrl,
	// 		@JsonProperty("point") Point point,
	// 		@JsonProperty("role") UserRole role,
	// 		@JsonProperty("authProvider") AuthProvider authProvider,
	// 		@JsonProperty("providerId") String providerId
	// 	) {
	//
	// 	}
	// }
	//
	// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	// public static abstract class NameMixin {
	//
	// 	@JsonCreator
	// 	public NameMixin(@JsonProperty("value") String value) {
	// 	}
	// }
	//
	// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	// public static abstract class PointMixin {
	//
	// 	@JsonCreator
	// 	public PointMixin(@JsonProperty("value") Long value) {
	// 	}
	//
	// }

}
