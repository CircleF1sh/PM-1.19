package com.fish.pm.registry;

import com.fish.pm.PM;
import com.fish.pm.common.entities.item.TerrorChickenThrownEgg;
import com.fish.pm.common.items.PMBucketItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.print.DocFlavor;

public class PMItems {
    public static final DeferredRegister<Item> REGISTER_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, PM.MOD_ID);

    public static final RegistryObject<Item> DWARF_CROCODILE_EGG = REGISTER_ITEM.register("dwarf_crocodile_egg", () -> new ForgeSpawnEggItem(PMEntities.DWARF_CROCODILE, 0x4a3f2e, 0xb9891d, new Item.Properties().tab(PM.GROUP)));
    public static final RegistryObject<Item> TERROR_CHICKEN_SPAWN_EGG = REGISTER_ITEM.register("terror_chicken_spawn_egg", () -> new ForgeSpawnEggItem(PMEntities.TERROR_CHICKEN, 0xe6d5d0, 0x8d2a29, new Item.Properties().tab(PM.GROUP)));

    public static final RegistryObject<Item> POLONIUM_INGOT = REGISTER_ITEM.register("polonium_ingot", () -> new Item(new Item.Properties().tab(PM.GROUP)));
    public static final RegistryObject<Item> POLONIUM_NUGGET = REGISTER_ITEM.register("polonium_nugget", () -> new Item(new Item.Properties().tab(PM.GROUP)));
    public static final RegistryObject<Item> DWARF_CROCODILE_TOOTH = REGISTER_ITEM.register("dwarf_crocodile_tooth", () -> new Item(new Item.Properties().tab(PM.GROUP)));
    public static final RegistryObject<Item> TEST_RECIPE = REGISTER_ITEM.register("test_recipe", () -> new Item(new Item.Properties().tab(PM.GROUP)));

    public static final RegistryObject<Item> TERROR_CHICKEN_EGG = REGISTER_ITEM.register("terror_chicken_egg", () -> new TerrorChickenThrownEgg(new Item.Properties().tab(PM.GROUP).stacksTo(16)));
    public static final RegistryObject<Item> DWARF_CROCODILE_BUCKET = REGISTER_ITEM.register("dwarf_crocodile_bucket", () -> new PMBucketItem(PMEntities.DWARF_CROCODILE, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().tab(PM.GROUP).stacksTo(1)));

        //Block Items
        public static final RegistryObject<BlockItem> ANCIENT_CASING = REGISTER_ITEM.register("ancient_casing", () -> new BlockItem(PMBlocks.ANCIENT_CASING.get(), new Item.Properties().tab(PM.GROUP)));
        public static final RegistryObject<BlockItem> ANCIENT_CASING_VENT = REGISTER_ITEM.register("ancient_casing_vent", () -> new BlockItem(PMBlocks.ANCIENT_CASING_VENT.get(), new Item.Properties().tab(PM.GROUP)));
        public static final RegistryObject<BlockItem> ANCIENT_CORE = REGISTER_ITEM.register("ancient_core", () -> new BlockItem(PMBlocks.ANCIENT_CORE.get(), new Item.Properties().tab(PM.GROUP)));
        public static final RegistryObject<BlockItem> EVOLUTIONISTS_TABLE = REGISTER_ITEM.register("evolutionists_table", () -> new BlockItem(PMBlocks.EVOLUTIONISTS_TABLE.get(), new Item.Properties().tab(PM.GROUP)));
}
