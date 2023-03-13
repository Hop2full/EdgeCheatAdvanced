package com.example.edgecheatadvanced;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class EdgeCheatAdvanced extends JavaPlugin implements Listener {

    private Map<Player, Long> flyCooldown = new HashMap<>();
    private Map<Player, Long> knockbackCooldown = new HashMap<>();
    private Map<Player, Long> speedCooldown = new HashMap<>();
    private Map<Player, Long> scaffoldCooldown = new HashMap<>();
    private Map<Player, Long> killauraCooldown = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isFlying() && !player.hasPermission("edgecheatadvanced.fly.bypass")) {
            if (flyCooldown.containsKey(player)) {
                if (System.currentTimeMillis() - flyCooldown.get(player) < 1000) {
                    event.setTo(player.getLocation());
                    return;
                }
            }
            flyCooldown.put(player, System.currentTimeMillis());
            player.kickPlayer("Flying is not allowed!");
        }
        if (player.isSprinting() && !player.hasPermission("edgecheatadvanced.speed.bypass")) {
            if (speedCooldown.containsKey(player)) {
                if (System.currentTimeMillis() - speedCooldown.get(player) < 1000) {
                    player.setSprinting(false);
                    return;
                }
            }
            speedCooldown.put(player, System.currentTimeMillis());
            player.kickPlayer("Speed is not allowed!");
        }
        if (player.isOnGround() && !player.hasPermission("edgecheatadvanced.scaffold.bypass")) {
            if (scaffoldCooldown.containsKey(player)) {
                if (System.currentTimeMillis() - scaffoldCooldown.get(player) < 1000) {
                    event.setTo(player.getLocation());
                    return;
                }
            }
            scaffoldCooldown.put(player, System.currentTimeMillis());
            player.kickPlayer("Scaffold is not allowed!");
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && !event.getDamager().hasPermission("edgecheatadvanced.killaura.bypass")) {
            Player player = (Player) event.getDamager();
            if (killauraCooldown.containsKey(player)) {
                if (System.currentTimeMillis() - killauraCooldown.get(player) < 1000) {
                    event.setCancelled(true);
                    return;
                }
            }
            killauraCooldown.put(player, System.currentTimeMillis());
            player.kickPlayer("KillAura is not allowed!");
        }
    }

    @EventHandler
    public void onPlayerDamage(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getVelocity().getY() > 0 && !player.hasPermission("edgecheatadvanced.knockback.bypass")) {
            if (knockbackCooldown.containsKey(player)) {
                if (System.currentTimeMillis() - knockbackCooldown.get(player) < 1000) {
                    player.setVelocity(player.getVelocity().setY(player.getVelocity().getY() * -1));
                    return;
                }
            }
            knockbackCooldown.put(player, System.currentTimeMillis());
            player.kickPlayer("Anti-Knockback is not allowed!");
        }
    }

}
