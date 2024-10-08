package com.takutou.pl_dungeon.mob;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class EntityObject {
    /**/
    private String mobName;
    private int speed;
    private int maxHealth;
    private int attackDamage;
    private  Plugin plugin;
    private UUID monsterID;
    private EntityType entityType;
    private  Location monsterSpawnLocation;
    private String mobKey;//どの設計書か見分ける
    public static int mobCounter = 0;



    public EntityObject(String mobName, int speed, int maxHealth,int attackDamage, Plugin plugin){
        this.mobName = mobName;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.plugin = plugin;
        this.monsterID = UUID.randomUUID();
    }
    public void spawn(Location location){
        Monster monster = (Monster) location.getWorld().spawnEntity(location,entityType);
        monster.setCustomName(mobName);
        monster.setCustomNameVisible(true);
        monster.getEquipment().clear();
        monster.setCanPickupItems(false);
        setSpeed(monster,speed);
        setMaxHealth(monster, maxHealth);
        setAttackDamage(monster,attackDamage);
        //プラグインで作成したモンスターであることを提示
        String MY_PLUGIN_MOB = "spawned_by_dungeonpl";
        monster.setMetadata(MY_PLUGIN_MOB,new FixedMetadataValue(plugin,true));


        EntityObject.mobCounter++;

    }

    /*データ取得*/
    public String getMobName() {
        return mobName;
    }
    public int getSpeed() {
        return speed;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getAttackDamage() {
        return attackDamage;
    }
    public Plugin getPlugin() {
        return plugin;
    }
    public EntityType getEntityType() {
        return entityType;
    }
    public Location getMonsterSpawnLocation(){
        return this.monsterSpawnLocation;
    }
    public String getMobKey(){return this.mobKey;}
    public UUID getMonsterID(){
        return this.monsterID;
    }
    public Map<String,Object> getMonsterData(){//基本のステータスを取得
        Map<String,Object> entityData = new HashMap<>();
        entityData.put("mobName",mobName);
        entityData.put("speed", speed);
        entityData.put("maxHealth", maxHealth);
        entityData.put("attackDamage", attackDamage);
        entityData.put("mobType",entityType);
        entityData.put("mobKey",mobKey);
        return entityData;
    }

    /*データセット*/
    public void setEntityType(EntityType entityType){
        this.entityType = entityType;
    }
    public void setMonsterID(UUID monsterID) {
        this.monsterID = monsterID;
    }
    public void setMonsterSpawnLocation(Location monsterSpawnLocation) {
        this.monsterSpawnLocation = monsterSpawnLocation;
    }
    public void setMobKey(String key){
        this.mobKey = key;
    }
    public void setSpeed(Monster monster, int speed) {
        if (monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED) != null) {
            double baseSpeed = 0.01; // 基本の移動速度
            monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseSpeed * speed);
        }
    }
    public void setMaxHealth(Monster monster, int maxHealth) {
        if (monster.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
            monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            monster.setHealth(maxHealth); // 体力を最大体力に設定
        }
    }
    // 攻撃力を設定するメソッドを追加
    public void setAttackDamage(Monster monster, int attackDamage) {
        if (monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
            monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
        }
    }



}
