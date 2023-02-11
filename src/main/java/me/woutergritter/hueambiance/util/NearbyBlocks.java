package me.woutergritter.hueambiance.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;

public class NearbyBlocks {

    public static Optional<Block> getNearbyBlockOfType(Location location, int radius, Material type) {
        return getNearbyBlockOfTypes(location, radius, singleton(type));
    }

    public static Optional<Block> getNearbyBlockOfTypes(Location location, int radius, Set<Material> types) {
        World world = requireNonNull(location.getWorld());

        int cx = location.getBlockX();
        int cy = location.getBlockY();
        int cz = location.getBlockZ();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int y = cy - radius; y <= cy + radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (types.contains(block.getType())) {
                        return Optional.of(block);
                    }
                }
            }
        }

        return Optional.empty();
    }
}
