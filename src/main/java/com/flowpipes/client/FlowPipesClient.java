package com.flowpipes.client;

import net.fabricmc.api.ClientModInitializer;
import com.flowpipes.client.render.PipeRenderer;

public class FlowPipesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		PipeRenderer.registerRenderers();
	}
}
