package store.nightmarket.domain.item.fixture;

import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserProductItem;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.UserId;

import java.util.Optional;
import java.util.UUID;

public class TestItemFactory {

    public static ProductItem defaultProductItem() {
        return ProductItem.newInstance(
                new ItemId(UUID.randomUUID()),
                new Name("TestItem"),
                TestOptionFactory.createItemOptionGroup(
                        UUID.randomUUID(),
                        TestOptionFactory.createItemOption(
                                UUID.randomUUID(),
                                "색깔",
                                TestOptionFactory.createDetailOption(
                                        UUID.randomUUID(),
                                        "검은색",
                                        1000,
                                        100
                                ), TestOptionFactory.createDetailOption(
                                        UUID.randomUUID(),
                                        "하얀색",
                                        2000,
                                        200
                                )
                        ), TestOptionFactory.createItemOption(
                                UUID.randomUUID(),
                                "cpu core",
                                TestOptionFactory.createDetailOption(
                                        UUID.randomUUID(),
                                        "4코어",
                                        10000,
                                        100
                                ), TestOptionFactory.createDetailOption(
                                        UUID.randomUUID(),
                                        "8코어",
                                        20000,
                                        200
                                )
                        )
                ),
                TestOptionFactory.createItemOption(
                        UUID.randomUUID(),
                        "모니터",
                        TestOptionFactory.createDetailOption(
                                UUID.randomUUID(),
                                "모니터60hz",
                                5000,
                                50
                        ), TestOptionFactory.createDetailOption(
                                UUID.randomUUID(),
                                "모니터144hz",
                                7000,
                                70
                        )
                ),
                new UserId(UUID.randomUUID())
        );
    }

    public static UserProductItem defaultUserProductItem() {
        return UserProductItem.newInstance(
                new ItemId(UUID.randomUUID()),
                TestUserOptionFactory.createUserItemOptionGroup(
                        UUID.randomUUID(),
                        TestUserOptionFactory.createUserItemOption(
                                UUID.randomUUID(),
                                TestUserOptionFactory.createUserItemDetailOption(
                                        UUID.randomUUID(),
                                        10
                                ), TestUserOptionFactory.createUserItemDetailOption(
                                        UUID.randomUUID(),
                                        10
                                )
                        ), TestUserOptionFactory.createUserItemOption(
                                UUID.randomUUID(),
                                TestUserOptionFactory.createUserItemDetailOption(
                                        UUID.randomUUID(),
                                        10
                                ), TestUserOptionFactory.createUserItemDetailOption(
                                        UUID.randomUUID(),
                                        10
                                )
                        )
                ), TestUserOptionFactory.createUserItemOption(
                        UUID.randomUUID(),
                        TestUserOptionFactory.createUserItemDetailOption(
                                UUID.randomUUID(),
                                10
                        ), TestUserOptionFactory.createUserItemDetailOption(
                                UUID.randomUUID(),
                                10
                        )
                )
        );
    }

    public static ProductItemTestData createTestData(
            int blackQty,
            int whiteQty,
            int cpu4Qty,
            int cpu8Qty,
            int monitor60Qty,
            int monitor144Qty,
            int blackUserQty,
            int whiteUserQty,
            int cpu4UserQty,
            int cpu8UserQty,
            int monitor60UserQty,
            int monitor144UserQty
    ) {
        ItemId itemId = new ItemId(UUID.randomUUID());
        UUID basicOption = UUID.randomUUID();
        UUID additionalOption = UUID.randomUUID();
        UUID colorOption = UUID.randomUUID();
        UUID cpuOption = UUID.randomUUID();
        UUID blackId = UUID.randomUUID();
        UUID whiteId = UUID.randomUUID();
        UUID cpu4Id = UUID.randomUUID();
        UUID cpu8Id = UUID.randomUUID();
        UUID monitor60hzId = UUID.randomUUID();
        UUID monitor144hzId = UUID.randomUUID();


        ProductItem productItem = ProductItem.newInstance(
                itemId,
                new Name("ComputerParts"),
                TestOptionFactory.createItemOptionGroup(
                        basicOption,
                        TestOptionFactory.createItemOption(
                                colorOption,
                                "색깔",
                                TestOptionFactory.createDetailOption(
                                        blackId,
                                        "검은색",
                                        1000,
                                        blackQty
                                ), TestOptionFactory.createDetailOption(
                                        whiteId,
                                        "하얀색",
                                        2000,
                                        whiteQty
                                )
                        ), TestOptionFactory.createItemOption(
                                cpuOption,
                                "cpu core",
                                TestOptionFactory.createDetailOption(
                                        cpu4Id,
                                        "4코어",
                                        10000,
                                        cpu4Qty
                                ), TestOptionFactory.createDetailOption(
                                        cpu8Id,
                                        "8코어",
                                        20000,
                                        cpu8Qty
                                )
                        )
                ),
                TestOptionFactory.createItemOption(
                        additionalOption,
                        "모니터",
                        TestOptionFactory.createDetailOption(
                                monitor60hzId,
                                "모니터60hz",
                                5000,
                                monitor60Qty
                        ), TestOptionFactory.createDetailOption(
                                monitor144hzId,
                                "모니터144hz",
                                7000,
                                monitor144Qty
                        )
                ),
                new UserId(UUID.randomUUID())
        );

        UserProductItem userProductItem = UserProductItem.newInstance(
                itemId,
                TestUserOptionFactory.createUserItemOptionGroup(
                        basicOption,
                        TestUserOptionFactory.createUserItemOption(
                                colorOption,
                                TestUserOptionFactory.createUserItemDetailOption(
                                        blackId,
                                        blackUserQty
                                ), TestUserOptionFactory.createUserItemDetailOption(
                                        whiteId,
                                        whiteUserQty
                                )
                        ), TestUserOptionFactory.createUserItemOption(
                                cpuOption,
                                TestUserOptionFactory.createUserItemDetailOption(
                                        cpu4Id,
                                        cpu4UserQty
                                ), TestUserOptionFactory.createUserItemDetailOption(
                                        cpu8Id,
                                        cpu8UserQty
                                )
                        )
                ), TestUserOptionFactory.createUserItemOption(
                        additionalOption,
                        TestUserOptionFactory.createUserItemDetailOption(
                                monitor60hzId,
                                monitor60UserQty
                        ), TestUserOptionFactory.createUserItemDetailOption(
                                monitor144hzId,
                                monitor144UserQty
                        )
                )
        );

        return new ProductItemTestData(productItem, userProductItem);
    }

    public static class ProductItemTestData {
        final ProductItem productItem;
        final UserProductItem userProductItem;

        public ProductItemTestData(ProductItem productItem, UserProductItem userProductItem) {
            this.productItem = productItem;
            this.userProductItem = userProductItem;
        }

        public ProductItem getProductItem() {
            return productItem;
        }

        public UserProductItem getUserProductItem() {
            return userProductItem;
        }
    }

}
