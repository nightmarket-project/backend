package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.out.mapper.ReplyMapper;
import store.nightmarket.application.appitem.out.mapper.UserMapper;
import store.nightmarket.domain.item.model.User;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.persistence.persistitem.entity.model.ReplyEntity;

@Getter
@Builder
public class ReplyAdapterDto {

	private final Reply reply;
	private final User user;

	public static ReplyAdapterDto toDomain(ReplyEntity entity) {
		return ReplyAdapterDto.builder()
			.reply(ReplyMapper.toDomain(entity))
			.user(UserMapper.toDomain(entity.getUserEntity()))
			.build();
	}

}
