package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.service.dto.EditReviewItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.EditReviewItemWebDomainServiceDto.Input;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.Rating;

class EditReviewItemWebDomainServiceTest {

    private EditReviewItemWebDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new EditReviewItemWebDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("리뷰 작성자와 요청한 사용자 ID가 같으면 리뷰가 수정된다")
    void shouldEditReviewWhenUserIdIsEqualToAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        Content content = new Content("bad!");
        Image image = TestObjectFactory.createImage("aaa");
        Rating rating = new Rating(0);
        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            "good!",
            TestObjectFactory.defaultImage(),
            5
        );

        Input input = Input.builder()
            .review(review)
            .content(content)
            .image(image)
            .rating(rating)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event.getReview().getContent()).isEqualTo(content);
        softly.assertThat(event.getReview().getRating()).isEqualTo(rating);
        softly.assertThat(event.getReview().getImage()).isEqualTo(image);
        softly.assertAll();
    }

    @Test
    @DisplayName("리뷰 작성자와 요청한 사용자 ID가 다르면 예외가 발생한다")
    void shouldThrowExceptionWhenUserIdIsDifferentFromAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        UserId ohterUserId = new UserId(UUID.randomUUID());
        Content content = new Content("bad!");
        Image image = TestObjectFactory.createImage("aaa");
        Rating rating = new Rating(0);
        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            "good!",
            TestObjectFactory.defaultImage(),
            5
        );

        Input input = Input.builder()
            .review(review)
            .content(content)
            .image(image)
            .rating(rating)
            .build();

        // when
        // then
        assertThatThrownBy(
            () -> service.execute(input)
        ).isInstanceOf(ItemWebException.class);
    }

}
