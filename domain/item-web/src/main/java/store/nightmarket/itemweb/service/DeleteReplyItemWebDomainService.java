package store.nightmarket.itemweb.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.service.dto.DeleteReplyItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.DeleteReplyItemWebDomainServiceDto.Input;

public class DeleteReplyItemWebDomainService
    implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        Reply reply = input.getReply();
        UserId userId = input.getUserId();

        reply.delete(userId);

        return Event.builder()
            .reply(reply)
            .build();
    }

}
