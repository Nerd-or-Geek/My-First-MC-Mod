package com.flowpipes.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import com.flowpipes.FlowPipesMod;
import com.flowpipes.screen.SorterScreenHandler;
import com.flowpipes.blockentity.SorterBlockEntity;

public class ScreenHandlerRegistry {

	public static ExtendedScreenHandlerType<SorterScreenHandler, BlockPos> SORTER_SCREEN_HANDLER;

	public static void registerScreenHandlers() {
		SORTER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
			Identifier.of(FlowPipesMod.MOD_ID, "sorter_screen_handler"),
			new ExtendedScreenHandlerType<>((syncId, inventory, pos) -> {
				SorterBlockEntity blockEntity = (SorterBlockEntity) inventory.player.getWorld().getBlockEntity(pos);
				return new SorterScreenHandler(syncId, inventory, blockEntity);
			}, BlockPos.PACKET_CODEC));
	}
}
