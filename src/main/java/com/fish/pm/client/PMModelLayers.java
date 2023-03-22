package com.fish.pm.client;

import com.fish.pm.PM;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class PMModelLayers {
    public static final ModelLayerLocation DWARF_CROCODILE = register("dwarf_crocodile");
    public static final ModelLayerLocation TERROR_CHICKEN = register("terror_chicken");

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(new ResourceLocation(PM.MOD_ID, name), "main");
    }
}
