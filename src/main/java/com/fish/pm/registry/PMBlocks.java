package com.fish.pm.registry;

import com.fish.pm.PM;
import com.fish.pm.common.block.EvolutionistsTableBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class PMBlocks {
    public static final DeferredRegister<Block> REGISTER_BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, PM.MOD_ID);

    public static final RegistryObject<Block> ANCIENT_CASING = REGISTER_BLOCK.register("ancient_casing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4.0F).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> ANCIENT_CASING_VENT = REGISTER_BLOCK.register("ancient_casing_vent", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> ANCIENT_CORE = REGISTER_BLOCK.register("ancient_core", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)));

    public static final RegistryObject<Block> EVOLUTIONISTS_TABLE = REGISTER_BLOCK.register("evolutionists_table", () -> new EvolutionistsTableBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6.0F).requiresCorrectToolForDrops().noOcclusion()));
}
