package com.fish.pm.registry;

import com.fish.pm.PM;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class PMTags {
    public static final TagKey<Item> DNA_ITEMS = registerItemTag("dna_items");
    public static final TagKey<Item> EGGS = registerItemTag("eggs");

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(PM.MOD_ID, name));
    }
}
