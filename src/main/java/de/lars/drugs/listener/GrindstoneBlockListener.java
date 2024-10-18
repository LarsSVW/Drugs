package de.lars.drugs.listener;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GrindstoneBlockListener implements Listener {
    private final Configuration config;
    private final Drugs plugin;

    public GrindstoneBlockListener(Drugs plugin, Configuration config){
        this.config = config;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof GrindstoneInventory)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                String itemName = meta.getDisplayName();
                if (itemName != null && isBlockedItemName(itemName)) {
                    event.setCancelled(true);
                    Player player = (Player) event.getWhoClicked();
                    player.sendMessage(ChatColor.RED + "You cannot use the grindstone on this item!");
                }
            }
        }
    }

    private boolean isBlockedItemName(String itemName) {
        return itemName.equalsIgnoreCase(this.config.getString("language")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("weed_seed_name").orElse("§eWeed Seed")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("weed_name").orElse("§eWeed")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("cocaine_name").orElse("§eCocaine")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("cocaine_seed_name").orElse("§eCocaine Seed")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("tobacco_seed_name").orElse("§eTobacco Seed")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("tobacco_name").orElse("§eTobacco")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("cigarette_name").orElse("§eCigarette")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("papes_name").orElse("§ePapes")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("long_papes_name").orElse("§eLong Papes")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("glue_bottle_name").orElse("§eGlue Bottle")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("zippo_name").orElse("§eZippo")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("shroom_name").orElse("§eShroom")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("lsd_name").orElse("§eLSD")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("lysergic_acid_name").orElse("§eLysergic Acid")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("sodium_name").orElse("§eSodium")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("propanolamine_name").orElse("§ePropanolamine")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("hydrogen_name").orElse("§eHydrogen")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("xtc_name").orElse("§eEcstasy")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("isosafrole_name").orElse("§eIsosafrole")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("safrole_name").orElse("§eSafrole")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("piperonal_name").orElse("§ePiperonal")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("speed_name").orElse("§eSpeed")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("phenyl_name").orElse("§ePhenyl")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("syringe_name").orElse("§eSyringe")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("heroin_name").orElse("§eHeroin")) ||
                itemName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("heroin_liquid_name").orElse("§eHeroin Liquid"));
    }
}
