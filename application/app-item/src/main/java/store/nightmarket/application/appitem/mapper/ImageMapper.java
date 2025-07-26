package store.nightmarket.application.appitem.mapper;

import java.util.List;
import java.util.stream.Collectors;
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

    public static List<Image> toDomainList(List<ImageEntity> entityList) {
        return entityList.stream()
            .map(ImageMapper::toDomain)
            .collect(Collectors.toList());
    }

    public static List<ImageEntity> toEntityList(
        List<Image> domainList,
        ReviewEntity reviewEntity,
        ProductPostEntity productPostEntity
    ) {
        return domainList.stream()
            .map(domain -> toEntity(domain, reviewEntity, productPostEntity))
            .collect(Collectors.toList());
    }

}
