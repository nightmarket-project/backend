package store.nightmarket.domain.item.fixture;

import store.nightmarket.domain.item.model.ItemGroup;
import store.nightmarket.domain.item.model.UserBuyItemGroup;
import store.nightmarket.itemcore.valueobject.ItemGroupId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.UserId;

import java.util.List;
import java.util.UUID;

public class TestItemFactory {

    private final ItemGroupId itemGroupId;
    private final UUID basicOption;
    private final UUID additionalOption;
    private final UUID colorOption;
    private final UUID cpuOption;
    private final UUID blackId;
    private final UUID whiteId;
    private final UUID cpu4Id;
    private final UUID cpu8Id;
    private final UUID monitor60hzId;
    private final UUID monitor144hzId;
    private final UserId userId;

    public TestItemFactory() {
        itemGroupId = new ItemGroupId(UUID.randomUUID());
        basicOption = UUID.randomUUID();
        additionalOption = UUID.randomUUID();
        colorOption = UUID.randomUUID();
        cpuOption = UUID.randomUUID();
        blackId = UUID.randomUUID();
        whiteId = UUID.randomUUID();
        cpu4Id = UUID.randomUUID();
        cpu8Id = UUID.randomUUID();
        monitor60hzId = UUID.randomUUID();
        monitor144hzId = UUID.randomUUID();
        userId = new UserId(UUID.randomUUID());
    }

    public UserBuyItemGroup createTestUserBuyProductItem(
            int blackUserQty,
            int whiteUserQty,
            int cpu4UserQty,
            int cpu8UserQty,
            int monitor60UserQty,
            int monitor144UserQty
    ) {
        return UserBuyItemGroup.newInstance(
            itemGroupId,
                TestUserOptionFactory.createUserItemOptionGroup(
                        basicOption,
                        List.of(
                                TestUserOptionFactory.createUserItemOption(
                                        colorOption,
                                        List.of(
                                                TestUserOptionFactory.createUserItemDetailOption(
                                                        blackId,
                                                        blackUserQty
                                                ), TestUserOptionFactory.createUserItemDetailOption(
                                                        whiteId,
                                                        whiteUserQty
                                                )
                                        )
                                ), TestUserOptionFactory.createUserItemOption(
                                        cpuOption,
                                        List.of(
                                                TestUserOptionFactory.createUserItemDetailOption(
                                                        cpu4Id,
                                                        cpu4UserQty
                                                ), TestUserOptionFactory.createUserItemDetailOption(
                                                        cpu8Id,
                                                        cpu8UserQty
                                                )
                                        )
                                )
                        )
                ), TestUserOptionFactory.createUserItemOption(
                        additionalOption,
                        List.of(
                                TestUserOptionFactory.createUserItemDetailOption(
                                        monitor60hzId,
                                        monitor60UserQty
                                ), TestUserOptionFactory.createUserItemDetailOption(
                                        monitor144hzId,
                                        monitor144UserQty
                                )
                        )
                )
        );
    }

    public UserBuyItemGroup createTestUserBuyProductItem(
            UUID productId,
            int blackUserQty,
            int whiteUserQty,
            int cpu4UserQty,
            int cpu8UserQty,
            int monitor60UserQty,
            int monitor144UserQty
    ) {
        return UserBuyItemGroup.newInstance(
                new ItemGroupId(productId),
                TestUserOptionFactory.createUserItemOptionGroup(
                        basicOption,
                        List.of(
                                TestUserOptionFactory.createUserItemOption(
                                        colorOption,
                                        List.of(
                                                TestUserOptionFactory.createUserItemDetailOption(
                                                        blackId,
                                                        blackUserQty
                                                ), TestUserOptionFactory.createUserItemDetailOption(
                                                        whiteId,
                                                        whiteUserQty
                                                )
                                        )
                                ), TestUserOptionFactory.createUserItemOption(
                                        cpuOption,
                                        List.of(
                                                TestUserOptionFactory.createUserItemDetailOption(
                                                        cpu4Id,
                                                        cpu4UserQty
                                                ), TestUserOptionFactory.createUserItemDetailOption(
                                                        cpu8Id,
                                                        cpu8UserQty
                                                )
                                        )
                                )
                        )
                ), TestUserOptionFactory.createUserItemOption(
                        additionalOption,
                        List.of(
                                TestUserOptionFactory.createUserItemDetailOption(
                                        monitor60hzId,
                                        monitor60UserQty
                                ), TestUserOptionFactory.createUserItemDetailOption(
                                        monitor144hzId,
                                        monitor144UserQty
                                )
                        )
                )
        );
    }

    public ItemGroup createTestProductItem(
            int blackQty,
            int whiteQty,
            int cpu4Qty,
            int cpu8Qty,
            int monitor60Qty,
            int monitor144Qty
    ) {
        return ItemGroup.newInstance(
            itemGroupId,
                new Name("ComputerParts"),
                TestOptionFactory.createItemOptionGroup(
                        basicOption,
                        List.of(
                                TestOptionFactory.createItemOption(
                                        colorOption,
                                        "색깔",
                                        List.of(
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
                                        )
                                ), TestOptionFactory.createItemOption(
                                        cpuOption,
                                        "cpu core",
                                        List.of(
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
                                )
                        )
                ),
                TestOptionFactory.createItemOption(
                        additionalOption,
                        "모니터",
                        List.of(
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
                        )
                ),
                userId
        );
    }

    public ItemGroup createTestProductItem(
            UUID productId,
            int blackQty,
            int whiteQty,
            int cpu4Qty,
            int cpu8Qty,
            int monitor60Qty,
            int monitor144Qty
    ) {
        return ItemGroup.newInstance(
                new ItemGroupId(productId),
                new Name("ComputerParts"),
                TestOptionFactory.createItemOptionGroup(
                        basicOption,
                        List.of(
                                TestOptionFactory.createItemOption(
                                        colorOption,
                                        "색깔",
                                        List.of(
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
                                        )
                                ), TestOptionFactory.createItemOption(
                                        cpuOption,
                                        "cpu core",
                                        List.of(
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
                                )
                        )
                ),
                TestOptionFactory.createItemOption(
                        additionalOption,
                        "모니터",
                        List.of(
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
                        )
                ),
                userId
        );
    }

}
