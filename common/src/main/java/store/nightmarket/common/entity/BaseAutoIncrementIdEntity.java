package store.nightmarket.common.entity;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseAutoIncrementIdEntity extends BaseEntity implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Override
	public boolean isNew() {
		return id == null;
	}

}