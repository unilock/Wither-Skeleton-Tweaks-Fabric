package dev.shadowsoffire.wstweaks;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dev.shadowsoffire.wstweaks.WitherSkeletonTweaks.CONFIG;

public class WSTEvents {

    public static boolean join(Entity entity) {
        if (entity instanceof Skeleton skeleton && skeleton.getCustomData().getBoolean("wst.removed")) {
            WitherSkeleton witherSkel = skeleton.convertTo(EntityType.WITHER_SKELETON, true);
            if (witherSkel == null) return true;
            ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(skeleton, witherSkel, true);
            if (CONFIG.giveBows.value()) witherSkel.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
            return false;
        }
        return true;
    }

    public static boolean delSwords(LivingEntity target, Collection<ItemEntity> drops) {
        if (CONFIG.delSwords.value() && !target.level().isClientSide && target instanceof AbstractSkeleton) {

            List<ItemEntity> toRemove = new ArrayList<>();
            for (ItemEntity entity : drops) {
                ItemStack stack = entity.getItem();
                if (stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.BOW) {
                    CompoundTag tag = stack.getTag();
                    if (tag != null && (tag.contains("Damage") && tag.getAllKeys().size() > 2 || tag.getAllKeys().size() > 1)) continue;
                    toRemove.add(entity);
                }
            }

            for (ItemEntity i : toRemove)
                drops.remove(i);
        }

        return false;
    }
}
