package com.fish.pm.common.entities;

import com.fish.pm.client.renderer.DwarfCrocodileRenderer;
import com.fish.pm.registry.PMBiomeSpawnTags;
import com.fish.pm.registry.PMEntities;
import com.fish.pm.registry.PMItems;
import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.*;
import com.fish.pm.common.entities.DwarfCrocodile;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.jarjar.selection.util.Constants;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class DwarfCrocodile extends Animal implements Bucketable {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DwarfCrocodile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(DwarfCrocodile.class, EntityDataSerializers.BOOLEAN);

    private static final Random RANDOM = new Random();
    public static final String VARIANT_TAG = "Variant";
    private final Map<String, Vector3f> modelRotationValues = Maps.newHashMap();


    public DwarfCrocodile(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new DwarfCrocodile.CrocodileMoveControl(this);
        this.lookControl = new DwarfCrocodile.CrocodileLookControl(this, 20);
        this.maxUpStep = 1.0F;
    }

    protected PathNavigation createNavigation(Level world) {
        return new DwarfCrocodile.CrocodileGroundSwimmerNav(this, world);
    }

    public float getWalkTargetValue(BlockPos p_149140_, LevelReader p_149141_) {
        return 0.0F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 0.45F, 0.8D, 0.8D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Tadpole.class, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Frog.class, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Cod.class, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, TropicalFish.class, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Salmon.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Silverfish.class, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        ItemEntity ie = EntityType.ITEM.create(level);
        boolean success = super.doHurtTarget(p_21372_);

        int dropChance = RANDOM.nextInt(21) + 6;
        if (dropChance == 6)
            ie.setItem(new ItemStack(PMItems.DWARF_CROCODILE_TOOTH.get(), 1));
            ie.moveTo(position());
            level.addFreshEntity(ie);
        return success;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
    public boolean isPushedByFluid() {
        return false;
    }

    public void travel(Vec3 p_149181_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), p_149181_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(p_149181_);
        }

    }



    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob p_146744_) {
    return null;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag != null && dataTag.contains("Variant")) {
            this.setVariant(dataTag.getInt("Variant"));
        } else if (isLushCaveBiome(worldIn, this.blockPosition())) {
                this.setVariant(1);
            this.saveToBucketTag(this.getBucketItemStack());
            } else {
                this.setVariant(0);
            this.saveToBucketTag(this.getBucketItemStack());
            }

        return spawnDataIn;
    }
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_149196_) {
        this.entityData.set(FROM_BUCKET, p_149196_);
    }

    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        compoundnbt.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void loadFromBucketTag(CompoundTag p_148832_) {
        Bucketable.loadDefaultDataFromBucketTag(this, p_148832_);
        p_148832_.getInt("Variant");
        p_148832_.getBoolean("FromBucket");
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    public InteractionResult mobInteract(Player p_149155_, InteractionHand p_149156_) {
        return Bucketable.bucketMobPickup(p_149155_, p_149156_, this).orElse(super.mobInteract(p_149155_, p_149156_));
    }


    public ItemStack getBucketItemStack() {
        return new ItemStack(PMItems.DWARF_CROCODILE_BUCKET.get());
    }

    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }

    public boolean isFood(ItemStack p_30440_) {
        Item item = p_30440_.getItem();
        return item.isEdible() && p_30440_.getFoodProperties(this).isMeat();
    }



    public boolean canBeLeashed(Player p_30396_) {
        return true;
    }

    private static boolean isLushCaveBiome(LevelAccessor worldIn, BlockPos position) {
        return worldIn.getBiome(position).is(PMBiomeSpawnTags.CAVE_DWARF_CROCODILE_SPAWN);
    }
    static class CrocodileGroundSwimmerNav extends WaterBoundPathNavigation {

            public CrocodileGroundSwimmerNav(PathfinderMob entity, Level world) {
                super(entity, world);
            }

            @Override
            protected boolean canUpdatePath() {
                return true;
            }

            @Override
            protected PathFinder createPathFinder(int p_179679_1_) {
                this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
                return new PathFinder(this.nodeEvaluator, p_179679_1_);
            }

            @Override
            public boolean isStableDestination(BlockPos pos) {
                BlockPos blockPos = pos.below();
                BlockState state = this.level.getBlockState(blockPos);
                return this.level.getBlockState(pos).is(Blocks.WATER) || !state.isAir();
            }
        }

    public Map<String, Vector3f> getModelRotationValues() {
        return this.modelRotationValues;
    }

    class CrocodileLookControl extends SmoothSwimmingLookControl {
        public CrocodileLookControl(DwarfCrocodile p_149210_, int p_149211_) {
            super(p_149210_, p_149211_);
        }
    }

    static class CrocodileMoveControl extends SmoothSwimmingMoveControl {
        private final DwarfCrocodile crocodile;

        public CrocodileMoveControl(DwarfCrocodile p_149215_) {
            super(p_149215_, 85, 10, 0.1F, 0.5F, false);
            this.crocodile = p_149215_;
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(PMItems.DWARF_CROCODILE_EGG.get());
    }

}
