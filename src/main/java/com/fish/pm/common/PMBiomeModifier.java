package com.fish.pm.common;

import com.fish.pm.registry.PMBiomeModifiers;
import com.fish.pm.registry.PMEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class PMBiomeModifier implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && biome.is(Biomes.SAVANNA)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.DWARF_CROCODILE.get(), 10, 1, 1));
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 5, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.DARK_FOREST)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 7, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.PLAINS)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 5, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.JUNGLE)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 5, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.BIRCH_FOREST)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 5, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.FOREST)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 5, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.FLOWER_FOREST)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.TERROR_CHICKEN.get(), 5, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.MANGROVE_SWAMP)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.DWARF_CROCODILE.get(), 8, 1, 1));
        }
        if (phase == Phase.ADD && biome.is(Biomes.LUSH_CAVES)) {
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(PMEntities.DWARF_CROCODILE.get(), 11, 1, 1));
        }
    }



    @Override
    public Codec<? extends BiomeModifier> codec() {
        return PMBiomeModifiers.PM_BIOME_MODIFIER.get();
    }
}
