package de.lars.drugs.listener;


import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShroomBreakListener implements Listener {
    private final Configuration config;
    private final Drugs plugin;
    private final double dropChanceshrooms; // 3% Chance


    public ShroomBreakListener(Drugs plugin, Configuration config){
        this.config = config;
        this.plugin = plugin;
        this.dropChanceshrooms = config.getDouble("shrooms_chance");
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGrassBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

            if (event.getBlock().getType() == Material.RED_MUSHROOM) {
                Random random = new Random();
                if (random.nextDouble() < dropChanceshrooms) {
                    ItemStack shroomItem = getShroomItem(plugin, 1);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), shroomItem);
                }


        } else {
            if (event.getBlock().getType() == Material.RED_MUSHROOM) {
                Random random = new Random();
                if (random.nextDouble() < dropChanceshrooms) {
                    ItemStack shroomItem = getShroomItem(plugin, 1);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), shroomItem);
                }
            }
        }
    }


    private ItemStack getShroomItem(Drugs plugin, int amount) {
        ItemStack itemStack = new ItemStack(Material.RED_MUSHROOM);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("shroom_name").orElse("§eShroom"));
        List<String> lore = new ArrayList<>();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("shroom_lore").orElse("§eYou can use it to get high."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(455);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
