package com.takutou.pl_dungeon.listener;

import com.takutou.pl_dungeon.method.JudgeAndSpawnMob;
import com.takutou.pl_dungeon.method.MobManager;
import com.takutou.pl_dungeon.mob.DungeonZombie;
import com.takutou.pl_dungeon.Pl_dungeon;
import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
                //ここをワイルドカードにする必要がある。現時点だとエッグ:1のものしか判定されていない
                JudgeAndSpawnMob zombieSpawn = new JudgeAndSpawnMob(mobManager,1,e,plugin);
                zombieSpawn.judgeAndSpawn();
                //customIDでアイテムを判定
                if(e.getItem() != null){
                    ItemStack handItem = e.getItem();
                    String customID = getCustomID(handItem);
                    if(customID != null && customID.equals("123")){
                        Player player = e.getPlayer();
                        e.getPlayer().sendMessage("123!!!!");
                        //標準のスポーンエッグの機能を無効化
                        e.setCancelled(true);
                        //右クリック先のブロックにスポーン位置を設定
                        Location spawnLocation;
                        if(e.getClickedBlock().isPassable()){
                            spawnLocation = e.getClickedBlock().getLocation().add(0.5,0,0.5);
                        }else{
                            spawnLocation = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().add(0.5,0,0.5);
                        }
                        //リストの1番目のモンスターをスポーン
                        EntityObject spawnedMob = mobManager.getAllDungeonMobs().get(1);
                        spawnedMob.spawn(spawnLocation);
                        player.sendMessage(mobManager.getAllDungeonMobs().toString());
                    }
                }

            }
        }
    }
    public String getCustomID(ItemStack item){
        if(item.hasItemMeta()){
            ItemMeta meta = item.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin,"custom_id");
            return meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        }
        return null;
    }
}
