package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity extends BaseUuidEntity {

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "alt_text", nullable = false)
	private String altText;

	private ImageEntity(
		UUID id,
		String url,
		String altText
	) {
		super(id);
		this.url = url;
		this.altText = altText;
	}

	public static ImageEntity newInstance(
		UUID id,
		String url,
		String altText
	) {
		return new ImageEntity(
			id,
			url,
			altText
		);
	}

}
