package de.lars.drugs.listener.DrugEffectListener;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

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

public class ShroomsEffectListener implements Listener {
    private final Configuration config;
    private final double shroomkiller;
    private final Drugs plugin;
    private final Set<UUID> shroomUsers;
    public ShroomsEffectListener(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        this.shroomkiller = config.getDouble("shroom_kill");
        this.shroomUsers = new HashSet<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item != null && item.getType() == Material.RED_MUSHROOM && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("shroom_name").orElse("§eShrooms")) && event.getAction().toString().contains("RIGHT_CLICK")) {
            int amount = item.getAmount();
            if (amount > 1) {
                item.setAmount(amount - 1);
            } else {
                player.setItemInHand(null);
            }

            this.applyEffects(player);
            shroomUsers.add(player.getUniqueId());
            player.sendMessage(plugin.getMessageHandler().getMessage("druginfo").orElse("You took %drug%").replace("%drug%", plugin.getMessageHandler().getMessage("shrooms_name").orElse("§eShrooms")));

            new BukkitRunnable() {
                @Override
                public void run() {
                    shroomUsers.remove(player.getUniqueId());
                    player.sendMessage(plugin.getMessageHandler().getMessage("druginfo_end").orElse("%drug% is no longer detectable").replace("%drug%", plugin.getMessageHandler().getMessage("shrooms_name").orElse("§eShrooms")));
                }
            }.runTaskLater(plugin, config.getInt("Shrooms_value_detectable") * 20);  // Zeit in Sekunden umrechnen zu Ticks
            Random random = new Random();
            if (random.nextDouble() < this.shroomkiller) {
                player.damage(2000.0D);
                Bukkit.getServer().broadcastMessage(plugin.getMessageHandler().getMessage("shroom_death").orElse("%prefix% &e%player% died because of an shrooms overdose.").replace("%player%", player.getName()).replace("%prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§e§lDrugs§0§l] ")));            }
        }

    }

    private void applyEffects(Player player) {
        int time = this.config.getInt("shrooms_duration", 30);
        int duration = time * 20;
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("shrooms_effect_1")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("shrooms_effect_2")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("shrooms_effect_3")), duration, 1));
        SleepBlockListener sleepListener = new SleepBlockListener(plugin);
        sleepListener.onDrugUse(player);
    }
    public boolean hasPlayerTakenShrooms(Player player) {
        return shroomUsers.contains(player.getUniqueId());
    }}