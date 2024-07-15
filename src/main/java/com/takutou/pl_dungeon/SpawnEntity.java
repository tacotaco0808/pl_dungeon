package com.takutou.pl_dungeon;

import org.bukkit.Location;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpawnEntity implements CommandExecutor, Listener {
    private final Map<UUID, Zombie> zombieMap = new HashMap<>();
    private final Map<UUID, Location> deathLocations = new HashMap<>();
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
            //ゾンビをスポーン
            Zombie undeadZombie = (Zombie) player.getWorld().spawnEntity(playerLoc, EntityType.ZOMBIE);
            UUID undeadZombieID = undeadZombie.getUniqueId();
            //メタデータ付与(コマンドで出したゾンビと通常沸きのゾンビを区別させる)
            undeadZombie.setMetadata(ZOMBIE_METADATA_KEY, new FixedMetadataValue(plugin, true));
            //ゾンビのUUIDを保存
            zombieMap.put(undeadZombieID,undeadZombie);


            player.sendMessage("ゾンビをスポーンしました！ID " + undeadZombieID);
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
                // ゾンビを倒したエンティティがプレイヤーかどうかを確認
                Player killer = zombie.getKiller();
                if(killer != null){
                    killer.sendMessage("special zombie!!");
                }
            }
        }
    }
}
