package store.nightmarket.application.appitem.mapper;

import store.nightmarket.itemweb.model.Image;
import store.nightmarket.itemweb.valueobject.ImageId;
import store.nightmarket.persistence.persistitem.entity.model.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

public class ImageMapper {

    public static Image toDomain(ImageEntity entity) {
        return Image.newInstance(
            new ImageId(entity.getId()),
            entity.getUrl(),
            entity.getAltText(),
            entity.getDisplayOrder(),
            entity.getType()
        );
    }

    public static ImageEntity toEntity(
        Image domain,
        ReviewEntity reviewEntity,
        ProductPostEntity productPostEntity
    ) {
        return ImageEntity.newInstance(
            domain.getImageId().getId(),
            domain.getImageUrl(),
            domain.getAltText(),
            domain.getDisplayOrder(),
            domain.getImageType(),
            reviewEntity,
            productPostEntity
        );
    }

}
