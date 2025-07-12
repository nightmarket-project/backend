package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageType;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity extends BaseUuidEntity {

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "alt_text", nullable = false)
    private String altText;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ImageType type;

    @OneToOne(mappedBy = "imageEntity")
    private ReviewEntity reviewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post")
    private ProductPostEntity productPostEntity;

    private ImageEntity(
        UUID id,
        String originalFileName,
        String url,
        String altText,
        Long fileSize,
        Integer width,
        Integer height,
        int displayOrder,
        ImageType type,
        ReviewEntity reviewEntity,
        ProductPostEntity productPostEntity
    ) {
        super(id);
        this.originalFileName = originalFileName;
        this.url = url;
        this.altText = altText;
        this.fileSize = fileSize;
        this.width = width;
        this.height = height;
        this.displayOrder = displayOrder;
        this.type = type;
        this.reviewEntity = reviewEntity;
        this.productPostEntity = productPostEntity;
    }

    public static ImageEntity newInstance(
        UUID id,
        String originalFileName,
        String url,
        String altText,
        Long fileSize,
        Integer width,
        Integer height,
        int displayOrder,
        ImageType type,
        ReviewEntity reviewEntity,
        ProductPostEntity productPostEntity
    ) {
        return new ImageEntity(
            id,
            originalFileName,
            url,
            altText,
            fileSize,
            width,
            height,
            displayOrder,
            type,
            reviewEntity,
            productPostEntity
        );
    }

}
