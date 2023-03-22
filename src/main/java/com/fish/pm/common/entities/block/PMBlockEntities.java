package com.fish.pm.common.entities.block;

import com.fish.pm.PM;
import com.fish.pm.registry.PMBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PMBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PM.MOD_ID);

    public static final RegistryObject<BlockEntityType<EvolutionistsTableBlockEntity>> EVOLUTIONISTS_TABLE = REGISTER_BLOCK_ENTITIES.register("evolutionists_table",
            () -> BlockEntityType.Builder.of(EvolutionistsTableBlockEntity::new,
                    PMBlocks.EVOLUTIONISTS_TABLE.get()).build(null));
    public static void register(IEventBus bus) {
        REGISTER_BLOCK_ENTITIES.register(bus);
    }
}
