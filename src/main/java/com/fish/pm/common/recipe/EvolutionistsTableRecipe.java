package com.fish.pm.common.recipe;

import com.fish.pm.PM;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;

public class EvolutionistsTableRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ResourceLocation output;
    private final NonNullList<Ingredient> recipeItems;

    public EvolutionistsTableRecipe(ResourceLocation id, ResourceLocation output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !recipeItems.isEmpty() && recipeItems.get(0).test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        // ensure level exists
        if(null == level) {
            return ItemStack.EMPTY;
        }
        // locate loot table
        LootTable loottable = ServerLifecycleHooks.getCurrentServer().getLootTables().get(getResultLootTable());
        List<ItemStack> randomItems = loottable.getRandomItems(new LootContext.Builder(level)
                .withRandom(level.getRandom())
                .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
                .create(LootContextParamSets.CHEST));
        // ensure loot table resolved
        if(randomItems.isEmpty()) {
            return ItemStack.EMPTY;
        }
        // return first element
        return randomItems.get(0);
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public ResourceLocation getResultLootTable() {
        return output;
    }

    public static class Type implements RecipeType<EvolutionistsTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<EvolutionistsTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(PM.MOD_ID, "evolutionizing");

        @Override
        public EvolutionistsTableRecipe fromJson(ResourceLocation id, JsonObject json) {
            ResourceLocation output = ResourceLocation.tryParse(GsonHelper.getAsString(json, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new EvolutionistsTableRecipe(id, output, inputs);
        }

        @Override
        public EvolutionistsTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ResourceLocation output = buf.readResourceLocation();
            return new EvolutionistsTableRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, EvolutionistsTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeResourceLocation(recipe.output);
        }


        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }


        public ResourceLocation getRegistryName() {
            return ID;
        }


        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
