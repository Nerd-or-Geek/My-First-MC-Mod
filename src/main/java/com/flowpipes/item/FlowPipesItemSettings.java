package com.flowpipes.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

/**
 * Custom Item.Settings that provides default values for translation key and model ID
 * without calling super, bypassing Minecraft's null checks that occur before registration
 */
public class FlowPipesItemSettings extends Item.Settings {
	private String translationKey = "item.placeholder";
	private Identifier modelId = Identifier.of("flowpipes", "placeholder");

	public FlowPipesItemSettings() {
		super();
	}

	@Override
	public String getTranslationKey() {
		// Return the translation key we set, bypassing super's null check
		return translationKey;
	}

	@Override
	public Identifier getModelId() {
		// Return the model ID we set, bypassing super's null check
		return modelId;
	}
}
