package store.nightmarket.domain.itemweb.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.itemweb.fixture.TestFactory;
import store.nightmarket.domain.itemweb.model.state.PostState;

public class ProductPostTest {

	@Test
	@DisplayName("예약 시간이 지나면 게시 상태로 전이된다")
	void scheduledPostChangePublished() {
		// given
		ProductPost post = TestFactory.createProductPost(UUID.randomUUID());

		LocalDateTime publishAt = LocalDateTime.of(2025, 1, 1, 10, 0);
		post.schedule(publishAt, null);

		// when
		LocalDateTime now = publishAt.plusMinutes(1);
		post.refreshStatus(now);

		// then
		assertThat(post.getState()).isEqualTo(PostState.PUBLISHED);
	}

	@Test
	@DisplayName("게시 시간이 되기 전에는 예약 상태를 유지한다")
	void beforePublishTimeRemainsScheduled() {
		ProductPost post = TestFactory.createProductPost(UUID.randomUUID());

		LocalDateTime publishAt = LocalDateTime.of(2025, 1, 1, 10, 0);
		post.schedule(publishAt, null);

		LocalDateTime now = publishAt.minusMinutes(10);
		post.refreshStatus(now);

		assertThat(post.getState()).isEqualTo(PostState.SCHEDULED);
	}

	@Test
	@DisplayName("종료 시간이 지나면 게시가 종료된다")
	void postExpiredAtAfterExpireTime() {
		ProductPost post = TestFactory.createProductPost(UUID.randomUUID());

		LocalDateTime publishAt = LocalDateTime.of(2025, 1, 1, 10, 0);
		LocalDateTime expireAt = publishAt.plusHours(2);

		post.schedule(publishAt, expireAt);

		LocalDateTime now = expireAt.plusSeconds(1);
		post.refreshStatus(now);

		assertThat(post.getState()).isEqualTo(PostState.EXPIRED);
	}

	@Test
	@DisplayName("삭제된 게시글은 상태 갱신 대상이 아니다")
	void deletedPostIsNotRefreshed() {
		ProductPost post = TestFactory.createProductPost(UUID.randomUUID());

		post.delete();

		post.refreshStatus(LocalDateTime.now());

		assertThat(post.getState()).isEqualTo(PostState.DELETED);
	}

}
