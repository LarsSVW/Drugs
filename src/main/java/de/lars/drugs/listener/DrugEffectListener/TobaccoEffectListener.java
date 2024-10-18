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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TobaccoEffectListener implements Listener {
    private final double cigarettekill;
    private final Configuration config;
    private final Drugs plugin;
    private final Set<UUID> tobaccoUsers;

    public TobaccoEffectListener(Drugs plugin, Configuration config) {
        this.cigarettekill = config.getDouble("cigarette_kill");
        this.config = config;
        this.plugin = plugin;
        this.tobaccoUsers = new HashSet<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getHand() == EquipmentSlot.HAND) {
            ItemStack item = player.getInventory().getItemInOffHand();
            ItemStack joint;
            if (item != null && item.getType() == Material.FLINT_AND_STEEL && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("zippo_name").orElse("§eZippo"))){
                joint = player.getInventory().getItemInMainHand();
                if (joint != null && joint.getType() == Material.STICK && joint.hasItemMeta() && joint.getItemMeta().hasDisplayName() && joint.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("cigarette_name").orElse("§eCigarette")) && event.getAction().toString().contains("RIGHT_CLICK")) {
                    int amount = joint.getAmount();
                    if (amount > 1) {
                        joint.setAmount(amount - 1);
                    } else {
                        player.getInventory().setItemInMainHand(null);
                    }

                    this.applyEffects(player);
                    event.setCancelled(true);

                    tobaccoUsers.add(player.getUniqueId());
                    player.sendMessage(plugin.getMessageHandler().getMessage("druginfo").orElse("You took %drug%").replace("%drug%", plugin.getMessageHandler().getMessage("cigarette_name").orElse("§eCigarette")));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            tobaccoUsers.remove(player.getUniqueId());
                            player.sendMessage(plugin.getMessageHandler().getMessage("druginfo_end").orElse("%drug% is no longer detectable").replace("%drug%", plugin.getMessageHandler().getMessage("cigarette_name").orElse("§eCigarette")));
                        }
                    }.runTaskLater(plugin, config.getInt("Cigarette_value_detectable") * 20);  // Zeit in Sekunden umrechnen zu Ticks

                    Random random = new Random();
                    if (random.nextDouble() < this.cigarettekill) {
                        player.damage(2000.0D);
                        Bukkit.getServer().broadcastMessage(plugin.getMessageHandler().getMessage("lung_cancer").orElse("%prefix% %player% died because of lung cancer").replace("%player%", player.getName()).replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
                    }

                    return;
                }
            }

            joint = player.getInventory().getItemInMainHand();
            if (joint != null && joint.getType() == Material.STICK && joint.hasItemMeta() && joint.getItemMeta().hasDisplayName() && joint.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', this.config.getString("cigarette_name", "§eCigarette")))) {
                player.sendMessage(plugin.getMessageHandler().getMessage("need_zippo").orElse("%preifx% §eYou need a zippo to light this up.").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            }

        }
    }

    private void applyEffects(Player player) {
        int time = this.config.getInt("tobacco_time", 30);
        int duration = time * 20;
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("tobacco_effect_1")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("tobacco_effect_2")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("tobacco_effect_3")), duration, 1));
        SleepBlockListener sleepListener = new SleepBlockListener(plugin);
        sleepListener.onDrugUse(player);
    }
    public boolean hasPlayerTakenTobacco(Player player) {
        return tobaccoUsers.contains(player.getUniqueId());

    }
}