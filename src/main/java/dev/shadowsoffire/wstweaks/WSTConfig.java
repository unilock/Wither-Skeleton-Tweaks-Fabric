package dev.shadowsoffire.wstweaks;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.IntegerRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.SerializedName;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class WSTConfig extends ReflectiveConfig {
    @Comment("The chance for a skull shard to drop. 1 = 100%, 0.5 = 50%, etc")
    @SerializedName("shard_drop_chance")
    @FloatRange(min = 0, max = 1)
    public final TrackedValue<Float> shardDropChance = value(1.0F);

    @Comment("If skeletons in ALL biomes are converted, instead of just the nether.")
    @SerializedName("convert_all_biomes")
    public final TrackedValue<Boolean> allBiomes = value(false);

    @Comment("The chance for skeletons to be converted in all biomes, when enabled. 1 = 100%, 0.5 = 50%, etc")
    @SerializedName("all_biomes_chance")
    @FloatRange(min = 0, max = 1)
    public final TrackedValue<Float> allBiomesChance = value(0.15F);

    @Comment("If stone swords and other trash are removed from wither skeleton drop tables.")
    @SerializedName("delete_swords")
    public final TrackedValue<Boolean> delSwords = value(true);

    @Comment("If converted skeletons receive bows (Wither Skeletons always shoot flaming arrows).")
    @SerializedName("give_bows")
    public final TrackedValue<Boolean> giveBows = value(true);

    @Comment("[Unsynced] [Requires Restart] The durability of immolation blades.")
    @SerializedName("blades_durability")
    @IntegerRange(min = 1, max = 65536)
    public final TrackedValue<Integer> swordDurability = value(4096);

    @Comment("[Unsynced] [Requires Restart] The attack damage of immolation blades. This is a modifier, so the real value is always 1 higher.")
    @SerializedName("blades_attack_damage")
    @FloatRange(min = 1, max = 4096)
    public final TrackedValue<Float> swordDamage = value(11.0F);

    @Comment("[Unsynced] [Requires Restart] The attack speed of immolation blades. This is a modifier, so the real value is a bit different.")
    @SerializedName("blades_attack_speed")
    @FloatRange(min = -4096, max = 4096)
    public final TrackedValue<Float> swordAtkSpeed = value(-2.0F);

}
