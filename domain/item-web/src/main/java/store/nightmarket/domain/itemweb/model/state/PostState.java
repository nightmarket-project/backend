package store.nightmarket.domain.itemweb.model.state;

import java.util.EnumSet;
import java.util.Set;

public enum PostState {
	DRAFT,        // 임시저장
	SCHEDULED,    // 예약됨
	PUBLISHED,    // 게시중
	EXPIRED,    // 게시 종료
	DELETED;        // 삭제됨

	private Set<PostState> nextStates;

	static {
		DRAFT.nextStates = EnumSet.of(SCHEDULED, PUBLISHED, DELETED);
		SCHEDULED.nextStates = EnumSet.of(PUBLISHED, DELETED);
		PUBLISHED.nextStates = EnumSet.of(EXPIRED, DELETED);
		EXPIRED.nextStates = EnumSet.of(DELETED);
		DELETED.nextStates = EnumSet.noneOf(PostState.class);
	}

	public boolean canTransitionTo(PostState target) {
		return nextStates.contains(target);
	}

}