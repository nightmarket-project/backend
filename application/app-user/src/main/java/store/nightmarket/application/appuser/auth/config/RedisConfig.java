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

		objectMapper.addMixIn(UsernamePasswordAuthenticationToken.class,
			UsernamePasswordAuthenticationTokenMixin.class);
		objectMapper.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(
			LaissezFaireSubTypeValidator.instance,
			ObjectMapper.DefaultTyping.NON_FINAL,
			JsonTypeInfo.As.PROPERTY
		);

		return new GenericJackson2JsonRedisSerializer(objectMapper);
	}

	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	@JsonIgnoreProperties({"authenticated"})
	public static abstract class UsernamePasswordAuthenticationTokenMixin {

		@JsonCreator
		public UsernamePasswordAuthenticationTokenMixin(
			@JsonProperty("principal") Object principal,
			@JsonProperty("credentials") Object credentials,
			@JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities) {
		}

	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	public static abstract class SimpleGrantedAuthorityMixin {

		@JsonCreator
		public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String authority) {
		}

	}

}
