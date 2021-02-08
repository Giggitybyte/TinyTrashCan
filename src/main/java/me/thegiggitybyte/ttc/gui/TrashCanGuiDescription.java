package me.thegiggitybyte.ttc.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import me.thegiggitybyte.ttc.TinyTrashCan;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;

public class TrashCanGuiDescription extends SyncedGuiDescription {
    
    public TrashCanGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(TinyTrashCan.TRASH_CAN_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, 1), getBlockPropertyDelegate(context));
        
        WLabel title = new WLabel(new TranslatableText("block.ttc.trash_can"));
        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        WGridPanel rootPanel = new WGridPanel();
        
        this.setRootPanel(rootPanel);
        rootPanel.setSize(130, 130);
        
        rootPanel.add(title, 3, 0);
        rootPanel.add(itemSlot, 4, 1);
        rootPanel.add(this.createPlayerInventoryPanel(), 0, 3);
        
        rootPanel.validate(this);
    }
    
    @Override
    public ItemStack onSlotClick(int slotIndex, int button, SlotActionType action, PlayerEntity player) {
        Slot trashSlot = this.slots.get(0);
        boolean itemWasDeleted = false;
        
        if ((slotIndex == 0) && ((action == SlotActionType.PICKUP) & (button == 0))) {
            itemWasDeleted = tryCursorDelete(trashSlot, player);
        } else if ((action == SlotActionType.QUICK_MOVE) && (slotIndex != 0)) { // Shift-click.
            itemWasDeleted = tryQuickDelete(trashSlot, this.slots.get(slotIndex));
        }
        
        if (itemWasDeleted) {
            trashSlot.markDirty();
            player.inventory.markDirty();
            
            world.playSound(null, player.getBlockPos(),
                    SoundEvents.BLOCK_COMPOSTER_READY,
                    SoundCategory.PLAYERS, 0.5f, 0.8f);
            
            return ItemStack.EMPTY;
        } else {
            return super.onSlotClick(slotIndex, button, action, player);
        }
    }
    
    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        world.playSound(null, player.getBlockPos(),
                SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE,
                SoundCategory.BLOCKS, 0.9f, 1.3f);
    }
    
    private boolean tryCursorDelete(Slot trashSlot, PlayerEntity player) {
        ItemStack cursorStack = player.inventory.getCursorStack();
        ItemStack trashStack = trashSlot.getStack();
        
        if (cursorStack.isEmpty() | trashStack.isEmpty() |
                trashStack.getItem() == Items.AIR) {
            return false;
        }
        
        if (canStacksCombine(cursorStack, trashStack)) {
            if (trashStack.getCount() >= trashStack.getMaxCount()) {
                trashSlot.setStack(cursorStack);
                player.inventory.setCursorStack(ItemStack.EMPTY);
                
                return true;
            }
            
            Item stackItem = trashStack.getItem();
            int maxStackCount = stackItem.getMaxCount();
            
            int combinedStackCount = cursorStack.getCount() + trashStack.getCount();
            if (combinedStackCount > maxStackCount) {
                int remainder = combinedStackCount - maxStackCount;
                
                trashSlot.setStack(new ItemStack(stackItem, maxStackCount));
                player.inventory.setCursorStack(new ItemStack(stackItem, remainder));
            }
            
            return false;
            
        } else {
            trashSlot.setStack(cursorStack);
            player.inventory.setCursorStack(ItemStack.EMPTY);
            
            return true;
        }
    }
    
    private boolean tryQuickDelete(Slot trashSlot, Slot originSlot) {
        ItemStack originStack = originSlot.getStack();
        ItemStack trashStack = trashSlot.getStack();
        
        if (originStack.isEmpty() | trashStack.isEmpty() |
                trashStack.getItem() == Items.AIR) {
            return false;
        }
        
        if (canStacksCombine(originStack, trashStack)) {
            if (trashStack.getCount() >= trashStack.getMaxCount()) {
                trashSlot.setStack(originStack);
                originSlot.setStack(ItemStack.EMPTY);
                originSlot.markDirty();
                
                return true;
            }
            
            Item stackItem = trashStack.getItem();
            int maxStackCount = stackItem.getMaxCount();
            
            int combinedStackCount = originStack.getCount() + trashStack.getCount();
            if (combinedStackCount > maxStackCount) {
                int remainder = combinedStackCount - maxStackCount;
                
                trashSlot.setStack(new ItemStack(stackItem, maxStackCount));
                originSlot.setStack(new ItemStack(stackItem, remainder));
                originSlot.markDirty();
            }
            
            return false;
            
        } else {
            trashSlot.setStack(originStack);
            originSlot.setStack(ItemStack.EMPTY);
            originSlot.markDirty();
            
            return true;
        }
    }
}
