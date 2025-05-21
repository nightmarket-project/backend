package store.nightmarket.common.domain.model;

import java.util.Objects;

public abstract class BaseId<Type> {

	public abstract Type getId();

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		BaseId<?> other = (BaseId<?>) obj;
		return Objects.equals(getId(), other.getId());
	}

}
