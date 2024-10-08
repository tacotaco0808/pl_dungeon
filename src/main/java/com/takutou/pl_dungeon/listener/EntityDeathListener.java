package com.takutou.pl_dungeon.listener;

import com.takutou.pl_dungeon.Pl_dungeon;
import com.takutou.pl_dungeon.method.SpawnedMobManager;
import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class EntityDeathListener implements Listener {
    private final Pl_dungeon plugin;
    public EntityDeathListener(Pl_dungeon plugin){
        this.plugin = plugin;
    }
    private final String MY_PLUGIN_MOB = "spawned_by_dungeonpl";
    private final String RESPAWN_ZOMBIE = "respawn_mob";
    private SpawnedMobManager spawnedMobManager;
    public EntityDeathListener(SpawnedMobManager spawnedMobManager, Pl_dungeon plugin) {
        this.spawnedMobManager = spawnedMobManager;
        this.plugin = plugin;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Monster){
            Monster monster = (Monster) e.getEntity();
            //プラグインによって生成されたモンスターかどうか
            if(monster.hasMetadata(MY_PLUGIN_MOB)){
                UUID monsterID = monster.getUniqueId();
                e.getDrops().clear();
                e.setDroppedExp(0);
                Player killer = monster.getKiller();
                if(killer != null ){
                    killer.sendMessage("killed monsterid:"+monsterID);
                }
                EntityObject.mobCounter--;
            }
            if(monster.hasMetadata(MY_PLUGIN_MOB) && monster.hasMetadata(RESPAWN_ZOMBIE)){
                //モンスター情報を取得
                UUID monsterID = monster.getUniqueId();
                EntityObject dungeonMonster= spawnedMobManager.getMobById(monsterID);
                e.getDrops().clear();
                e.setDroppedExp(0);
                Player killer = monster.getKiller();
                if(killer != null ){
                    killer.sendMessage("killed respawn zombie!!");
                    dungeonMonster.spawn(dungeonMonster.getMonsterSpawnLocation());
                    spawnedMobManager.removeDungeonMobById(monsterID);//古いモンスターを削除
                    spawnedMobManager.pushSpawnedDungeonMobs(dungeonMonster);//新しいモンスターをプッシュ

                }
                EntityObject.mobCounter--;
            }
        }
    }
}
