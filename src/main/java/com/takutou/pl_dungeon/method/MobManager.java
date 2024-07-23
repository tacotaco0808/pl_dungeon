package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import com.takutou.pl_dungeon.mob.TestZombie;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MobManager {
    private Plugin plugin;
    public  MobManager(Plugin plugin){
        this.plugin = plugin;
    }
    private List<EntityObject> mobs = new ArrayList<>();
    public void spawnDungeonMob(String mobType,String mobName, int speed, int maxHealth, int attackDamage, Location location){
        if(mobType == "testzombie"){
            //a
        }
    }
}
