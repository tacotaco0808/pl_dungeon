package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.DungeonZombie;
import com.takutou.pl_dungeon.mob.EntityObject;
import com.takutou.pl_dungeon.mob.TestZombie;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MobManager {
    /*エンティティインスタンスを管理するマネージャー*/
    private Plugin plugin;
    public  MobManager(Plugin plugin){
        this.plugin = plugin;
    }
    private List<EntityObject> dungeonMobs = new ArrayList<>();
    public void spawnDungeonMob(String mobType, String mobName, int speed, int maxHealth, int attackDamage, Location location , Player player){
        if(Objects.equals(mobType, "testzombie")){
            TestZombie testZombie = new TestZombie(mobName,speed,maxHealth,attackDamage,plugin);
            testZombie.spawn(location);
            dungeonMobs.add(testZombie);
        }else if(Objects.equals(mobType, "dungeonzombie")){
            DungeonZombie dungeonZombie = new DungeonZombie(mobName,speed,maxHealth,attackDamage,plugin);
            dungeonZombie.spawn(location);
            dungeonMobs.add(dungeonZombie);
        }else{
            throw new IllegalArgumentException("作成可能なmobtypeの種類 :\ntestzombie\ndungeonzombie");
        }
    }
    public EntityObject getDungeonMob(UUID mobID){
        for(EntityObject mob : dungeonMobs){
            if(mob.getMonsterID().equals(mobID)){
                return mob;
            }
        }
        return null;
    }
    public List<EntityObject> getAllDungeonMobs(){
        return dungeonMobs;
    }
    public void removeDungeonMob(UUID mobID){
        dungeonMobs.removeIf(mob -> mob.getMonsterID().equals(mobID));
    }
}
