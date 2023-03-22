package com.fish.pm.registry;

import com.fish.pm.PM;
import com.fish.pm.common.PMBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PMBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> REGISTER_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, PM.MOD_ID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> PM_BIOME_MODIFIER = REGISTER_MODIFIERS.register("pm_biome_modifier", () -> Codec.unit(PMBiomeModifier::new));



}
