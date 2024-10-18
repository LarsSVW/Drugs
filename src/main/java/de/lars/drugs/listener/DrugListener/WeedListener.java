package de.lars.drugs.listener.DrugListener;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WeedListener implements Listener {
    private final Configuration config;
    private final Drugs plugin;
    private File weedplantsFile;
    private FileConfiguration weedplantsConfig;

    public WeedListener(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        initWeedPlantsFile();
        loadWeedPlants();
    }

    private void initWeedPlantsFile() {
        weedplantsFile = new File(plugin.getDataFolder(), "weedplants.yml");
        if (!weedplantsFile.exists()) {
            try {
                weedplantsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        weedplantsConfig = YamlConfiguration.loadConfiguration(weedplantsFile);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item != null && item.getType() == Material.WHEAT_SEEDS && item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(plugin.getMessageHandler().getMessage("weed_seed_name").orElse("§eWeed Seed"))) {
                Block block = event.getBlock();
                block.setMetadata("weedplant", new FixedMetadataValue(this.plugin, this));

                saveWeedPlant(block);
            }
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.WHEAT && block.hasMetadata("weedplant")) {
            boolean isFullyGrown = this.isFullyGrown(block);
            event.setCancelled(true);
            block.setType(Material.AIR);
            if (isFullyGrown) {
                block.getWorld().dropItemNaturally(block.getLocation(), CreatedItems.getSuperWheatItem(plugin));
                block.getWorld().dropItemNaturally(block.getLocation(), this.getWeedSeed(2));
            } else {
                block.getWorld().dropItemNaturally(block.getLocation(), this.getWeedSeed(1));
            }

            block.removeMetadata("weedplant", this.plugin);
            removeWeedPlant(block);
        }
    }

    private boolean isFullyGrown(Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Ageable) {
            Ageable ageable = (Ageable) blockData;
            return ageable.getAge() == ageable.getMaximumAge();
        } else {
            return false;
        }
    }


    private ItemStack getWeedSeed(int amount) {
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



    private void removeWeedPlant(Block block) {
        String key = block.getWorld().getUID() + ":" + block.getX() + "," + block.getY() + "," + block.getZ();
        weedplantsConfig.set("weed_plants." + key, null);
        saveWeedPlantsFile();
    }

    private void loadWeedPlants() {
        ConfigurationSection weedSection = weedplantsConfig.getConfigurationSection("weed_plants");
        if (weedSection == null) {
            return; // Es gibt keine gespeicherten Weed-Pflanzen, daher nichts zu laden
        }

        Set<String> keys = weedSection.getKeys(false);
        for (String key : keys) {
            String[] parts = key.split(":");
            UUID worldUUID = UUID.fromString(parts[0]);
            String[] coords = parts[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);

            Block block = plugin.getServer().getWorld(worldUUID).getBlockAt(x, y, z);

            // Set the correct block type in case it was reset to something else
            block.setType(Material.WHEAT); // Stelle sicher, dass der Block auf Weizen gesetzt wird

            // Restore the metadata and age
            block.setMetadata("weedplant", new FixedMetadataValue(this.plugin, this));
            int age = weedplantsConfig.getInt("weed_plants." + key + ".age");
            BlockData blockData = block.getBlockData();
            if (blockData instanceof Ageable) {
                Ageable ageable = (Ageable) blockData;
                ageable.setAge(age);

                block.setBlockData(ageable); // Korrektes Setzen der Blockdaten mit dem Alterswert
            }
        }
    }

    private void saveWeedPlant(Block block) {
        String key = block.getWorld().getUID() + ":" + block.getX() + "," + block.getY() + "," + block.getZ();
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Ageable) {
            int age = ((Ageable) blockData).getAge();
            weedplantsConfig.set("weed_plants." + key + ".age", age);
            weedplantsConfig.set("weed_plants." + key + ".type", block.getType().toString());
            saveWeedPlantsFile();
        }
    }



    private void saveWeedPlantsFile() {
        try {
            weedplantsConfig.save(weedplantsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
