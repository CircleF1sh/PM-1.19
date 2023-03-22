package com.fish.pm.common.entities.block;

import com.fish.pm.common.recipe.EvolutionistsTableRecipe;
import com.fish.pm.common.screen.EvolutionistsTableMenu;
import com.fish.pm.registry.PMItems;
import com.fish.pm.registry.PMTags;
import com.google.common.collect.Maps;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

public class EvolutionistsTableBlockEntity extends BlockEntity implements MenuProvider {
    private BlockState state;
    protected final ContainerData data;
    public int ticksAlive;
    private int progress = 0;
    private int maxProgress = 78;
    private int fuel = 0;
    private int maxFuel = 790;
    public int tickCount = 0;

    public EvolutionistsTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(PMBlockEntities.EVOLUTIONISTS_TABLE.get(), pos, blockState);
        this.state = blockState;
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EvolutionistsTableBlockEntity.this.progress;
                    case 1 -> EvolutionistsTableBlockEntity.this.maxProgress;
                    case 2 -> EvolutionistsTableBlockEntity.this.fuel;
                    case 3 -> EvolutionistsTableBlockEntity.this.maxFuel;
                    case 4 -> EvolutionistsTableBlockEntity.this.tickCount;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> EvolutionistsTableBlockEntity.this.progress = value;
                    case 1 -> EvolutionistsTableBlockEntity.this.maxProgress = value;
                    case 2 -> EvolutionistsTableBlockEntity.this.fuel = value;
                    case 3 -> EvolutionistsTableBlockEntity.this.maxFuel = value;
                    case 4 -> EvolutionistsTableBlockEntity.this.tickCount = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
    };

        private IItemHandler pItemHandler = new IItemHandler() {
            @Override
            public int getSlots() {
                return itemHandler.getSlots();
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                return itemHandler.getStackInSlot(slot);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) {
                    return stack;
                }

                if (slot == 0 && stack.is(PMTags.DNA_ITEMS)) {
                    return itemHandler.insertItem(slot, stack, simulate);
                }
                if (slot == 1 && stack.is(PMTags.EGGS)) {
                    return itemHandler.insertItem(slot, stack, simulate);
                }
                if (slot == 2 && stack.is(PMItems.POLONIUM_NUGGET.get())) {
                    return itemHandler.insertItem(slot, stack, simulate);
                }
                return stack;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return null;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 0;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return false;
            }
        };

    @Override
    public Component getDisplayName() {
        return Component.literal("Evolutionist's Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new EvolutionistsTableMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("evolutionists_table.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("evolutionists_table.progress");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EvolutionistsTableBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        if(hasRecipe(pEntity)) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean hasRecipe(EvolutionistsTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 1; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EvolutionistsTableRecipe> match = level.getRecipeManager().getRecipeFor(EvolutionistsTableRecipe.Type.INSTANCE, inventory, level);

        return canInsertAmountIntoOutputSlot(inventory);
    }
    private static void craftItem(EvolutionistsTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EvolutionistsTableRecipe> match = level.getRecipeManager().getRecipeFor(EvolutionistsTableRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            // determine result item
            ItemStack result = match.get().assemble(inventory);
            // remove first input
            entity.itemHandler.extractItem(1, 1, false);
            // attempt to remove flask
            if (isDNAItem(result)) {
                entity.itemHandler.extractItem(0, 2, false);
            }
            // attempt to insert result item
            boolean success = false;
            for (int i = 2, n = entity.itemHandler.getSlots(); i < n; i++) {
                if (entity.itemHandler.insertItem(i, result, false).isEmpty()) {
                    success = true;
                    break;
                }
            }
            // reset progress
            entity.resetProgress();
        }
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        for(int slot = 2, n = inventory.getContainerSize(); slot < n; slot++) {
            if(inventory.getItem(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDNAItem(ItemStack itemStack) {
        return itemStack.is(PMTags.DNA_ITEMS);
    }
}