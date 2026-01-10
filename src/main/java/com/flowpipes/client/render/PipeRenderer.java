package com.flowpipes.client.render;

import net.minecraft.client.world.ClientWorld;

public class PipeRenderer {

	private static boolean animationsEnabled = true;

	public static void registerRenderers() {
		// Register custom block renderers if needed
	}

	public static boolean isAnimationsEnabled() {
		return animationsEnabled;
	}

	public static void setAnimationsEnabled(boolean enabled) {
		animationsEnabled = enabled;
	}

	/**
	 * Render flowing items inside pipes
	 */
	public static void renderPipeItems(ClientWorld world, float tickDelta) {
		if (!animationsEnabled) {
			return;
		}

		// Render logic for animated items moving through pipes
		// This would be called during world render events
	}
}
