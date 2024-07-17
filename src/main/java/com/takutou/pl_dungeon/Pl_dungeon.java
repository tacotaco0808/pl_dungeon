package com.takutou.pl_dungeon;

import com.takutou.pl_dungeon.command.DungeonEntitySpawnEgg;
import com.takutou.pl_dungeon.command.DungeonZombieSpawnEgg;
import com.takutou.pl_dungeon.listener.InteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pl_dungeon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        SpawnEntity spawnEntity = new SpawnEntity(this);
        // コマンドエクゼキューターの登録
        this.getCommand("spawnzombie").setExecutor(spawnEntity);
        this.getCommand("givedungeonzombieegg").setExecutor(new DungeonZombieSpawnEgg());
        this.getCommand("dungeon").setExecutor(new DungeonEntitySpawnEgg());
        // イベントリスナーの登録
        getServer().getPluginManager().registerEvents(spawnEntity, this);
        getServer().getPluginManager().registerEvents(new InteractListener(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
