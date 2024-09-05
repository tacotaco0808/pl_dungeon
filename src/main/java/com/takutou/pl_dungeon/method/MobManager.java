package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MobManager {
    private Plugin plugin;
    private List<EntityObject> dungeonMobs = new ArrayList<>();
    private File mobDataFile;
    private FileConfiguration mobDataConfig;

    public MobManager(Plugin plugin) {
        this.plugin = plugin;
        this.mobDataFile = new File(plugin.getDataFolder(), "mobs.yml");
        this.mobDataConfig = YamlConfiguration.loadConfiguration(mobDataFile);
        loadMobs();
    }

    public void spawnDungeonMob(MobFactory factory, String mobName, int speed, int maxHealth, int attackDamage, Location location) {
        EntityObject mob = factory.createMob(mobName, speed, maxHealth, attackDamage, plugin);
        mob.spawn(location);
        dungeonMobs.add(mob);

        saveMobData(mob, location);
    }

    private void saveMobData(EntityObject mob, Location location) {
        String path = "mobs." + mob.getMonsterID().toString();
        mobDataConfig.set(path + ".type", mob.getClass().getSimpleName().toLowerCase());
        mobDataConfig.set(path + ".name", mob.getMobName());
        mobDataConfig.set(path + ".speed", mob.getSpeed());
        mobDataConfig.set(path + ".maxHealth", mob.getMaxHealth());
        mobDataConfig.set(path + ".attackDamage", mob.getAttackDamage());
        mobDataConfig.set(path + ".location.world", location.getWorld().getName());
        mobDataConfig.set(path + ".location.x", location.getX());
        mobDataConfig.set(path + ".location.y", location.getY());
        mobDataConfig.set(path + ".location.z", location.getZ());
        mobDataConfig.set(path + ".location.yaw", location.getYaw());
        mobDataConfig.set(path + ".location.pitch", location.getPitch());

        try {
            mobDataConfig.save(mobDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMobs() {
        if (mobDataConfig.contains("mobs")) {
            for (String key : mobDataConfig.getConfigurationSection("mobs").getKeys(false)) {
                String mobType = mobDataConfig.getString("mobs." + key + ".type");
                String mobName = mobDataConfig.getString("mobs." + key + ".name");
                int speed = mobDataConfig.getInt("mobs." + key + ".speed");
                int maxHealth = mobDataConfig.getInt("mobs." + key + ".maxHealth");
                int attackDamage = mobDataConfig.getInt("mobs." + key + ".attackDamage");

                String worldName = mobDataConfig.getString("mobs." + key + ".location.world");
                double x = mobDataConfig.getDouble("mobs." + key + ".location.x");
                double y = mobDataConfig.getDouble("mobs." + key + ".location.y");
                double z = mobDataConfig.getDouble("mobs." + key + ".location.z");
                float yaw = (float) mobDataConfig.getDouble("mobs." + key + ".location.yaw");
                float pitch = (float) mobDataConfig.getDouble("mobs." + key + ".location.pitch");
                Location location = new Location(plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);

                MobFactory factory = getFactoryByType(mobType);
                if (factory != null) {
                    spawnDungeonMob(factory, mobName, speed, maxHealth, attackDamage, location);
                }
            }
        }
    }

    private MobFactory getFactoryByType(String mobType) {
        switch (mobType.toLowerCase()) {
            case "testzombie":
                return new TestZombieFactory();
            case "dungeonzombie":
                return new DungeonZombieFactory();
            default:
                return null;
        }
    }

    public EntityObject getDungeonMob(UUID mobID) {
        for (EntityObject mob : dungeonMobs) {
            if (mob.getMonsterID().equals(mobID)) {
                return mob;
            }
        }
        return null;
    }

    public List<EntityObject> getAllDungeonMobs() {
        return dungeonMobs;
    }

    public void removeDungeonMob(UUID mobID) {
        dungeonMobs.removeIf(mob -> mob.getMonsterID().equals(mobID));
        mobDataConfig.set("mobs." + mobID.toString(), null);
        try {
            mobDataConfig.save(mobDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
