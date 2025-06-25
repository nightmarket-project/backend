package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "variant_option_value")
@NoArgsConstructor
public class VariantOptionValueEntity extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariantEntity productVariantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroupEntity optionGroupEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id")
    private OptionValueEntity optionValueEntity;

    public VariantOptionValueEntity(
        ProductVariantEntity productVariantEntity,
        OptionGroupEntity optionGroupEntity,
        OptionValueEntity optionValueEntity
    ) {
        this.productVariantEntity = productVariantEntity;
        this.optionGroupEntity = optionGroupEntity;
        this.optionValueEntity = optionValueEntity;
    }

}
