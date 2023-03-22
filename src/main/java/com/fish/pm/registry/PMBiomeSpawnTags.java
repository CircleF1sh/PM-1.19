package com.fish.pm.registry;

import com.fish.pm.PM;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class PMBiomeSpawnTags {
    public static final TagKey<Biome> CAVE_DWARF_CROCODILE_SPAWN = registerBiomeTag("cave_dwarf_crocodile_spawn");

    private static TagKey<Biome> registerBiomeTag(String string) {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(PM.MOD_ID, string));
    }
}
