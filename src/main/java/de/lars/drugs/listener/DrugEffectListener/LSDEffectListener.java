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

public class LSDEffectListener implements Listener {
    private final Configuration config;
    private final double lsdkiller;
    private final Drugs plugin;
    private final Set<UUID> lsdUsers;

    public LSDEffectListener(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        this.lsdkiller = config.getDouble("lsd_kill");
        this.lsdUsers = new HashSet<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item != null && item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("lsd_name").orElse("§eLSD"))) {
            if (event.getAction().toString().contains("RIGHT_CLICK")) {
                int amount = item.getAmount();
                if (amount > 1) {
                    item.setAmount(amount - 1);
                } else {
                    player.setItemInHand(null);
                }

                applyEffects(player);

                lsdUsers.add(player.getUniqueId());
                player.sendMessage(plugin.getMessageHandler().getMessage("druginfo").orElse("You took %drug%").replace("%drug%", plugin.getMessageHandler().getMessage("lsd_name").orElse("§eLSD")));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        lsdUsers.remove(player.getUniqueId());
                        player.sendMessage(plugin.getMessageHandler().getMessage("druginfo_end").orElse("%drug% is no longer detectable").replace("%drug%", plugin.getMessageHandler().getMessage("lsd_name").orElse("§eLSD")));
                    }
                }.runTaskLater(plugin, config.getInt("LSD_value_detectable") * 20);  // Zeit in Sekunden umrechnen zu Ticks

                Random random = new Random();
                if (random.nextDouble() < lsdkiller) {
                    player.damage(2000);
                    Bukkit.getServer().broadcastMessage(plugin.getMessageHandler().getMessage("lsd_death").orElse("%prefix% &e%player% died because of an lsd overdose.").replace("%player%", player.getName()).replace("%prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§e§lDrugs§0§l] ")));
                }
            }
        }
    }

    private void applyEffects(Player player) {
        int time = config.getInt("lsd_duration", 30);
        int duration = time * 20;
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(config.getString("lsd_effect_1")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(config.getString("lsd_effect_2")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(config.getString("lsd_effect_3")), duration, 1));
        SleepBlockListener sleepListener = new SleepBlockListener(plugin);
        sleepListener.onDrugUse(player);
    }

    public boolean hasPlayerTakenLSD(Player player) {
       return lsdUsers.contains(player.getUniqueId());
    }
}
