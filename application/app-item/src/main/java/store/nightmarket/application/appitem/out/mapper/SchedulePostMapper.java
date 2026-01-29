package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.domain.itemweb.model.SchedulePost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.persistence.persistitem.entity.model.SchedulePostEntity;

public class SchedulePostMapper {

	public static SchedulePost toDomain(SchedulePostEntity entity) {
		return SchedulePost.newInstanceWithCreatedAt(
			new SchedulePostId(entity.getId()),
			entity.getCreatedAt(),
			new ProductPostId(entity.getProductPostId()),
			entity.getScheduledAt(),
			entity.getState()
		);
	}

	public static SchedulePostEntity toEntity(SchedulePost domain) {
		return SchedulePostEntity.newInstanceWithCreatedAt(
			domain.getSchedulePostId().getId(),
			domain.getCreatedAt(),
			domain.getProductPostId().getId(),
			domain.getScheduledAt(),
			domain.getState()
		);
	}

}
