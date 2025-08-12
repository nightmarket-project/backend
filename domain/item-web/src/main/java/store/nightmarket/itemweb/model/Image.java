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
    private final static int PostMaxFileSize = 20 * 1024 * 1024;
    private final static int ReviewMaxFileSize = 5 * 1024 * 1024;

    private final String imageUrl;
    private final String altText;
    private final Long fileSize;
    private int displayOrder;
    private final ImageType imageType;


    private Image(
        ImageId id,
        String imageUrl,
        String altText,
        Long fileSize,
        int displayOrder,
        ImageType imageType
    ) {
        super(id);
        if (ImageType.REVIEW == imageType) {
            validateReviewImage(
                imageUrl,
                fileSize
            );
        }
        if (ImageType.MAIN == imageType
            || ImageType.DETAIL == imageType
            || ImageType.THUMBNAIL == imageType
        ) {
            validatePostImage(
                imageUrl,
                altText,
                fileSize
            );
        }

        this.imageUrl = imageUrl;
        this.altText = altText;
        this.fileSize = fileSize;

        this.displayOrder = displayOrder;
        this.imageType = imageType;
    }

    public static Image newInstance(
        ImageId id,
        String imageUrl,
        String altText,
        Long fileSize,
        int displayOrder,
        ImageType imageType
    ) {
        return new Image(
            id,
            imageUrl,
            altText,
            fileSize,
            displayOrder,
            imageType
        );
    }

    private void validatePostImage(
        String imageUrl,
        String altText,
        Long fileSize
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
        if (fileSize > PostMaxFileSize) {
            throw new ItemWebException("FileSize exceeds MaxFileSize");
        }
    }

    private void validateReviewImage(
        String imageUrl,
        Long fileSize
    ) {
        if (imageUrl.isBlank()) {
            throw new ItemWebException("ImageUrl is Blank");
        }
        if (imageUrl.length() > MaxUrlLength) {
            throw new ItemWebException("ImageUrl exceeds MaxUrlLength");
        }
        if (fileSize > ReviewMaxFileSize) {
            throw new ItemWebException("FileSize exceeds ReviewMaxFileSize");
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
            && Objects.equals(fileSize, image.fileSize)
            && imageType == image.imageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            imageUrl,
            altText,
            fileSize,
            displayOrder,
            imageType
        );
    }
    
}
