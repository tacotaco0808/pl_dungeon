package com.takutou.pl_dungeon;

import org.bukkit.plugin.java.JavaPlugin;

public final class Pl_dungeon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        SpawnEntity spawnEntity = new SpawnEntity(this);
        // コマンドエクゼキューターの登録
        this.getCommand("spawnzombie").setExecutor(spawnEntity);
        // イベントリスナーの登録
        getServer().getPluginManager().registerEvents(spawnEntity, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
