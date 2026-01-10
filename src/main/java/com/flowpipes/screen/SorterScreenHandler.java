package com.flowpipes.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import com.flowpipes.blockentity.SorterBlockEntity;

public class SorterScreenHandler extends ScreenHandler {
	private final SorterBlockEntity sorterEntity;

	public SorterScreenHandler(int syncId, PlayerInventory playerInventory, SorterBlockEntity sorterEntity) {
		super(null, syncId);
		this.sorterEntity = sorterEntity;

		// Add sorter inventory slots (9 slots for filter)
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				addSlot(new Slot(sorterEntity, col + row * 3, 44 + col * 18, 17 + row * 18));
			}
		}

		// Add player inventory
		addPlayerInventory(playerInventory);
		addPlayerHotbar(playerInventory);
	}

	private void addPlayerInventory(PlayerInventory playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlot(new Slot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 140 + i * 18));
			}
		}
	}

	private void addPlayerHotbar(PlayerInventory playerInventory) {
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 198));
		}
	}

	public SorterBlockEntity getSorterEntity() {
		return sorterEntity;
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slotObject = this.slots.get(slot);

		if (slotObject != null && slotObject.hasStack()) {
			ItemStack itemStack2 = slotObject.getStack();
			itemStack = itemStack2.copy();

			if (slot < 9) {
				if (!this.insertItem(itemStack2, 9, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, 9, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slotObject.setStack(ItemStack.EMPTY);
			} else {
				slotObject.markDirty();
			}
		}

		return itemStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return sorterEntity.canPlayerUse(player);
	}
}
