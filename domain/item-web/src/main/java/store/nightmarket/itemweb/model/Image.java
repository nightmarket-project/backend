package store.nightmarket.itemweb.model;

import java.util.Objects;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.state.ImageType;
import store.nightmarket.itemweb.valueobject.ImageId;

@Getter
public class Image extends BaseModel<ImageId> {

    private final static int MaxOriginalFilenameLength = 255;
    private final static int MaxUrlLength = 1000;
    private final static int MaxAltTextLength = 1000;
    private final static int PostMaxFileSize = 20 * 1024 * 1024;
    private final static int PostMaxWidth = 200;
    private final static int PostMaxHeight = 200;

    private final static int ReviewMaxFileSize = 5 * 1024 * 1024;
    private final static int ReviewMaxWidth = 75;
    private final static int ReviewMaxHeight = 75;

    private final String originalFileName;
    private final String imageUrl;
    private final String altText;
    private final Long fileSize;
    private final Integer width;
    private final Integer height;
    private int displayOrder;
    private final ImageType imageType;


    private Image(
        ImageId id,
        String originalFileName,
        String imageUrl,
        String altText,
        Long fileSize,
        Integer width,
        Integer height,
        int displayOrder,
        ImageType imageType
    ) {
        super(id);
        if (ImageType.REVIEW == imageType) {
            validateReviewImage(
                imageUrl,
                fileSize,
                width,
                height
            );
        }
        if (ImageType.MAIN == imageType
            || ImageType.DETAIL == imageType
            || ImageType.THUMBNAIL == imageType
        ) {
            validatePostImage(
                originalFileName,
                imageUrl,
                altText,
                fileSize,
                width,
                height
            );
        }

        this.originalFileName = originalFileName;
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.fileSize = fileSize;
        this.width = width;
        this.height = height;
        this.displayOrder = displayOrder;
        this.imageType = imageType;
    }

    public static Image newInstance(
        ImageId id,
        String originalFileName,
        String imageUrl,
        String altText,
        Long fileSize,
        Integer width,
        Integer height,
        int displayOrder,
        ImageType imageType
    ) {
        return new Image(
            id,
            originalFileName,
            imageUrl,
            altText,
            fileSize,
            width,
            height,
            displayOrder,
            imageType
        );
    }

    private void validatePostImage(
        String originalFilename,
        String imageUrl,
        String altText,
        Long fileSize,
        Integer width,
        Integer height
    ) {
        if (originalFilename.isBlank()) {
            throw new ItemWebException("Original filename is blank");
        }
        if (originalFilename.length() > MaxOriginalFilenameLength) {
            throw new ItemWebException("OriginalFilename exceeds MaxOriginalFilenameLength");
        }
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
        if (width > PostMaxWidth) {
            throw new ItemWebException("Width exceeds MaxWidth");
        }
        if (height > PostMaxHeight) {
            throw new ItemWebException("Height exceeds MaxHeight");
        }
    }

    private void validateReviewImage(
        String imageUrl,
        Long fileSize,
        Integer width,
        Integer height
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
        if (width > ReviewMaxWidth) {
            throw new ItemWebException("Width exceeds ReviewMaxWidth");
        }
        if (height > ReviewMaxHeight) {
            throw new ItemWebException("Height exceeds ReviewMaxHeight");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return displayOrder == image.displayOrder
            && Objects.equals(originalFileName, image.originalFileName)
            && Objects.equals(imageUrl, image.imageUrl)
            && Objects.equals(altText, image.altText)
            && Objects.equals(fileSize, image.fileSize)
            && Objects.equals(width, image.width)
            && Objects.equals(height, image.height)
            && imageType == image.imageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalFileName, imageUrl, altText, fileSize,
            width, height, displayOrder, imageType);
    }
}
