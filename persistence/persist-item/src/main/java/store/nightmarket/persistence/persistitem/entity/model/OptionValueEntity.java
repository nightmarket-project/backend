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
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

@Getter
@Entity
@Table(name = "option_value")
@NoArgsConstructor
public class OptionValueEntity extends BaseUuidEntity {

    @Column(name = "value")
    private String value;

    @Embedded
    @Column(name = "price")
    private PriceEntity price;

    @Column(name = "order")
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroupEntity optionGroupEntity;

    @OneToMany(mappedBy = "optionValueEntity", fetch = FetchType.LAZY)
    List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

    public OptionValueEntity(
        String value,
        PriceEntity price,
        int order,
        OptionGroupEntity optionGroupEntity,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        this.value = value;
        this.price = price;
        this.order = order;
        this.optionGroupEntity = optionGroupEntity;
        this.variantOptionValueEntityList = variantOptionValueEntityList;
    }

}
