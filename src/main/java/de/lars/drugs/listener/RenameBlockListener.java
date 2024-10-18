package de.lars.drugs.listener;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameBlockListener implements Listener {
    private final Configuration config;
    private final Drugs plugin;
    public RenameBlockListener(Drugs plugin, Configuration config){
        this.config = config;
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                String newName = meta.getDisplayName();
                if (newName != null && (newName.equalsIgnoreCase(this.config.getString("language")) ||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("weed_seed_name").orElse("§eWeed Seed"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("weed_name").orElse("§eWeed")) ||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("cocaine_name").orElse("§eCocaine"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("cocaine_seed_name").orElse("§eCocaine Seed"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("tobacco_seed_name").orElse("§eTobacco Seed"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("tobacco_name").orElse("§eTobacco"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("cigarette_name").orElse("§eCigarette"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("papes_name").orElse("§ePapes"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("long_papes_name").orElse("§eLong Papes"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("glue_bottle_name").orElse("§eGlue Bottle"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("zippo_name").orElse("§eZippo"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("shroom_name").orElse("§eShroom"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("lsd_name").orElse("§eLSD"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("lysergic_acid_name").orElse("§eLysergic Acid"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("sodium_name").orElse("§eSodium"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("propanolamine_name").orElse("§ePropanolamine"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("hydrogen_name").orElse("§eHydrogen"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("xtc_name").orElse("§eEcstasy"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("isosafrole_name").orElse("§eIsosafrole"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("safrole_name").orElse("§eSafrole"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("piperonal_name").orElse("§ePiperonal"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("speed_name").orElse("§eSpeed"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("phenyl_name").orElse("§ePhenyl")) ||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("syringe_name").orElse("§eSyringe")) ||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("heroin_name").orElse("§eHeroin"))||
                        newName.equalsIgnoreCase(plugin.getMessageHandler().getMessage("heroin_liquid_name").orElse("§eHeroin Liquid")))) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You cannot fake your drugs!");
                }
            }
        }
    }
}