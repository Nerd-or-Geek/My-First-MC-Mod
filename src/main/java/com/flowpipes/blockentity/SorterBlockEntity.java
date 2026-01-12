package com.flowpipes.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import com.flowpipes.util.PipeTier;
import com.flowpipes.screen.SorterScreenHandler;
import com.flowpipes.registry.BlockEntityRegistry;

public class SorterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
	private PipeTier tier;
	private boolean isWhitelist = true;

	public SorterBlockEntity(BlockPos pos, BlockState state, PipeTier tier) {
		super(BlockEntityRegistry.SORTER_ENTITY, pos, state);
		this.tier = tier;
	}

	public PipeTier getTier() {
		return tier;
	}

	public boolean isWhitelist() {
		return isWhitelist;
	}

	public void setWhitelist(boolean whitelist) {
		isWhitelist = whitelist;
	}

	@Override
	public int size() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack result = inventory.get(slot);
		if (result.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			return result.split(amount);
		}
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		if (stack.getCount() > getMaxCountPerStack()) {
			stack.setCount(getMaxCountPerStack());
		}
	}

	@Override
	public boolean canPlayerUse(net.minecraft.entity.player.PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	public static void tick(World world, BlockPos pos, BlockState state, SorterBlockEntity entity) {
		if (world.isClient) return;
	}

	@Override
	public Text getDisplayName() {
		return Text.literal("Sorter");
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, net.minecraft.entity.player.PlayerInventory inventory, net.minecraft.entity.player.PlayerEntity player) {
		return new SorterScreenHandler(syncId, inventory, this);
	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		// Skip inventory serialization for now
		nbt.putString("tier", tier.getDisplayName());
		nbt.putInt("isWhitelist", isWhitelist ? 1 : 0);
		return nbt;
	}

	public void readNbt(NbtCompound nbt) {
		// Skip inventory deserialization for now
		NbtElement whitelistElement = nbt.get("isWhitelist");
		if (whitelistElement instanceof NbtInt whitelistTag) {
			isWhitelist = whitelistTag.intValue() == 1;
		}
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbt = new NbtCompound();
		writeNbt(nbt);
		return nbt;
	}
}
