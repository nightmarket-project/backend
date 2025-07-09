package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

@Getter
@Entity
@Table(name = "option_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private NameEntity nameEntity;

    @Column(name = "order")
    private int order;

    @OneToMany(mappedBy = "optionGroupEntity", fetch = FetchType.LAZY)
    private List<OptionValueEntity> optionValueEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "optionGroupEntity", fetch = FetchType.LAZY)
    private List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    private OptionGroupEntity(
        NameEntity nameEntity,
        int order,
        List<OptionValueEntity> optionValueEntityList,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        this.nameEntity = nameEntity;
        this.order = order;
        this.optionValueEntityList = optionValueEntityList;
        this.variantOptionValueEntityList = variantOptionValueEntityList;
    }

    public static OptionGroupEntity create(
        NameEntity nameEntity,
        int order,
        List<OptionValueEntity> optionValueEntityList,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        return new OptionGroupEntity(
            nameEntity,
            order,
            optionValueEntityList,
            variantOptionValueEntityList
        );
    }

}
