package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CreatedMobManager {
    private Plugin plugin;
    private List<EntityObject> createdDungeonMobs = new ArrayList<>();
    public CreatedMobManager(Plugin plugin){
        this.plugin = plugin;
    }
    public void pushCreatedDungeonMob(MobFactory factory, String mobName, int speed, int maxHealth, int attackDamage, Location location){
        EntityObject mob = factory.createMob(mobName,speed,maxHealth,attackDamage,plugin);
        createdDungeonMobs.add(mob);
    }
    /*getter*/
    public List<EntityObject> getAllDungeonMobs() {
        return createdDungeonMobs;
    }

}
