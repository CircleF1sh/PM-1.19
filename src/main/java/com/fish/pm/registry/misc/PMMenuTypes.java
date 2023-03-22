package com.fish.pm.registry.misc;

import com.fish.pm.PM;
import com.fish.pm.common.screen.EvolutionistsTableMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PMMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTER_MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PM.MOD_ID);

    public static final RegistryObject<MenuType<EvolutionistsTableMenu>> EVOLUTIONISTS_TABLE_MENU = registerMenuType(EvolutionistsTableMenu::new, "evolutionists_table_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return REGISTER_MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
