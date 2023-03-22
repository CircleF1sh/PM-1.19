package com.fish.pm;

import com.fish.pm.common.entities.DwarfCrocodile;
import com.fish.pm.common.entities.TerrorChicken;
import com.fish.pm.common.entities.block.PMBlockEntities;
import com.fish.pm.registry.*;
import com.fish.pm.registry.misc.PMBlockTagGen;
import com.fish.pm.registry.misc.PMItemTagGen;
import com.fish.pm.registry.misc.PMMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PM.MOD_ID)
public class PM {
    public static final String MOD_ID = "pm";
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();


    public PM() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(this::setup);

        PMEntities.REGISTER_ENTITY.register(modEventBus);
        PMItems.REGISTER_ITEM.register(modEventBus);
        PMBlocks.REGISTER_BLOCK.register(modEventBus);
        PMBiomeModifiers.REGISTER_MODIFIERS.register(modEventBus);
        PMBlockEntities.register(modEventBus);
        PMMenuTypes.REGISTER_MENUS.register(modEventBus);
        PMRecipes.REGISTER_RECIPES.register(modEventBus);

    }
    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(PMEntities.DWARF_CROCODILE.get(), DwarfCrocodile.createAttributes().build());
        event.put(PMEntities.TERROR_CHICKEN.get(), TerrorChicken.createAttributes().build());
    }
    private void setup(final FMLCommonSetupEvent event) {
        SpawnPlacements.register(PMEntities.DWARF_CROCODILE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkMobSpawnRules);
        SpawnPlacements.register(PMEntities.TERROR_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkMobSpawnRules);

    }

    public final static CreativeModeTab GROUP = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(PMItems.DWARF_CROCODILE_BUCKET.get());
        }
    };
}
