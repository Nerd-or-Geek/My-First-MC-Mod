package com.flowpipes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import com.flowpipes.util.PipeTier;
import com.flowpipes.blockentity.PumpBlockEntity;

public class PumpBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED = BooleanProperty.of("powered");
	public static final BooleanProperty NORTH = BooleanProperty.of("north");
	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
	public static final BooleanProperty EAST = BooleanProperty.of("east");
	public static final BooleanProperty WEST = BooleanProperty.of("west");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");

	private final PipeTier tier;

	public PumpBlock(PipeTier tier) {
		super(Settings.create()
			.strength(2.0f, 8.0f)
			.nonOpaque());
		this.tier = tier;
	}

	public PipeTier getTier() {
		return tier;
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		throw new UnsupportedOperationException("PumpBlock does not support codec");
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PumpBlockEntity(pos, state, tier);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWERED, NORTH, SOUTH, EAST, WEST, UP, DOWN);
	}
}
