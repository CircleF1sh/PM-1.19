package com.fish.pm.client.renderer;

import com.fish.pm.PM;
import com.fish.pm.client.PMModelLayers;
import com.fish.pm.client.models.TerrorChickenModel;
import com.fish.pm.common.entities.TerrorChicken;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TerrorChickenRenderer extends MobRenderer<TerrorChicken, TerrorChickenModel<TerrorChicken>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(PM.MOD_ID, "textures/entity/terror_chicken.png");

    public TerrorChickenRenderer(EntityRendererProvider.Context p_173921_) {
        super(p_173921_, new TerrorChickenModel<>(p_173921_.bakeLayer(PMModelLayers.TERROR_CHICKEN)), 0.5F);
    }

    public ResourceLocation getTextureLocation(TerrorChicken entity) {
        return TEXTURE;
    }
}
