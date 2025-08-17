package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.ReplyMapper;
import store.nightmarket.application.appitem.mapper.ReviewMapper;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

@Builder
@Getter
public class ReviewDto {

	private final Review review;
	private final Reply reply;

	public static ReviewDto toDomain(ReviewEntity entity) {
		return ReviewDto.builder()
			.review(ReviewMapper.toDomain(entity))
			.reply(ReplyMapper.toDomain(entity.getReplyEntity()))
			.build();
	}

}
