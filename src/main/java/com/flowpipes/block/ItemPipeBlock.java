package com.flowpipes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.flowpipes.util.PipeTier;
import com.flowpipes.util.PipeSize;

public class ItemPipeBlock extends Block {
	public static final BooleanProperty NORTH = BooleanProperty.of("north");
	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
	public static final BooleanProperty EAST = BooleanProperty.of("east");
	public static final BooleanProperty WEST = BooleanProperty.of("west");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");

	private final PipeTier tier;
	private PipeSize size = PipeSize.SIZE_1X1;

	public ItemPipeBlock(PipeTier tier) {
		super(Settings.create()
			.strength(1.5f, 6.0f)
			.nonOpaque());
		this.tier = tier;
	}

	public PipeTier getTier() {
		return tier;
	}

	public PipeSize getSize() {
		return size;
	}

	public void setSize(PipeSize size) {
		this.size = size;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, 
			PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient && player.isSneaking()) {
			// Cycle pipe size
			PipeSize[] sizes = PipeSize.values();
			int nextIndex = (size.ordinal() + 1) % sizes.length;
			setSize(sizes[nextIndex]);
			player.sendMessage(net.minecraft.text.Text.literal("Pipe size: " + size.getDisplayName()), false);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}
