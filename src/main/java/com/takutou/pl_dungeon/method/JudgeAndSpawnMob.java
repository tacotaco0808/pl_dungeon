package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import com.takutou.pl_dungeon.mob.TestZombie;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

/*
* judgeAndSpawn: ここで指定したIDを持ったアイテムか判定する
* */
public class JudgeAndSpawnMob {
    String entityName ;
    PlayerInteractEvent e;
    private final Plugin plugin;
    public JudgeAndSpawnMob(String entityName, PlayerInteractEvent event, Plugin plugin){
        this.entityName = entityName;
        this.e = event;
        this.plugin = plugin;
    }
    public void judgeAndSpawn(){
        Player player = e.getPlayer();
        if(e.getItem() != null && e.getItem().getItemMeta() != null
            && e.getItem().getItemMeta().getLore() !=null
            && e.getItem().getItemMeta().getLore().contains(ChatColor.BLUE + "Spawns a dungeon:" + entityName)){
            //標準のスポーンエッグの機能を無効化
            e.setCancelled(true);
            //右クリックしたアイテムのメタデータ
            player.sendMessage(ChatColor.BLUE + "Spawns a dungeon:" + entityName);
            //右クリック先のブロックにスポーン位置を設定
            Location spawnLocation;
            if(e.getClickedBlock().isPassable()){
                spawnLocation = e.getClickedBlock().getLocation().add(0.5,0,0.5);
            }else{
                spawnLocation = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().add(0.5,0,0.5);
            }
            //モンスターをスポーン
            TestZombie testZombie = new TestZombie("aho",3,3,1,plugin);
            testZombie.spawn(spawnLocation,EntityType.ZOMBIE);
            UUID monsterid = testZombie.getMonsterID();
            player.sendMessage("MonsterID:"+monsterid+"\n MonsterSum:"+ EntityObject.mobCounter);
            player.sendMessage("MonsterSpawn:"+testZombie.getSpawnLocation());


        }
    }
}
