package com.takutou.pl_dungeon.mob;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class DungeonZombie {
    public DungeonZombie(Location location){
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        zombie.setCustomName(ChatColor.DARK_RED+"ゾンビ");
        zombie.setCustomNameVisible(true);
    }
 }
