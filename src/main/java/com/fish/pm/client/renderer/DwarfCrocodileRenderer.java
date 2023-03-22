package com.fish.pm.client.renderer;

import com.fish.pm.PM;
import com.fish.pm.client.PMModelLayers;
import com.fish.pm.client.models.DwarfCrocodileModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.fish.pm.common.entities.DwarfCrocodile;
import net.minecraft.world.entity.animal.Fox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DwarfCrocodileRenderer extends MobRenderer<DwarfCrocodile, DwarfCrocodileModel<DwarfCrocodile>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(PM.MOD_ID, "textures/entity/dwarf_crocodile/regular.png");
    public static final ResourceLocation CAVE_TEXTURE = new ResourceLocation(PM.MOD_ID, "textures/entity/dwarf_crocodile/cave.png");

    public DwarfCrocodileRenderer(EntityRendererProvider.Context p_173921_) {
        super(p_173921_, new DwarfCrocodileModel<>(p_173921_.bakeLayer(PMModelLayers.DWARF_CROCODILE)), 0.5F);
    }

    public ResourceLocation getTextureLocation(DwarfCrocodile entity) {
        return entity.getVariant() == 1 ? CAVE_TEXTURE : TEXTURE;
    }
}
