package com.fish.pm.common.events;

import com.fish.pm.PM;
import com.fish.pm.client.PMModelLayers;
import com.fish.pm.client.models.DwarfCrocodileModel;
import com.fish.pm.client.models.TerrorChickenModel;
import com.fish.pm.client.renderer.DwarfCrocodileRenderer;
import com.fish.pm.client.renderer.TerrorChickenRenderer;
import com.fish.pm.client.screen.EvolutionistsTableScreen;
import com.fish.pm.common.entities.TerrorChicken;
import com.fish.pm.registry.PMEntities;
import com.fish.pm.registry.misc.PMBlockTagGen;
import com.fish.pm.registry.misc.PMItemTagGen;
import com.fish.pm.registry.misc.PMMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer())
            registerItemGenerator(evt.getGenerator(), evt);

    }

    @SubscribeEvent
    public static void registerEntityDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PMModelLayers.DWARF_CROCODILE, DwarfCrocodileModel::createBodyLayer);
        event.registerLayerDefinition(PMModelLayers.TERROR_CHICKEN, TerrorChickenModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PMEntities.DWARF_CROCODILE.get(), DwarfCrocodileRenderer::new);
        event.registerEntityRenderer(PMEntities.TERROR_CHICKEN.get(), TerrorChickenRenderer::new);
    }

    private static void registerItemGenerator(DataGenerator dGenerator, GatherDataEvent dataEvent) {
        ExistingFileHelper helper = dataEvent.getExistingFileHelper();
        PMBlockTagGen blockGen = new PMBlockTagGen(dGenerator, helper);
        dGenerator.addProvider(true,new PMItemTagGen(dGenerator, blockGen, helper));
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(PMMenuTypes.EVOLUTIONISTS_TABLE_MENU.get(), EvolutionistsTableScreen::new);
    }
}
