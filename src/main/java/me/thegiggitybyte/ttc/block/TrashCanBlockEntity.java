package me.thegiggitybyte.ttc.block;

import me.thegiggitybyte.ttc.TinyTrashCan;
import me.thegiggitybyte.ttc.gui.TrashCanGuiDescription;
import me.thegiggitybyte.ttc.inventory.TrashCanInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class TrashCanBlockEntity extends BlockEntity implements TrashCanInventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    
    public TrashCanBlockEntity() {
        super(TinyTrashCan.TRASH_CAN_BLOCK_ENTITY);
    }
    
    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
    
    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
    
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getBlockPos(), 4.5);
    }
    
    
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new TrashCanGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
    }
    
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, items);
    }
    
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }
}
