package com.takutou.pl_dungeon;

import com.takutou.pl_dungeon.command.CreateDungeonMob;
import com.takutou.pl_dungeon.command.DungeonEntitySpawnEgg;
import com.takutou.pl_dungeon.command.GetDungeonMobList;
import com.takutou.pl_dungeon.listener.EntityDeathListener;
import com.takutou.pl_dungeon.listener.InteractListener;
import com.takutou.pl_dungeon.method.CreatedMobManager;
import com.takutou.pl_dungeon.method.MobManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pl_dungeon extends JavaPlugin {
    private MobManager mobManager;
    private CreatedMobManager createdMobManager;
    @Override
    public void onEnable() {
        mobManager = new MobManager(this);
        createdMobManager = new CreatedMobManager(this);
        // Plugin startup logic
        // コマンドエクゼキューターの登録
        this.getCommand("dungeon").setExecutor(new DungeonEntitySpawnEgg(this));
        this.getCommand("dungeoncreate").setExecutor(new CreateDungeonMob(createdMobManager));
        this.getCommand("dungeonmoblist").setExecutor(new GetDungeonMobList(createdMobManager));
        // イベントリスナーの登録
        getServer().getPluginManager().registerEvents(new InteractListener(createdMobManager,this),this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(createdMobManager,this),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
