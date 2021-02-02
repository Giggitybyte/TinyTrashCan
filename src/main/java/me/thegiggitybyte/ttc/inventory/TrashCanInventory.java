package me.thegiggitybyte.ttc.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface TrashCanInventory extends Inventory {
    
    default DefaultedList<ItemStack> getItems() {
        return DefaultedList.ofSize(1, ItemStack.EMPTY);
    }
    
    @Override
    default int size() {
        return getItems().size();
    }
    
    @Override
    default boolean isEmpty() {
        ItemStack stack = getStack(0);
        return stack.isEmpty();
    }
    
    @Override
    default ItemStack getStack(int slotIndex) {
        if (slotIndex != 0) throw new IllegalArgumentException("Trash can only contain one slot.");
        return getItems().get(slotIndex);
    }
    
    @Override
    default void setStack(int slotIndex, ItemStack stack) {
        if (slotIndex != 0) throw new IllegalArgumentException("Trash can only contain one slot.");
        
        getItems().set(slotIndex, stack);
        if (stack.getCount() > getMaxCountPerStack()) stack.setCount(getMaxCountPerStack());
    }
    
    @Override
    default ItemStack removeStack(int slotIndex, int count) {
        if (slotIndex != 0) throw new IllegalArgumentException("Trash can only contain one slot.");
        
        ItemStack result = Inventories.splitStack(getItems(), slotIndex, count);
        if (!result.isEmpty()) markDirty();
        
        return result;
    }
    
    @Override
    default ItemStack removeStack(int slotIndex) {
        if (slotIndex != 0) throw new IllegalArgumentException("Trash can only contain one slot.");
        return Inventories.removeStack(getItems(), slotIndex);
    }
    
    @Override
    default void clear() {
        getItems().clear();
    }
    
    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
