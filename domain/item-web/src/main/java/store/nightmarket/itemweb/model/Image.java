package store.nightmarket.itemweb.model;

import java.util.Objects;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.ImageId;

@Getter
public class Image extends BaseModel<ImageId> {

    private final static int MaxUrlLength = 1000;
    private final static int MaxAltTextLength = 1000;

    private final String imageUrl;
    private final String altText;
    private int displayOrder;
    private final ImageType imageType;


    private Image(
        ImageId id,
        String imageUrl,
        String altText,
        int displayOrder,
        ImageType imageType
    ) {
        super(id);
        if (ImageType.REVIEW == imageType) {
            validateReviewImage(
                imageUrl
            );
        }
        if (ImageType.MAIN == imageType
            || ImageType.DETAIL == imageType
            || ImageType.THUMBNAIL == imageType
        ) {
            validatePostImage(
                imageUrl,
                altText
            );
        }

        this.imageUrl = imageUrl;
        this.altText = altText;
        this.displayOrder = displayOrder;
        this.imageType = imageType;
    }

    public static Image newInstance(
        ImageId id,
        String imageUrl,
        String altText,
        int displayOrder,
        ImageType imageType
    ) {
        return new Image(
            id,
            imageUrl,
            altText,
            displayOrder,
            imageType
        );
    }

    public ImageId getImageId() {
        return internalId();
    }

    private void validatePostImage(
        String imageUrl,
        String altText
    ) {
        if (imageUrl.isBlank()) {
            throw new ItemWebException("ImageUrl is Blank");
        }
        if (imageUrl.length() > MaxUrlLength) {
            throw new ItemWebException("ImageUrl exceeds MaxUrlLength");
        }
        if (altText.isBlank()) {
            throw new ItemWebException("AltText is Blank");
        }
        if (altText.length() > MaxAltTextLength) {
            throw new ItemWebException("AltText exceeds MaxAltTextLength");
        }
    }

    private void validateReviewImage(
        String imageUrl
    ) {
        if (imageUrl.isBlank()) {
            throw new ItemWebException("ImageUrl is Blank");
        }
        if (imageUrl.length() > MaxUrlLength) {
            throw new ItemWebException("ImageUrl exceeds MaxUrlLength");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return displayOrder == image.displayOrder
            && Objects.equals(imageUrl, image.imageUrl)
            && Objects.equals(altText, image.altText)
            && imageType == image.imageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            imageUrl,
            altText,
            displayOrder,
            imageType
        );
    }

}
