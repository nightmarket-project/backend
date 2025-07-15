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
import store.nightmarket.persistence.persistitem.entity.valueobject.Price;

@Getter
@Entity
@Table(name = "option_value")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionValueEntity extends BaseUuidEntity {

    @Column(name = "value")
    private String value;

    @Embedded
    @Column(name = "price")
    private Price price;

    @Column(name = "order")
    private int order;

    @OneToMany(mappedBy = "optionValueEntity", fetch = FetchType.LAZY)
    private List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroupEntity optionGroupEntity;

    private OptionValueEntity(
        String value,
        Price price,
        int order,
        OptionGroupEntity optionGroupEntity
    ) {
        this.value = value;
        this.price = price;
        this.order = order;
        this.optionGroupEntity = optionGroupEntity;
    }

    public static OptionValueEntity newInstance(
        String value,
        Price price,
        int order,
        List<VariantOptionValueEntity> variantOptionValueEntityList,
        OptionGroupEntity optionGroupEntity
    ) {
        return new OptionValueEntity(
            value,
            price,
            order,
            optionGroupEntity
        );
    }

}
