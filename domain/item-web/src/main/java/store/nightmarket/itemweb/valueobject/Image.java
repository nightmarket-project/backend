package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

import store.nightmarket.domain.item.valueobject.Name;

public record Image(String imageUrl, Name name) {

	private final static int MaxUrlLength = 1000;

	public Image {
		validate(imageUrl);
	}

	private void validate(String imageUrl) {
		if (imageUrl.isBlank()) {
			throw new IllegalArgumentException("Image url is blank");
		}
		if (imageUrl.length() > MaxUrlLength) {
			throw new IllegalArgumentException("Image url exceeds maximum length of " + MaxUrlLength);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Image image = (Image)obj;
		return Objects.equals(name, image.name)
			&& Objects.equals(imageUrl, image.imageUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(imageUrl, name);
	}

}
