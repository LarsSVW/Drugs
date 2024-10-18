package de.lars.drugs.listener;

import de.lars.drugs.Drugs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class SleepBlockListener implements Listener {

    private final Drugs plugin;
    private final HashMap<UUID, Long> drugTimers = new HashMap<>();
    private final long sleepCooldown = 10 * 60 * 1000;

    public SleepBlockListener(Drugs plugin) {
        this.plugin = plugin;
    }

    public void onDrugUse(Player player) {
        drugTimers.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();


        if (drugTimers.containsKey(playerId)) {
            long timeSinceDrugUse = System.currentTimeMillis() - drugTimers.get(playerId);

            if (timeSinceDrugUse < sleepCooldown) {
                long remainingTime = sleepCooldown - timeSinceDrugUse;
                long minutesLeft = remainingTime / 1000 / 60;

                player.sendMessage(ChatColor.RED + "Du kannst erst wieder in " + minutesLeft + " Minuten schlafen.");
                event.setCancelled(true);
            }
        }
    }
}
