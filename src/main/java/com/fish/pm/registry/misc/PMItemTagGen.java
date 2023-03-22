package com.fish.pm.registry.misc;

import com.fish.pm.PM;
import com.fish.pm.registry.PMItems;
import com.fish.pm.registry.PMTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class PMItemTagGen extends ItemTagsProvider {

    public PMItemTagGen(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, PM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        super.addTags();

        tag(PMTags.DNA_ITEMS)
                .add(PMItems.DWARF_CROCODILE_TOOTH.get());

        tag(PMTags.EGGS)
                .add(PMItems.TERROR_CHICKEN_EGG.get())
                .add(Items.EGG);
   }

    @Override
    public String getName() {
        return PM.MOD_ID + " Item Tags";
    }
}
