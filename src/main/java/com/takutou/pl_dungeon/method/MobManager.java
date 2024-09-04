package com.takutou.pl_dungeon.method;

import com.takutou.pl_dungeon.mob.EntityObject;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MobManager {
    private Plugin plugin;
    private List<EntityObject> dungeonMobs = new ArrayList<>();

    public MobManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void spawnDungeonMob(MobFactory factory, String mobName, int speed, int maxHealth, int attackDamage, Location location) {
        EntityObject mob = factory.createMob(mobName, speed, maxHealth, attackDamage, plugin);
        mob.spawn(location);
        dungeonMobs.add(mob);
    }

    public EntityObject getDungeonMob(UUID mobID) {
        for (EntityObject mob : dungeonMobs) {
            if (mob.getMonsterID().equals(mobID)) {
                return mob;
            }
        }
        return null;
    }

    public List<EntityObject> getAllDungeonMobs() {
        return dungeonMobs;
    }

    public void removeDungeonMob(UUID mobID) {
        dungeonMobs.removeIf(mob -> mob.getMonsterID().equals(mobID));
    }
}