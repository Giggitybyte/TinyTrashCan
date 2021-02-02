package me.thegiggitybyte.ttc.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import me.thegiggitybyte.ttc.TinyTrashCan;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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
        ItemStack trashSlotStack = trashSlot.getStack();
        boolean wasItemDeleted = false;
        
        if ((slotIndex == 0) && ((action == SlotActionType.PICKUP) & (button == 0))) {
            ItemStack cursorStack = player.inventory.getCursorStack();
            
            if (!cursorStack.isEmpty() && !trashSlotStack.isEmpty()) {
                trashSlot.setStack(cursorStack);
                player.inventory.setCursorStack(ItemStack.EMPTY);
                
                wasItemDeleted = true;
            }
            
        } else if (action == SlotActionType.QUICK_MOVE) { // Shift-click.
            Slot selectedSlot = this.slots.get(slotIndex);
            ItemStack selectedStack = selectedSlot.getStack();
            
            if (!trashSlotStack.isEmpty() && !selectedStack.isEmpty()) {
                trashSlot.setStack(selectedStack);
                selectedSlot.setStack(ItemStack.EMPTY);
                
                selectedSlot.markDirty();
                wasItemDeleted = true;
            }
        }
        
        if (wasItemDeleted) {
            trashSlot.markDirty();
            player.inventory.markDirty();
            
            world.playSound(null, player.getBlockPos(),
                    SoundEvents.BLOCK_COMPOSTER_READY,
                    SoundCategory.PLAYERS, 0.4f, 0.85f);
            
            return ItemStack.EMPTY;
        } else {
            return super.onSlotClick(slotIndex, button, action, player);
        }
    }
}
