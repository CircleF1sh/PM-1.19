package com.fish.pm.common.entities.item;

import com.fish.pm.PM;
import com.fish.pm.common.entities.TerrorChicken;
import com.fish.pm.registry.PMEntities;
import com.fish.pm.registry.PMItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class TerrorChickenThrownEgg extends Item {
    public TerrorChickenThrownEgg(Item.Properties p_41126_) {
        super(p_41126_);
    }

    public InteractionResultHolder<ItemStack> use(Level p_41128_, Player p_41129_, InteractionHand p_41130_) {
        ItemStack itemstack = p_41129_.getItemInHand(p_41130_);
        p_41128_.playSound((Player)null, p_41129_.getX(), p_41129_.getY(), p_41129_.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (p_41128_.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!p_41128_.isClientSide) {
            TerrorThrownEgg thrownegg = new TerrorThrownEgg(p_41128_, p_41129_);
            thrownegg.setItem(itemstack);
            thrownegg.shootFromRotation(p_41129_, p_41129_.getXRot(), p_41129_.getYRot(), 0.0F, 1.5F, 1.0F);
            p_41128_.addFreshEntity(thrownegg);
        }

        p_41129_.awardStat(Stats.ITEM_USED.get(this));
        if (!p_41129_.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, p_41128_.isClientSide());
    }

    private class TerrorThrownEgg extends ThrowableItemProjectile {

        public TerrorThrownEgg(EntityType<? extends ThrownEgg> p_37473_, Level p_37474_) {
            super(p_37473_, p_37474_);
        }

        public TerrorThrownEgg(Level p_37481_, LivingEntity p_37482_) {
            super(EntityType.EGG, p_37482_, p_37481_);
        }

        public TerrorThrownEgg(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
            super(EntityType.EGG, p_37477_, p_37478_, p_37479_, p_37476_);
        }

        public void handleEntityEvent(byte p_37484_) {
            if (p_37484_ == 3) {
                double d0 = 0.08D;

                for (int i = 0; i < 8; ++i) {
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
                }
            }

        }

        protected void onHitEntity(EntityHitResult p_37486_) {
            super.onHitEntity(p_37486_);
            p_37486_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
        }
        protected void onHit(HitResult p_37488_) {
            super.onHit(p_37488_);
            if (!this.level.isClientSide) {
                if (this.random.nextInt(8) == 0) {
                    int i = 1;
                    if (this.random.nextInt(32) == 0) {
                        i = 4;
                    }

                    for (int j = 0; j < i; ++j) {
                        TerrorChicken terrorChicken = PMEntities.TERROR_CHICKEN.get().create(this.level);
                        terrorChicken.setAge(-24000);
                        terrorChicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        this.level.addFreshEntity(terrorChicken);
                    }
                }

                this.level.broadcastEntityEvent(this, (byte) 3);
                this.discard();
            }

        }

        protected Item getDefaultItem() {
            return PMItems.TERROR_CHICKEN_EGG.get();
        }
    }
}
