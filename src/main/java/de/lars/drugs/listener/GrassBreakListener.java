package de.lars.drugs.listener;


import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassBreakListener implements Listener {

    private final double dropChanceCocaine;
    private final double dropChanceTobacco;
    private final double dropChanceWeed;
    private final Drugs plugin;

    public GrassBreakListener(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.dropChanceCocaine = config.getDouble("cocaine_chance");
        this.dropChanceTobacco = config.getDouble("tobacco_chance");
        this.dropChanceWeed = config.getDouble("weed_chance");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGrassBreak(BlockBreakEvent event) {
        if (event.isCancelled()){
            return;
        }


        if (event.getBlock().getType() == Material.GRASS) {
            Random random = new Random();
            if (random.nextDouble() < this.dropChanceWeed) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getSuperSeedItem(1));
            }

            if (random.nextDouble() < this.dropChanceCocaine) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getCocainePlantItem(1));
            }

            if (random.nextDouble() < this.dropChanceTobacco) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getTobaccoPlant(1));
            }
        }

        else{
            if (event.getBlock().getType() == Material.GRASS) {
                Random random = new Random();
                if (random.nextDouble() < this.dropChanceWeed) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getSuperSeedItem(1));
                }

                if (random.nextDouble() < this.dropChanceCocaine) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getCocainePlantItem(1));
                }

                if (random.nextDouble() < this.dropChanceTobacco) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), getTobaccoPlant(1));
                }
            }
        }
        }

    private ItemStack getSuperSeedItem(int amount) {
        ItemStack itemStack = new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("weed_seed_name").orElse("§eWeed Seed"));
        List<String> lore = new ArrayList<>();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("weed_seed_lore").orElse("§eYou need it to create weed."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(451);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getCocainePlantItem(int amount) {
        ItemStack itemStack = new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("cocaine_seed_name").orElse("§eCocaine Plant."));
        List<String> lore = new ArrayList<>();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("cocaine_seed_lore").orElse("§eYou need it to create a piece of cocain.."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(452);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getTobaccoPlant(int amount) {
        ItemStack itemStack = new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("tobacco_seed_name").orElse("§eTobacco Plant."));
        List<String> lore = new ArrayList<>();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("tobacco_seed_lore").orElse("§eYou need it to create tobacco."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(453);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
