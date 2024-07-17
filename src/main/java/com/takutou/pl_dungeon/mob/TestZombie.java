package com.takutou.pl_dungeon.mob;

import org.bukkit.plugin.Plugin;

public class TestZombie extends EntityObject{
    public TestZombie(String mobName, int speed, int maxHealth,int attackDamage, Plugin plugin){
        super(mobName,speed,maxHealth,attackDamage,plugin);
    }
}
