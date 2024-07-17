package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.DungeonZombie;
import com.takutou.pl_dungeon.mob.TestZombie;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
/*
* judgeAndSpawn: ここで指定したIDを持ったアイテムか判定する
* */
public class JudgeAndSpawnMob {
    String entityName ;
    PlayerInteractEvent e;

    public JudgeAndSpawnMob(String entityName, PlayerInteractEvent event){
        this.entityName = entityName;
        this.e = event;
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
            new TestZombie("aho",5,5);
            TestZombie.spawn(spawnLocation,EntityType.ZOMBIE);
        }
    }
}
