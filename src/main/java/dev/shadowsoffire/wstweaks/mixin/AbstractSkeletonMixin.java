package dev.shadowsoffire.wstweaks.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.shadowsoffire.wstweaks.WitherSkeletonTweaks.CONFIG;

@Mixin(AbstractSkeleton.class)
public class AbstractSkeletonMixin {
    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void finalizeSpawn(CallbackInfoReturnable<SpawnGroupData> cir) {
        if ((AbstractSkeleton) (Object) this instanceof Skeleton skeleton && !skeleton.isRemoved()) {
            RandomSource rand = skeleton.level().getRandom();
            if (!skeleton.level().isClientSide()) {
                if (skeleton.level().dimension() == Level.NETHER || CONFIG.allBiomes.value() && skeleton.level().getRawBrightness(skeleton.blockPosition(), 0) < 9 && rand.nextFloat() < CONFIG.allBiomesChance.value()) {
                    skeleton.getCustomData().putBoolean("wst.removed", true);
                }
            }
        }
    }
}
