package com.takutou.pl_dungeon.mob;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class DungeonZombie extends EntityObject{
    public DungeonZombie(String mobName, int speed, int maxHealth,int attackDamage, Plugin plugin){
        super(mobName,speed,maxHealth,attackDamage,plugin);
        super.setEntityType(EntityType.ZOMBIE);
    }
    @Override
    public void spawn(Location location){
        Monster monster = (Monster) location.getWorld().spawnEntity(location,getEntityType());
        monster.setCustomName("getMobName()");
        monster.setCustomNameVisible(true);
        monster.getEquipment().clear();
        monster.setCanPickupItems(false);
        setSpeed(monster,getSpeed());
        setMaxHealth(monster, getMaxHealth());
        setAttackDamage(monster,getAttackDamage());
        //プラグインで作成したモンスターであることを提示
        String MY_PLUGIN_MOB = "spawned_by_dungeonpl";
        monster.setMetadata(MY_PLUGIN_MOB,new FixedMetadataValue(getPlugin(),true));
        String RESPAWN_MOB = "respawn_mob";
        monster.setMetadata(RESPAWN_MOB,new FixedMetadataValue(getPlugin(),true));
        //UUIDとスポーン位置を保存
        setMonsterID(monster.getUniqueId());
        setMonsterSpawnLocation(location);
        EntityObject.mobCounter++;
    }
 }
