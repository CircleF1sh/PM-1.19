package com.fish.pm.client.screen;

import com.fish.pm.PM;
import com.fish.pm.common.screen.EvolutionistsTableMenu;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EvolutionistsTableScreen extends AbstractContainerScreen<EvolutionistsTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PM.MOD_ID, "textures/gui/evolutionists_table_gui.png");

    private static final int SCREEN_WIDTH = 176;
    private static final int SCREEN_HEIGHT = 166;

    private static final int LEFT_PROGRESS_X = 71;
    private static final int LEFT_PROGRESS_Y = 45;
    private static final int RIGHT_PROGRESS_X = 90;
    private static final int RIGHT_PROGRESS_Y = 26;

    private static final int PROGRESS_WIDTH = 11;
    private static final int PROGRESS_HEIGHT = 44;

    public EvolutionistsTableScreen(EvolutionistsTableMenu pMenu, Inventory inv, Component component) {
        super(pMenu, inv, component);
        imageWidth = SCREEN_WIDTH;
        imageHeight = SCREEN_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        Lighting.setupForFlatItems();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(new PoseStack(), x, y, 0, 0, imageWidth, imageHeight);
        renderDNAArrow(poseStack, x, y);
        renderFinishArrow(poseStack, x, y);
    }

    private void renderDNAArrow(PoseStack stack, int x, int y) {
        if (menu.isCrafting()) {
            blit(stack, x + 71, y + 45, 200, 0, 10, menu.getScaledProgress());
        }
    }

    private void renderFinishArrow(PoseStack stack, int x, int y) {
        if (menu.isCrafting()) {
            blit(stack, x + 90, y + 26, 176, 1, 16, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
