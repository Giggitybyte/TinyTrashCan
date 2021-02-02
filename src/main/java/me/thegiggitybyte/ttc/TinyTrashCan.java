package me.thegiggitybyte.ttc;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import me.thegiggitybyte.ttc.block.TrashCanBlock;
import me.thegiggitybyte.ttc.block.TrashCanBlockEntity;
import me.thegiggitybyte.ttc.gui.TrashCanGuiDescription;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TinyTrashCan implements ModInitializer, ClientModInitializer {
    public static final TrashCanBlock TRASH_CAN_BLOCK;
    public static final BlockItem TRASH_CAN_ITEM;
    
    public static BlockEntityType<TrashCanBlockEntity> TRASH_CAN_BLOCK_ENTITY;
    public static ScreenHandlerType<? extends TrashCanGuiDescription> TRASH_CAN_SCREEN_HANDLER;
    
    @Override
    public void onInitialize() {
        Identifier id = new Identifier("ttc", "trash_can");
        
        Registry.register(Registry.BLOCK, id, TRASH_CAN_BLOCK);
        Registry.register(Registry.ITEM, id, TRASH_CAN_ITEM);
        
        BlockEntityType<TrashCanBlockEntity> trashCanEntityEntry = BlockEntityType.Builder.create(TrashCanBlockEntity::new, TRASH_CAN_BLOCK).build(null);
        TRASH_CAN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id, trashCanEntityEntry);
        
        TRASH_CAN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id, (s, i) -> new TrashCanGuiDescription(s, i, ScreenHandlerContext.EMPTY));
    }
    
    @Override
    public void onInitializeClient() {
        ScreenRegistry.<TrashCanGuiDescription, CottonInventoryScreen<TrashCanGuiDescription>>register(
                TRASH_CAN_SCREEN_HANDLER, (d, i, __) -> new CottonInventoryScreen<>(d, i.player, Text.of(""))
        );
    }
    
    static {
        TRASH_CAN_BLOCK = new TrashCanBlock(FabricBlockSettings.of(Material.STONE).hardness(2.0f).nonOpaque());
        TRASH_CAN_ITEM = new BlockItem(TRASH_CAN_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));
    }
}
