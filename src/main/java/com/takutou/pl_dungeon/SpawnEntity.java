package com.takutou.pl_dungeon;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpawnEntity implements CommandExecutor, Listener {
    private final Map<UUID, Zombie> zombieMap = new HashMap<>();
    private final Map<UUID, Location> deathLocations = new HashMap<>();
    private final Map<UUID, Location> spawnLocations = new HashMap<>();
    private final String ZOMBIE_METADATA_KEY = "spawned_by_command";

    private final JavaPlugin plugin;
    public SpawnEntity(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            //プレイヤーの位置情報等取得
            Player player = (Player) sender;
            Location playerLoc = player.getLocation();
            //-----プレーンなゾンビをスポーン-----
            Zombie undeadZombie = (Zombie) player.getWorld().spawnEntity(playerLoc, EntityType.ZOMBIE);
            UUID undeadZombieID = undeadZombie.getUniqueId();
            //メタデータ付与(コマンドで出したゾンビと通常沸きのゾンビを区別させる)
            undeadZombie.setMetadata(ZOMBIE_METADATA_KEY, new FixedMetadataValue(plugin, true));
            // 装備を取り除く・アイテムを拾わないように設定
            undeadZombie.getEquipment().clear();
            undeadZombie.setCanPickupItems(false);
            // 大人のゾンビに設定
            undeadZombie.setAdult();
            // 村人ゾンビへの変換を防ぐ
            undeadZombie.setConversionTime(-1);


            //ゾンビのUUIDを保存
            zombieMap.put(undeadZombieID,undeadZombie);
            //spawn位置を保存
            spawnLocations.put(undeadZombieID,playerLoc);


            player.sendMessage("ゾンビをスポーンしました！ID " + undeadZombieID);
            // 定期的にゾンビが水に入っているかをチェックするタスクを開始
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (undeadZombie.isDead()) {
                        cancel();
                        return;
                    }
                    if (undeadZombie.getLocation().getBlock().getType() == Material.WATER || undeadZombie.getLocation().getBlock().getType() == Material.WATER_CAULDRON) {
                        undeadZombie.damage(undeadZombie.getHealth());
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L); // 1秒ごとにチェック
            return true;
        } else {
            sender.sendMessage("このコマンドはプレイヤーのみ実行可能です。");
            return false;
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        //ゾンビが死んだ際
        if(event.getEntity() instanceof Zombie){
            Zombie zombie = (Zombie) event.getEntity();
            //コマンドによって生成されたゾンビかどうか
            if(zombie.hasMetadata(ZOMBIE_METADATA_KEY)){
                UUID zombieId = zombie.getUniqueId();
                // ドロップアイテムと経験値を削除
                event.getDrops().clear();
                event.setDroppedExp(0);
                Player killer = zombie.getKiller();
                if(killer != null){// ゾンビを倒したエンティティがプレイヤーかどうかを確認
                    killer.sendMessage("special zombie!!");
                }
                respawnZombie(zombieId);
            }
        }
    }

    //特定のゾンビをリスポーン
    public void respawnZombie(UUID zombieId){
        Location spawnLocation = spawnLocations.get(zombieId);
        if(spawnLocation != null){
            // -----プレーンなゾンビをリスポーン-----
            Zombie newZombie = (Zombie) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
            UUID newZombieId = newZombie.getUniqueId();
            // 新しいゾンビにメタデータを付与して識別
            newZombie.setMetadata(ZOMBIE_METADATA_KEY, new FixedMetadataValue(plugin, true));
            // 装備を取り除く・アイテムを拾わないように設定
            newZombie.getEquipment().clear();
            newZombie.setCanPickupItems(false);
            // 大人のゾンビに設定
            newZombie.setAdult();
            // 村人ゾンビへの変換を防ぐ
            newZombie.setConversionTime(-1);
            // ゾンビのUUIDを更新
            zombieMap.remove(zombieId);
            zombieMap.put(newZombieId,newZombie);
            //　スポーン位置の引継ぎ
            spawnLocations.remove(zombieId);
            spawnLocations.put(newZombieId,spawnLocation);

            // 定期的にゾンビが水に入っているかをチェックするタスクを開始
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (newZombie.isDead()) {
                        cancel();
                        return;
                    }
                    if (newZombie.getLocation().getBlock().getType() == Material.WATER || newZombie.getLocation().getBlock().getType() == Material.WATER_CAULDRON) {
                        newZombie.damage(newZombie.getHealth());
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L); // 1秒ごとにチェック
        }
    }
}
