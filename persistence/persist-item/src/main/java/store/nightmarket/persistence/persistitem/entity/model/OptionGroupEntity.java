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
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

@Getter
@Entity
@Table(name = "option_group")
@NoArgsConstructor
public class OptionGroupEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private NameEntity nameEntity;

    @Column(name = "order")
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity productEntity;

    @OneToMany(mappedBy = "optionGroupEntity", fetch = FetchType.LAZY)
    List<OptionValueEntity> optionValueEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "optionGroupEntity", fetch = FetchType.LAZY)
    List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

    public OptionGroupEntity(
        NameEntity nameEntity,
        int order,
        ProductEntity productEntity,
        List<OptionValueEntity> optionValueEntityList,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        this.nameEntity = nameEntity;
        this.order = order;
        this.productEntity = productEntity;
        this.optionValueEntityList = optionValueEntityList;
        this.variantOptionValueEntityList = variantOptionValueEntityList;
    }
}
