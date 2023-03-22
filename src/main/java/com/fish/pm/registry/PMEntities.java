package com.fish.pm.registry;

import com.fish.pm.PM;
import com.fish.pm.common.entities.DwarfCrocodile;
import com.fish.pm.common.entities.TerrorChicken;
import com.fish.pm.common.entities.item.TerrorChickenThrownEgg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PMEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER_ENTITY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PM.MOD_ID);

    public static final RegistryObject<EntityType<DwarfCrocodile>> DWARF_CROCODILE = REGISTER_ENTITY.register("dwarf_crocodile",
            () -> EntityType.Builder.of(DwarfCrocodile::new, MobCategory.AMBIENT)
                    .sized(0.4F, 0.3F)
                    .build(new ResourceLocation(PM.MOD_ID, "dwarf_crocodile").toString()));

    public static final RegistryObject<EntityType<TerrorChicken>> TERROR_CHICKEN = REGISTER_ENTITY.register("terror_chicken",
            () -> EntityType.Builder.of(TerrorChicken::new, MobCategory.AMBIENT)
                    .sized(0.55F, 0.45F)
                    .build(new ResourceLocation(PM.MOD_ID, "terror_chicken").toString()));

}
