package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.ReviewMapper;
import store.nightmarket.application.appitem.mapper.UserMapper;
import store.nightmarket.domain.item.model.User;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

@Builder
@Getter
public class ReviewDto {

	private final Review review;
	private final ReplyDto replyDto;
	private final User user;

	public static ReviewDto toDomain(ReviewEntity entity) {
		return ReviewDto.builder()
			.review(ReviewMapper.toDomain(entity))
			.replyDto(ReplyDto.toDomain(entity.getReplyEntity()))
			.user(UserMapper.toDomain(entity.getUserEntity()))
			.build();
	}

}
