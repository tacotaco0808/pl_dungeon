package com.takutou.pl_dungeon.mob;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;

abstract class EntityObject {
    String entityName;
    String mobName;
    int speed;
    int maxHealth;

    public EntityObject(String entityName, String mobName, int speed, int maxHealth){
        this.entityName = entityName;
        this.mobName = mobName;
        this.speed = speed;
        this.maxHealth = maxHealth;

    }
    public void spawn(Location location, EntityType entityType){
        Monster monster = (Monster) location.getWorld().spawnEntity(location,entityType);
        monster.setCustomName(mobName);
        monster.setCustomNameVisible(true);
        setSpeed(monster,speed);
        setMaxHealth(monster, maxHealth);
    }
    public void setSpeed(Monster monster, int speed) {
        if (monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            double baseSpeed = 0.2; // 基本の移動速度
            monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseSpeed * speed);
        }
    }
    public void setMaxHealth(Monster monster, int maxHealth) {
        if (monster.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
            monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            monster.setHealth(maxHealth); // 体力を最大体力に設定
        }
    }

}
