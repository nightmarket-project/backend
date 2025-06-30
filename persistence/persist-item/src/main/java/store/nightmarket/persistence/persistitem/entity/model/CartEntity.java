package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartEntity extends BaseUuidEntity {

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @OneToMany(mappedBy ="cartEntity", fetch = FetchType.LAZY)
    List<CartProductEntity> cartProductEntityList = new ArrayList<>();

    private CartEntity(
        UUID userId,
        List<CartProductEntity> cartProductEntityList
    ) {
        this.userId = userId;
        this.cartProductEntityList = cartProductEntityList;
    }

    public static CartEntity newInstance(
        UUID userId,
        List<CartProductEntity> cartProductEntityList
    ) {
        return new CartEntity(
            userId,
            cartProductEntityList
        );
    }
}
