package com.fish.pm.registry;

import com.fish.pm.PM;
import com.fish.pm.common.recipe.EvolutionistsTableRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PMRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER_RECIPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PM.MOD_ID);

    public static final RegistryObject<RecipeSerializer<EvolutionistsTableRecipe>> EVOLUTIONISTS_SERIALIZER =
            REGISTER_RECIPES.register("evolutionizing", () -> EvolutionistsTableRecipe.Serializer.INSTANCE);
}
