package de.lars.drugs.listener;

import de.lars.drugs.Drugs;
import de.lars.drugs.listener.DrugEffectListener.LSDEffectListener;
import de.lars.drugs.listener.DrugEffectListener.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DrugTestListener implements Listener {
    private final Drugs plugin;
    private final LSDEffectListener lsdEffectListener;
    private final WeedEffectListener weedEffectListener;
    private final CocaineEffectListener cocaineEffectListener;
    private final ShroomsEffectListener shroomsEffectListener;
    private final HeroinEffectListener heroinEffectListener;
    private final EcstasyEffectListener ecstasyEffectListener;
    private final SpeedEffectListener speedEffectListener;
    private final TobaccoEffectListener tobaccoEffectListener;

    public DrugTestListener(Drugs plugin, LSDEffectListener lsdEffectListener, WeedEffectListener weedEffectListener,
                            CocaineEffectListener cocaineEffectListener, ShroomsEffectListener shroomsEffectListener,
                            HeroinEffectListener heroinEffectListener, EcstasyEffectListener ecstasyEffectListener,
                            SpeedEffectListener speedEffectListener, TobaccoEffectListener tobaccoEffectListener) {
        this.plugin = plugin;
        this.lsdEffectListener = lsdEffectListener;
        this.weedEffectListener = weedEffectListener;
        this.cocaineEffectListener = cocaineEffectListener;
        this.shroomsEffectListener = shroomsEffectListener;
        this.heroinEffectListener = heroinEffectListener;
        this.ecstasyEffectListener = ecstasyEffectListener;
        this.speedEffectListener = speedEffectListener;
        this.tobaccoEffectListener = tobaccoEffectListener;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item != null && item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Drugtest")) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8&lDrugtest by" + player.getDisplayName()));
            if (cocaineEffectListener.hasPlayerTakenCocaine(player)) {
                lore.add(ChatColor.RED + "Cocaine: positive");
            } else {
                lore.add(ChatColor.GREEN + "Cocaine: negative");
            }
            if (ecstasyEffectListener.hasPlayerTakenXTC(player)) {
                lore.add(ChatColor.RED + "Ecstasy: positive");
            } else {
                lore.add(ChatColor.GREEN + "Ecstasy: negative");
            }
            if (heroinEffectListener.hasPlayerTakenHeroin(player)) {
                lore.add(ChatColor.RED + "Heroine: positive");
            } else {
                lore.add(ChatColor.GREEN + "Heroine: negative");
            }
            if (lsdEffectListener.hasPlayerTakenLSD(player)) {
                lore.add(ChatColor.RED + "LSD: positive");
            } else {
                lore.add(ChatColor.GREEN + "LSD: negative");
            }
            if (shroomsEffectListener.hasPlayerTakenShrooms(player)) {
                lore.add(ChatColor.RED + "Shrooms: positive");
            } else {
                lore.add(ChatColor.GREEN + "Shrooms: negative");
            }
            if (speedEffectListener.hasPlayerTakenSpeed(player)) {
                lore.add(ChatColor.RED + "Speed: positive");
            } else {
                lore.add(ChatColor.GREEN + "Speed: negative");
            }
            if (tobaccoEffectListener.hasPlayerTakenTobacco(player)) {
                lore.add(ChatColor.RED + "Tobacco: positive");
            } else {
                lore.add(ChatColor.GREEN + "Tobacco: negative");
            }
            if (weedEffectListener.hasPlayerTakenWeed(player)) {
                lore.add(ChatColor.RED + "Weed: positive");
            } else {
                lore.add(ChatColor.GREEN + "Weed: negative");
            }








            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            meta.setCustomModelData(467);
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GREEN + "Your Drugtest is ready you can look at your results.");
            }
        }
    }
