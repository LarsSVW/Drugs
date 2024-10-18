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

public class WeedEffectListener implements Listener {
    private final Drugs plugin;
    private final Configuration config;
    private final double weedkiller;
    private final Set<UUID> weedUsers;

    public WeedEffectListener(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        this.weedkiller = config.getDouble("weed_kill");
        this.weedUsers = new HashSet<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getHand() == EquipmentSlot.HAND) {
            ItemStack item = player.getInventory().getItemInOffHand();
            ItemStack joint;
            if (item != null && item.getType() == Material.FLINT_AND_STEEL && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("zippo_name").orElse("§eZippo"))){
                joint = player.getInventory().getItemInMainHand();
                if (joint != null && joint.getType() == Material.STICK && joint.hasItemMeta() && joint.getItemMeta().hasDisplayName() && joint.getItemMeta().getDisplayName().equals(plugin.getMessageHandler().getMessage("joint_name").orElse("§eJoint")) && event.getAction().toString().contains("RIGHT_CLICK")) {
                    int amount = joint.getAmount();
                    if (amount > 1) {
                        joint.setAmount(amount - 1);
                    } else {
                        player.getInventory().setItemInMainHand(null);
                    }

                    this.applyEffects(player);
                    event.setCancelled(true);
                    weedUsers.add(player.getUniqueId());
                    player.sendMessage(plugin.getMessageHandler().getMessage("druginfo").orElse("You took %drug%").replace("%drug%", plugin.getMessageHandler().getMessage("joint_name").orElse("§eJoint")));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            weedUsers.remove(player.getUniqueId());
                            player.sendMessage(plugin.getMessageHandler().getMessage("druginfo_end").orElse("%drug% is no longer detectable").replace("%drug%", plugin.getMessageHandler().getMessage("joint_name").orElse("§eJoint")));
                        }
                    }.runTaskLater(plugin, config.getInt("Joint_value_detectable") * 20);  // Zeit in Sekunden umrechnen zu Ticks
                    Random random = new Random();
                    if (random.nextDouble() < this.weedkiller) {
                        player.damage(2000.0D);
                        Bukkit.getServer().broadcastMessage(plugin.getMessageHandler().getMessage("weed_death").orElse("%prefix% %player% died because of an cocaine overdose.").replace("%player%", player.getName()).replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
                    }

                    return;
                }
            }

            joint = player.getInventory().getItemInMainHand();
            if (joint != null && joint.getType() == Material.STICK && joint.hasItemMeta() && joint.getItemMeta().hasDisplayName() && joint.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', this.config.getString("joint_name", "§eJoint")))) {
                player.sendMessage(plugin.getMessageHandler().getMessage("need_zippo").orElse("%prefix% §eYou need a zippo to light this up.").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            }

        }
    }

    private void applyEffects(Player player) {
        int time = this.config.getInt("joint_duration", 30);
        int duration = time * 20;
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("joint_effect_1")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("joint_effect_2")), duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(this.config.getString("joint_effect_3")), duration, 1));


    }
    public boolean hasPlayerTakenWeed(Player player) {
        return weedUsers.contains(player.getUniqueId());
    }}