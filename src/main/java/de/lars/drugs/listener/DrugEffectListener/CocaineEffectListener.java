package de.lars.drugs.listener.DrugEffectListener;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.listener.DrugTestListener;
import de.lars.drugs.listener.SleepBlockListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class CocaineEffectListener implements Listener {

    private final Configuration config;
    private final Drugs plugin;
    private final double cocaine_kill;
    private final Set<UUID> cocaineUsers;

    public CocaineEffectListener(Drugs plugin, Configuration config){
        this.config = config;
        this.plugin = plugin;
        this.cocaine_kill = config.getDouble("cocaine_kill");
        this.cocaineUsers = new HashSet<>();
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item != null && item.getType() == Material.SUGAR && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("cocaine_name").orElse("§eCocaine"))) {
            if (event.getAction().toString().contains("RIGHT_CLICK")) {
                int amount = item.getAmount();
                if (amount > 1) {
                    item.setAmount(amount - 1);
                } else {
                    player.setItemInHand(null);
                }
                applyEffects(player);
                cocaineUsers.add(player.getUniqueId());
                player.sendMessage(plugin.getMessageHandler().getMessage("druginfo").orElse("You took %drug%").replace("%drug%", plugin.getMessageHandler().getMessage("cocaine_name").orElse("§eCocaine")));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        cocaineUsers.remove(player.getUniqueId());
                        player.sendMessage(plugin.getMessageHandler().getMessage("druginfo_end").orElse("%drug% is no longer detectable").replace("%drug%", plugin.getMessageHandler().getMessage("cocaine_name").orElse("§eCocaine")));
                    }
                }.runTaskLater(plugin, config.getInt("Cocaine_value_detectable") * 20);  // Zeit in Sekunden umrechnen zu Ticks


                Random random = new Random();
                if (random.nextDouble() < cocaine_kill) {
                    player.damage(2000);
                    Bukkit.getServer().broadcastMessage(plugin.getMessageHandler().getMessage("cocaine_death").orElse("%prefix% &e%player% died because of an cocaine overdose.").replace("%player%", player.getName()).replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§eDrugs§0§l]")));
                }
            }
        }
    }

    private void applyEffects(Player player) {
        int time = config.getInt("cocaine_duration", 30);
        int duration = time * 20;
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(config.getString("cocaine_effect_1")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(config.getString("cocaine_effect_2")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(config.getString("cocaine_effect_3")), duration, 1));
        SleepBlockListener sleepListener = new SleepBlockListener(plugin);
        sleepListener.onDrugUse(player);
    }
    public boolean hasPlayerTakenCocaine(Player player) {
        return cocaineUsers.contains(player.getUniqueId());
    }
}
