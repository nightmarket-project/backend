package store.nightmarket.domain.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.Reply;
import store.nightmarket.domain.itemweb.service.dto.EditReplyItemWebDomainServiceDto.Event;
import store.nightmarket.domain.itemweb.service.dto.EditReplyItemWebDomainServiceDto.Input;
import store.nightmarket.domain.itemweb.valueobject.CommentText;

public class EditReplyItemWebDomainService
	implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		Reply reply = input.getReply();
		UserId userId = input.getUserId();
		CommentText commentText = input.getCommentText();

		reply.edit(userId, commentText);

		return Event.builder()
			.reply(reply)
			.build();
	}

}
