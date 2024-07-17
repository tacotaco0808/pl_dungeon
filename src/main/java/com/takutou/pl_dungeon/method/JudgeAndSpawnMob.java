package com.takutou.pl_dungeon.method;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

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
            player.sendMessage(ChatColor.BLUE + "Spawns a dungeon:" + entityName);
        }
    }
}
