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

public class SpawnedMobManager {
    private CreatedMobManager createdMobManager;
    private Plugin plugin;
    private List<EntityObject> spawnedDungeonMobs = new ArrayList<>();//スポーンしたMOBが入るリスト
    //ファイル読み込み
    private File mobDataFile;
    private FileConfiguration mobDataConfig;
    public SpawnedMobManager(CreatedMobManager createdMobManager,Plugin plugin){
        this.plugin = plugin;
        this.mobDataFile = new File(plugin.getDataFolder(),"spawnedmobs.yml");
        this.mobDataConfig = YamlConfiguration.loadConfiguration(mobDataFile);
        this.createdMobManager = createdMobManager;
        loadMobs();
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
    private void loadMobs() {
        // 既存のリストをクリア
        spawnedDungeonMobs.clear();
        //　ymlからリストを読み込み
        if (mobDataConfig.contains("mobs")) {
            for (String mobId : mobDataConfig.getConfigurationSection("mobs").getKeys(false)) {
                String mobKey = mobDataConfig.getString("mobs." + mobId + ".mobKey");
                String worldName = mobDataConfig.getString("mobs." + mobId + ".location.world");
                double x = mobDataConfig.getDouble("mobs." + mobId + ".location.x");
                double y = mobDataConfig.getDouble("mobs." + mobId + ".location.y");
                double z = mobDataConfig.getDouble("mobs." + mobId + ".location.z");
                Location location = new Location(plugin.getServer().getWorld(worldName), x, y, z, 0, 0);
                //mobKeyから設計図を検索しデータを取得
                EntityObject searchedMob =  createdMobManager.getMobByKey(mobKey);
                //設計図
                searchedMob.setMonsterSpawnLocation(location);
                searchedMob.setMonsterID(UUID.fromString(mobId));
                this.pushSpawnedDungeonMobs(searchedMob);
            }
        }
    }
    /*getter*/
    public List<EntityObject> getAllDungeonMobs() {
        return spawnedDungeonMobs;
    }
    // 指定のUUIDを持つEntityObjectを検索
    public EntityObject getMobById(UUID monsterId) {
        for (EntityObject mob : spawnedDungeonMobs) {
            if (mob.getMonsterID().equals(monsterId)) {
                return mob; // 見つけたら返す
            }
        }
        return null; // 見つからなかった場合はnullを返す
    }
    public void removeDungeonMobById(UUID mobID) {
        spawnedDungeonMobs.removeIf(mob -> mob.getMonsterID().equals(mobID));
        mobDataConfig.set("mobs." + mobID.toString(), null);
        try {
            mobDataConfig.save(mobDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
