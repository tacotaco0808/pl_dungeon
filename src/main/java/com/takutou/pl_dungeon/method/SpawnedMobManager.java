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

public class SpawnedMobManager {
    private Plugin plugin;
    private List<EntityObject> spawnedDungeonMobs = new ArrayList<>();//スポーンしたMOBが入るリスト
    //ファイル読み込み
    private File mobDataFile;
    private FileConfiguration mobDataConfig;
    public SpawnedMobManager(Plugin plugin){
        this.plugin = plugin;
        this.mobDataFile = new File(plugin.getDataFolder(),"spawnedmobs.yml");
        this.mobDataConfig = YamlConfiguration.loadConfiguration(mobDataFile);
    }
    public void pushSpawnedDungeonMobs(EntityObject mob){
        spawnedDungeonMobs.add(mob);
        this.saveMobData(mob);
    }
    private void saveMobData(EntityObject mob) {
        String path = "mobs." + mob.getMonsterID().toString();
        Location location = mob.getMonsterSpawnLocation();
        mobDataConfig.set(path + ".mobKey", mob.getMobKey());
        mobDataConfig.set(path + ".location.world", location.getWorld().getName());
        mobDataConfig.set(path + ".location.x", location.getX());
        mobDataConfig.set(path + ".location.y", location.getY());
        mobDataConfig.set(path + ".location.z", location.getZ());
        try {
            mobDataConfig.save(mobDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*getter*/
    public List<EntityObject> getAllDungeonMobs() {
        return spawnedDungeonMobs;
    }
}
