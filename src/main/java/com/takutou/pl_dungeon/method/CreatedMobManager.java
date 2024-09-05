package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatedMobManager {
    private Plugin plugin;
    private List<EntityObject> createdDungeonMobs = new ArrayList<>();
    private Map<String, MobFactory> createdMobFactoryMap;
    private File mobDataFile;
    private FileConfiguration mobDataConfig;
    public CreatedMobManager(Plugin plugin){
        this.plugin = plugin;
        // モブタイプに対応するファクトリクラスをマッピング
        this.createdMobFactoryMap = new HashMap<>();
        createdMobFactoryMap.put("testzombie", new TestZombieFactory());
        createdMobFactoryMap.put("dungeonzombie", new DungeonZombieFactory());
        //configファイル設置&load
        this.mobDataFile = new File(plugin.getDataFolder(), "createdmobs.yml");
        this.mobDataConfig = YamlConfiguration.loadConfiguration(mobDataFile);
        loadMobs();
    }
    public void pushCreatedDungeonMob(MobFactory factory, String mobName, int speed, int maxHealth, int attackDamage){
        EntityObject mob = factory.createMob(mobName,speed,maxHealth,attackDamage,plugin);
        createdDungeonMobs.add(mob);//リストに追加
        this.saveMobData(mob);//ファイルに保存
    }
    /*file loading*/
    private void saveMobData(EntityObject mob) {
        String path = "mobs." + mob.getMonsterID().toString();
        mobDataConfig.set(path + ".type", mob.getClass().getSimpleName().toLowerCase());
        mobDataConfig.set(path + ".name", mob.getMobName());
        mobDataConfig.set(path + ".speed", mob.getSpeed());
        mobDataConfig.set(path + ".maxHealth", mob.getMaxHealth());
        mobDataConfig.set(path + ".attackDamage", mob.getAttackDamage());

        try {
            mobDataConfig.save(mobDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadMobs() {
        // 既存のリストをクリア
        createdDungeonMobs.clear();
        //　ymlからリストを読み込み
        if (mobDataConfig.contains("mobs")) {
            for (String key : mobDataConfig.getConfigurationSection("mobs").getKeys(false)) {
                String mobType = mobDataConfig.getString("mobs." + key + ".type");
                String mobName = mobDataConfig.getString("mobs." + key + ".name");
                int speed = mobDataConfig.getInt("mobs." + key + ".speed");
                int maxHealth = mobDataConfig.getInt("mobs." + key + ".maxHealth");
                int attackDamage = mobDataConfig.getInt("mobs." + key + ".attackDamage");

                // モブタイプに対応するファクトリを取得
                MobFactory factory = createdMobFactoryMap.get(mobType.toLowerCase());
                // ファクトリが存在する場合のみモブを生成
                if (factory != null) {
                    EntityObject mob = factory.createMob(mobName, speed, maxHealth, attackDamage, plugin);
                    // リストに追加 (この時点で saveMobData は呼び出さない)
                    createdDungeonMobs.add(mob);
                }
            }
        }
    }
    /*getter*/
    public List<EntityObject> getAllDungeonMobs() {
        return createdDungeonMobs;
    }

}
