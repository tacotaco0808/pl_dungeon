package com.takutou.pl_dungeon.mob;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;

import java.util.UUID;

abstract class EntityObject {
    static String mobName;
    static int speed;
    static int maxHealth;
    static UUID monsterID;
    public EntityObject(String mobName, int speed, int maxHealth){
        EntityObject.mobName = mobName;
        EntityObject.speed = speed;
        EntityObject.maxHealth = maxHealth;

    }
    public static void spawn(Location location, EntityType entityType){
        Monster monster = (Monster) location.getWorld().spawnEntity(location,entityType);
        monster.setCustomName(mobName);
        monster.setCustomNameVisible(true);
        monster.getEquipment().clear();
        monster.setCanPickupItems(false);
        setSpeed(monster,speed);
        setMaxHealth(monster, maxHealth);
        //UUID取得
        monsterID = monster.getUniqueId();

    }
    public static UUID getMonsterID(){
        return monsterID;
    }
    public static void setSpeed(Monster monster, int speed) {
        if (monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            double baseSpeed = 0.01; // 基本の移動速度
            monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseSpeed * speed);
        }
    }
    public static void setMaxHealth(Monster monster, int maxHealth) {
        if (monster.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
            monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            monster.setHealth(maxHealth); // 体力を最大体力に設定
        }
    }


}
