package me.woutergritter.hueambiance.util;

import org.bukkit.block.Biome;

import java.awt.*;

public class BiomeColors {

    /**
     * @author Chat-GPT
     */
    public static Color getBiomeColor(Biome biome) {
        return switch (biome) {
            case OCEAN -> new Color(0, 0, 128);
            case PLAINS -> new Color(0, 255, 0);
            case DESERT -> new Color(255, 204, 153);
            case WINDSWEPT_HILLS -> new Color(102, 102, 51);
            case FOREST -> new Color(34, 139, 34);
            case TAIGA -> new Color(0, 128, 128);
            case SWAMP -> new Color(0, 128, 0);
            case MANGROVE_SWAMP -> new Color(51, 102, 51);
            case RIVER -> new Color(0, 128, 255);
            case NETHER_WASTES -> new Color(102, 0, 102);
            case THE_END -> new Color(153, 153, 153);
            case FROZEN_OCEAN -> new Color(128, 128, 255);
            case FROZEN_RIVER -> new Color(255, 255, 255);
            case SNOWY_PLAINS -> new Color(255, 250, 250);
            case MUSHROOM_FIELDS -> new Color(255, 0, 255);
            case BEACH -> new Color(255, 228, 196);
            case JUNGLE -> new Color(107, 142, 35);
            case SPARSE_JUNGLE -> new Color(50, 205, 50);
            case DEEP_OCEAN -> new Color(0, 0, 64);
            case STONY_SHORE -> new Color(128, 128, 128);
            case SNOWY_BEACH -> new Color(250, 240, 230);
            case BIRCH_FOREST -> new Color(144, 238, 144);
            case DARK_FOREST -> new Color(34, 139, 34);
            case SNOWY_TAIGA -> new Color(224, 255, 255);
            case OLD_GROWTH_PINE_TAIGA -> new Color(0, 100, 0);
            case WINDSWEPT_FOREST -> new Color(34, 139, 34);
            case SAVANNA -> new Color(255, 255, 153);
            case SAVANNA_PLATEAU -> new Color(255, 255, 102);
            case BADLANDS -> new Color(255, 102, 0);
            case WOODED_BADLANDS -> new Color(128, 64, 0);
            case SMALL_END_ISLANDS -> new Color(102, 102, 102);
            case END_MIDLANDS -> new Color(153, 153, 153);
            case END_HIGHLANDS -> new Color(89, 121, 144);
            case END_BARRENS -> new Color(109, 140, 165);
            case WARM_OCEAN -> new Color(17, 185, 203);
            case LUKEWARM_OCEAN -> new Color(72, 205, 214);
            case COLD_OCEAN -> new Color(21, 123, 140);
            case DEEP_LUKEWARM_OCEAN -> new Color(36, 168, 187);
            case DEEP_COLD_OCEAN -> new Color(24, 94, 115);
            case DEEP_FROZEN_OCEAN -> new Color(0, 64, 87);
            case THE_VOID -> new Color(51, 51, 51);
            case SUNFLOWER_PLAINS -> new Color(194, 179, 87);
            case WINDSWEPT_GRAVELLY_HILLS -> new Color(135, 117, 64);
            case FLOWER_FOREST -> new Color(140, 180, 140);
            case ICE_SPIKES -> new Color(222, 235, 245);
            case OLD_GROWTH_BIRCH_FOREST -> new Color(109, 140, 87);
            case OLD_GROWTH_SPRUCE_TAIGA -> new Color(73, 123, 97);
            case WINDSWEPT_SAVANNA -> new Color(135, 179, 97);
            case ERODED_BADLANDS -> new Color(165, 140, 115);
            case BAMBOO_JUNGLE -> new Color(155, 179, 87);
            case SOUL_SAND_VALLEY -> new Color(75, 64, 32);
            case CRIMSON_FOREST -> new Color(191, 48, 48);
            case WARPED_FOREST -> new Color(112, 165, 87);
            case BASALT_DELTAS -> new Color(89, 89, 89);
            case DRIPSTONE_CAVES -> new Color(140, 140, 140);
            case LUSH_CAVES -> new Color(87, 179, 179);
            case DEEP_DARK -> new Color(51, 51, 51);
            case MEADOW -> new Color(180, 180, 140);
            case GROVE -> new Color(87, 179, 109);
            case SNOWY_SLOPES -> new Color(255, 255, 255);
            case FROZEN_PEAKS -> new Color(235, 235, 245);
            case JAGGED_PEAKS -> new Color(219, 219, 219);
            case STONY_PEAKS -> new Color(165, 165, 165);
            case CUSTOM -> new Color(0, 0, 0);
            default -> throw new IllegalArgumentException("Unknown biome: " + biome);
        };
    }
}
