package com.flowpipes.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.flowpipes.util.PipeTier;
import com.flowpipes.flow.FlowPayload;
import java.util.ArrayList;
import java.util.List;

public class PipeBlockEntity extends BlockEntity {
	protected PipeTier tier;
	protected List<FlowPayload> payloads = new ArrayList<>();

	public PipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, PipeTier tier) {
		super(type, pos, state);
		this.tier = tier;
	}

	public PipeTier getTier() {
		return tier;
	}

	public List<FlowPayload> getPayloads() {
		return payloads;
	}

	public void addPayload(FlowPayload payload) {
		payloads.add(payload);
	}

	public void removePayload(FlowPayload payload) {
		payloads.remove(payload);
	}

	public static void tick(World world, BlockPos pos, BlockState state, PipeBlockEntity entity) {
		if (world.isClient) return;
		
		// Tick all payloads in this pipe
		List<FlowPayload> toRemove = new ArrayList<>();
		for (FlowPayload payload : entity.payloads) {
			payload.incrementTicksInPipe();
			
			// If payload has been in pipe too long, remove it (stuck)
			if (payload.getTicksInPipe() > 400) {
				toRemove.add(payload);
			}
		}
		
		for (FlowPayload payload : toRemove) {
			entity.removePayload(payload);
		}
	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.putString("tier", tier.getDisplayName());
		nbt.putInt("payload_count", payloads.size());
		return nbt;
	}

	public void readNbt(NbtCompound nbt) {
		// Read tier and payload count if needed
	}
}
