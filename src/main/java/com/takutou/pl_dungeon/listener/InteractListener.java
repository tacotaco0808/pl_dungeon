package com.takutou.pl_dungeon.listener;

import com.takutou.pl_dungeon.method.CreatedMobManager;
import com.takutou.pl_dungeon.method.JudgeAndSpawnMob;
import com.takutou.pl_dungeon.method.MobManager;
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

import java.util.Map;

public class InteractListener implements Listener {
    private final Pl_dungeon plugin;
    private CreatedMobManager createdMobManager;
    public InteractListener(CreatedMobManager createdMobManager, Pl_dungeon plugin){
        this.createdMobManager = createdMobManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction()== Action.RIGHT_CLICK_BLOCK){
            if(e.getHand() != null && e.getHand() == EquipmentSlot.HAND){
                //手に持っているアイテムを判定
                //ここをワイルドカードにする必要がある。現時点だとエッグ:1のものしか判定されていない
//                JudgeAndSpawnMob zombieSpawn = new JudgeAndSpawnMob(mobManager,1,e,plugin);
//                zombieSpawn.judgeAndSpawn();
                //customIDでアイテムを判定
                if(e.getItem() != null){
                    ItemStack handItem = e.getItem();
                    String customID = getCustomID(handItem);
                    if(customID != null ){
                        Player player = e.getPlayer();
                        //標準のスポーンエッグの機能を無効化
                        e.setCancelled(true);
                        //右クリック先のブロックにスポーン位置を設定
                        Location spawnLocation;
                        if(e.getClickedBlock().isPassable()){
                            spawnLocation = e.getClickedBlock().getLocation().add(0.5,0,0.5);
                        }else{
                            spawnLocation = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().add(0.5,0,0.5);
                        }
                        //リストにいるモンスターIDかチェック
                        int monsterIndex = Integer.parseInt(customID);
                        if(0 <= monsterIndex && monsterIndex < createdMobManager.getAllDungeonMobs().size()){
                            //リストのn番目のモンスターをスポーン
                            EntityObject spawnedMob = createdMobManager.getAllDungeonMobs().get(monsterIndex);
                            spawnedMob.spawn(spawnLocation);
                            player.sendMessage(createdMobManager.getAllDungeonMobs().toString());
                            /*スポーンメッセージを表示*/
                            Map<String,Object> spawnedMobData = spawnedMob.getMonsterData();
                            String mobName = (String) spawnedMobData.get("mobName");
                            String mobType = spawnedMob.getClass().getSimpleName().toLowerCase();
                            int mobSpeed = (int) spawnedMobData.get("speed");
                            int mobMaxHealth = (int) spawnedMobData.get("maxHealth");
                            int mobAttackDamage = (int) spawnedMobData.get("attackDamage");


                            e.getPlayer().sendMessage(
                            ChatColor.GREEN + "customID-" + customID + "のモンスターをスポーンしました\n" +
                                ChatColor.GREEN + "----------\n" +
                                ChatColor.GREEN + "MOBタイプ:" + mobType + "\n" +
                                ChatColor.GREEN + "名前　　:" + mobName + "\n" +
                                ChatColor.GREEN + "スピード:" + mobSpeed + "\n" +
                                ChatColor.GREEN + "体力　　:" + mobMaxHealth + "\n" +
                                ChatColor.GREEN + "攻撃力　:" + mobAttackDamage + "\n" +
                                ChatColor.GREEN + "----------"
                            );

                        }else{//エラー
                            player.sendMessage(ChatColor.RED + "Error:CustomID-" +monsterIndex+"のモンスターはリストに存在しません");

                        }

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
