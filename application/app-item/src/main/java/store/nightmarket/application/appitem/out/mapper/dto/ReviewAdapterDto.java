package store.nightmarket.application.appitem.out.mapper.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.out.mapper.ReviewMapper;
import store.nightmarket.application.appitem.out.mapper.UserMapper;
import store.nightmarket.domain.item.model.User;
import store.nightmarket.domain.itemweb.model.Review;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

@Builder
@Getter
public class ReviewAdapterDto {

	private final Review review;
	private final ReplyAdapterDto replyAdapterDto;
	private final User user;

	public static ReviewAdapterDto toDomain(ReviewEntity entity) {
		return ReviewAdapterDto.builder()
			.review(ReviewMapper.toDomain(entity))
			.replyAdapterDto(ReplyAdapterDto.toDomain(entity.getReplyEntity()))
			.user(UserMapper.toDomain(entity.getUserEntity()))
			.build();
	}

}
