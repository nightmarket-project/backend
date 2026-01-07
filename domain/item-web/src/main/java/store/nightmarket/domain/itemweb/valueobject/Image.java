package store.nightmarket.domain.itemweb.valueobject;

import java.util.Objects;

public record Image(String imageUrl) {

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
		return Objects.equals(imageUrl, image.imageUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(imageUrl);
	}

}
