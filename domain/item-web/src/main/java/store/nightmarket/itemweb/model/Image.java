package store.nightmarket.itemweb.model;

import java.util.Objects;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.ImageId;

@Getter
public class Image extends BaseModel<ImageId> {

	private final static int MaxUrlLength = 1000;
	private final static int MaxAltTextLength = 1000;

	private final String imageUrl;
	private final String altText;

	private Image(
		ImageId id,
		String imageUrl,
		String altText
	) {
		super(id);
		validate(imageUrl);
		this.imageUrl = imageUrl;
		this.altText = altText;
	}

	public static Image newInstance(
		ImageId id,
		String imageUrl,
		String altText
	) {
		return new Image(
			id,
			imageUrl,
			altText
		);
	}

	public ImageId getImageId() {
		return internalId();
	}

	private void validate(String imageUrl) {
		if (imageUrl.isBlank()) {
			throw new ItemWebException("ImageUrl is Blank");
		}
		if (imageUrl.length() > MaxUrlLength) {
			throw new ItemWebException("ImageUrl exceeds MaxUrlLength");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Image image = (Image)obj;
		return Objects.equals(imageUrl, image.imageUrl)
			&& Objects.equals(altText, image.altText);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			imageUrl,
			altText
		);
	}

}
