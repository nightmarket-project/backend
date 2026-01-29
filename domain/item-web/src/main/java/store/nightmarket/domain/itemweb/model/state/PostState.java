package store.nightmarket.domain.itemweb.model.state;

import java.util.EnumSet;
import java.util.Set;

public enum PostState {
	UNPUBLISHED,  // 게시안됨
	PUBLISHED,    // 게시중
	DELETED;      // 삭제됨

	private Set<PostState> nextStates;

	static {
		UNPUBLISHED.nextStates = EnumSet.of(PUBLISHED, DELETED);
		PUBLISHED.nextStates = EnumSet.of(UNPUBLISHED, DELETED);
		DELETED.nextStates = EnumSet.noneOf(PostState.class);
	}

	public boolean canTransitionTo(PostState target) {
		return nextStates.contains(target);
	}

}