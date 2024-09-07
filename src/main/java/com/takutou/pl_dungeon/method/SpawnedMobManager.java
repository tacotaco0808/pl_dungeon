package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
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
    }
    public void pushSpawnedDungeonMobs(EntityObject mob){
        spawnedDungeonMobs.add(mob);

    }
    /*getter*/
    public List<EntityObject> getAllDungeonMobs() {
        return spawnedDungeonMobs;
    }
}
