package com.flowpipes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import com.flowpipes.util.PipeTier;
import com.flowpipes.blockentity.SorterBlockEntity;

public class SorterBlock extends BlockWithEntity {
	public static final BooleanProperty NORTH = BooleanProperty.of("north");
	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
	public static final BooleanProperty EAST = BooleanProperty.of("east");
	public static final BooleanProperty WEST = BooleanProperty.of("west");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");

	private final PipeTier tier;

	public SorterBlock(PipeTier tier) {
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
		throw new UnsupportedOperationException("SorterBlock does not support codec");
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SorterBlockEntity(pos, state, tier);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, 
			PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;
		
		BlockEntity entity = world.getBlockEntity(pos);
		if (entity instanceof SorterBlockEntity && entity instanceof NamedScreenHandlerFactory factory) {
			player.openHandledScreen(factory);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient ? null : (world2, pos, state2, blockEntity) -> {
			if (blockEntity instanceof SorterBlockEntity sorter) {
				SorterBlockEntity.tick(world2, pos, state2, sorter);
			}
		};
	}
}
