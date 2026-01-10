package com.flowpipes.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import com.flowpipes.screen.SorterScreenHandler;
import com.flowpipes.blockentity.SorterBlockEntity;

public class SorterScreen extends HandledScreen<SorterScreenHandler> {
	private ButtonWidget modeButton;

	public SorterScreen(SorterScreenHandler handler, PlayerInventory inventory, Text component) {
		super(handler, inventory, component);
		this.backgroundWidth = 176;
		this.backgroundHeight = 222;
	}

	@Override
	protected void init() {
		super.init();
		
		int centerX = (this.width - this.backgroundWidth) / 2;
		int centerY = (this.height - this.backgroundHeight) / 2;

		// Mode toggle button
		this.modeButton = ButtonWidget.builder(Text.literal(getModeText()), button -> {
			SorterBlockEntity sorter = handler.getSorterEntity();
			sorter.setWhitelist(!sorter.isWhitelist());
		})
		.dimensions(centerX + 130, centerY + 17, 40, 20)
		.build();

		this.addDrawableChild(modeButton);
	}

	private String getModeText() {
		return handler.getSorterEntity().isWhitelist() ? "White" : "Black";
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;
		context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, 0xFF202020);
	}
}
