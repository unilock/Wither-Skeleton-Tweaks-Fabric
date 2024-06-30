package dev.shadowsoffire.wstweaks;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class WitherSkeletonTweaks implements ModInitializer {

    public static final String MODID = "wstweaks";
    public static final WSTConfig CONFIG = WSTConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MODID, WSTConfig.class);
    public static final Tier IMMOLATION = new Tier() {
        @Override
        public int getUses() {
            return CONFIG.swordDurability.value();
        }

        @Override
        public float getSpeed() {
            return CONFIG.swordAtkSpeed.value();
        }

        @Override
        public float getAttackDamageBonus() {
            return CONFIG.swordDamage.value();
        }

        @Override
        public int getLevel() {
            return 9;
        }

        @Override
        public int getEnchantmentValue() {
            return 30;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.NETHER_STAR);
        }
    };

    static Item fragment, lavaBlade, blazeBlade;

    @Override
    public void onInitialize() {
        registerItems();
        registerGMLSer();
        tabs();

        LivingEntityEvents.ON_JOIN_WORLD.register((entity, world, loadedFromDisk) -> WSTEvents.join(entity));
        LivingEntityEvents.DROPS.register((target, source, drops, lootingLevel, recentlyHit) -> WSTEvents.delSwords(target, drops));
    }

    public void tabs() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(e -> {
            e.accept(lavaBlade);
            e.accept(blazeBlade);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(e -> {
            e.accept(fragment);
        });
    }

    private void registerItems() {
        fragment = Registry.register(BuiltInRegistries.ITEM, loc("fragment"), new Item(new Item.Properties()));
        lavaBlade = Registry.register(BuiltInRegistries.ITEM, loc("lava_blade"), new ItemImmolationBlade());
        blazeBlade = Registry.register(BuiltInRegistries.ITEM, loc("blaze_blade"), new ItemImmolationBlade());
    }

    private void registerGMLSer() {
        Registry.register(PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), loc("wstmodifier"), WSTLootModifier.CODEC.get());
    }

    public static ResourceLocation loc(String s) {
        return new ResourceLocation(MODID, s);
    }

}
