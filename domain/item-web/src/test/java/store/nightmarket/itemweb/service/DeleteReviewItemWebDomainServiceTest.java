package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.service.dto.DeleteReviewItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.DeleteReviewItemWebDomainServiceDto.Input;

class DeleteReviewItemWebDomainServiceTest {

    private DeleteReviewItemWebDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new DeleteReviewItemWebDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("서비스 실행 시 리뷰를 삭제 처리하고 내용은 '삭제된 댓글 입니다.'로 변경한다")
    void shouldSoftDeleteReviewAndMaskContentWhenExecuted() {
        // given
        UUID authorId = UUID.randomUUID();
        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            "good!",
            TestObjectFactory.defaultImage(),
            5
        );

        Input input = Input.builder()
            .userId(new UserId(authorId))
            .review(review)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event).isNotNull();
        softly.assertThat(event.getReview().isDeleted()).isTrue();
        softly.assertThat(event.getReview().getContent().getText()).isEqualTo("삭제된 댓글 입니다.");
        softly.assertAll();
    }

    @Test
    @DisplayName("요청한 사용자 ID가 리뷰 작성자와 다르면 예외가 발생한다")
    void shouldThrowExceptionWhenUserIdDoesNotMatchAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        UUID otherPersonId = UUID.randomUUID();
        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            "good!",
            TestObjectFactory.defaultImage(),
            5
        );

        Input input = Input.builder()
            .userId(new UserId(otherPersonId))
            .review(review)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ItemWebException.class);
    }

}
