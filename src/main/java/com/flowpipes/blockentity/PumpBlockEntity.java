package com.flowpipes.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import com.flowpipes.util.PipeTier;

public class PumpBlockEntity extends BlockEntity {
	private PipeTier tier;
	private boolean redstoneActive = false;
	private boolean waterWheelActive = false;

	public PumpBlockEntity(BlockPos pos, BlockState state, PipeTier tier) {
		super(null, pos, state);
		this.tier = tier;
	}

	public PipeTier getTier() {
		return tier;
	}

	public boolean isActive() {
		return redstoneActive || waterWheelActive;
	}

	public void setRedstoneActive(boolean active) {
		this.redstoneActive = active;
	}

	public void setWaterWheelActive(boolean active) {
		this.waterWheelActive = active;
	}

	public boolean hasWaterWheel() {
		return waterWheelActive;
	}
}
