package store.nightmarket.domain.itemweb.service;

import static store.nightmarket.domain.itemweb.service.dto.ScheduleProductPostDomainServiceDto.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.itemweb.fixture.TestFactory;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.state.PostState;

public class ScheduleProductPostDomainServiceTest {

	private ScheduleProductPostDomainService service;
	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		service = new ScheduleProductPostDomainService();
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("서비스 실행시 예약 된다.")
	void shouldScheduleWhenExecuted() {
		// given
		ProductPost productPost = TestFactory.createProductPost(UUID.randomUUID());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime publishTime = now.plusDays(10);
		LocalDateTime expiredTime = now.plusDays(20);

		Input input = Input.builder()
			.productPost(productPost)
			.publishAt(publishTime)
			.expiredAt(expiredTime)
			.build();

		// when
		Event event = service.execute(input);

		// then
		softly.assertThat(event).isNotNull();
		softly.assertThat(event.getProductPost().getState()).isEqualTo(PostState.SCHEDULED);
		softly.assertThat(event.getProductPost().getPublishAt()).isEqualTo(publishTime);
		softly.assertThat(event.getProductPost().getExpiredAt()).isEqualTo(expiredTime);
		softly.assertAll();
	}

}
