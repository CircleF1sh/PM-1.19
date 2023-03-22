package com.fish.pm.registry.misc;

import com.fish.pm.PM;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PMBlockTagGen extends BlockTagsProvider {
    public PMBlockTagGen(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, PM.MOD_ID, helper);
    }
}
