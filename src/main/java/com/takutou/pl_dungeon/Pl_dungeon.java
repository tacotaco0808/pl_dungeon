package com.takutou.pl_dungeon;

import com.takutou.pl_dungeon.command.CreateDungeonMob;
import com.takutou.pl_dungeon.command.DungeonEntitySpawnEgg;
import com.takutou.pl_dungeon.command.DungeonZombieSpawnEgg;
import com.takutou.pl_dungeon.listener.EntityDeathListener;
import com.takutou.pl_dungeon.listener.InteractListener;
import com.takutou.pl_dungeon.method.MobManager;
import com.takutou.pl_dungeon.mob.TestZombie;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pl_dungeon extends JavaPlugin {
    private MobManager mobManager;
    @Override
    public void onEnable() {
        mobManager = new MobManager(this);
        // Plugin startup logic
        SpawnEntity spawnEntity = new SpawnEntity(this);
        // コマンドエクゼキューターの登録
        this.getCommand("spawnzombie").setExecutor(spawnEntity);
        this.getCommand("givedungeonzombieegg").setExecutor(new DungeonZombieSpawnEgg());
        this.getCommand("dungeon").setExecutor(new DungeonEntitySpawnEgg(this));
        this.getCommand("dungeoncreate").setExecutor(new CreateDungeonMob(mobManager));
        // イベントリスナーの登録
        getServer().getPluginManager().registerEvents(spawnEntity, this);
        getServer().getPluginManager().registerEvents(new InteractListener(mobManager,this),this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
