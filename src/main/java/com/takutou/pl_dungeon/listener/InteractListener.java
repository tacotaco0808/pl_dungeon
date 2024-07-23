package com.takutou.pl_dungeon.listener;

import com.takutou.pl_dungeon.method.JudgeAndSpawnMob;
import com.takutou.pl_dungeon.method.MobManager;
import com.takutou.pl_dungeon.mob.DungeonZombie;
import com.takutou.pl_dungeon.Pl_dungeon;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class InteractListener implements Listener {
    private final Pl_dungeon plugin;
    private  MobManager mobManager;
    public InteractListener(MobManager mobManager, Pl_dungeon plugin){
        this.mobManager = mobManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction()== Action.RIGHT_CLICK_BLOCK){
            if(e.getHand() != null && e.getHand() == EquipmentSlot.HAND){
                //手に持っているアイテムを判定
                if(e.getItem() != null && e.getItem().getItemMeta() != null
                    && e.getItem().getItemMeta().getLore() !=null
                    && e.getItem().getItemMeta().getLore().contains(ChatColor.GRAY + "spawn a dungeon zombie!")){
                    Location spawnLocation;
                    if(e.getClickedBlock().isPassable()){
                        spawnLocation = e.getClickedBlock().getLocation().add(0.5,0,0.5);
                    }else{
                        spawnLocation = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().add(0.5,0,0.5);
                    }
                    new DungeonZombie(spawnLocation);
                }
                //手に持っているアイテムを判定
                JudgeAndSpawnMob zombieSpawn = new JudgeAndSpawnMob(mobManager,"zombie",e,plugin);
                zombieSpawn.judgeAndSpawn();
                //手に持っているアイテムを判定
                JudgeAndSpawnMob skeletonSpawn = new JudgeAndSpawnMob(mobManager,"skeleton",e,plugin);
                skeletonSpawn.judgeAndSpawn();

            }
        }
    }
}
