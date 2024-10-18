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

public class CocaineListener implements Listener {
    private final Configuration config;
    private final Drugs plugin;
    private File cocaineplantsFile;
    private FileConfiguration cocaineplantsConfig;

    public CocaineListener(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        initCocainePlantsFile();
        loadCocainePlants();
    }

    private void initCocainePlantsFile() {
        cocaineplantsFile = new File(plugin.getDataFolder(), "cocaineplants.yml");
        if (!cocaineplantsFile.exists()) {
            try {
                cocaineplantsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cocaineplantsConfig = YamlConfiguration.loadConfiguration(cocaineplantsFile);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item != null && item.getType() == Material.WHEAT_SEEDS && item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(plugin.getMessageHandler().getMessage("cocaine_seed_name").orElse("§eCocaine Seed"))) {
                Block block = event.getBlock();
                block.setMetadata("cocaineplant", new FixedMetadataValue(this.plugin, this));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.WHEAT && block.hasMetadata("cocaineplant")) {
            boolean isFullyGrown = this.isFullyGrown(block);
            event.setCancelled(true);
            block.setType(Material.AIR);
            if (isFullyGrown) {
                block.getWorld().dropItemNaturally(block.getLocation(), CreatedItems.getCocaineItem(plugin));
                block.getWorld().dropItemNaturally(block.getLocation(), this.getCocaineSeed(2));
            } else {
                block.getWorld().dropItemNaturally(block.getLocation(), this.getCocaineSeed(1));
            }

            block.removeMetadata("cocaineplant", this.plugin);
            removeCocainePlant(block);
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

    private ItemStack getCocaineSeed(int amount) {
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
    public void onPluginDisable(PluginDisableEvent event, Block block){
        if (event.getPlugin().equals(Bukkit.getPluginManager().getPlugin(plugin.getName()))){
            String key = block.getWorld().getUID() + ":" + block.getX() + "," + block.getY() + "," + block.getZ();
            BlockData blockData = block.getBlockData();
            int age = ((Ageable) blockData).getAge();
            Bukkit.getPlayer("SVW_Lars").sendMessage(age + "stage");
            cocaineplantsConfig.set("cocaine_plants." + key + ".age", age);
            cocaineplantsConfig.set("cocaine_plants." + key + ".type", block.getType().toString());
            saveCocainePlantsFile();
        }
    }

    private void removeCocainePlant(Block block) {
        String key = block.getWorld().getUID() + ":" + block.getX() + "," + block.getY() + "," + block.getZ();
        cocaineplantsConfig.set("cocaine_plants." + key, null);
        saveCocainePlantsFile();
    }

    private void loadCocainePlants() {
        if (!cocaineplantsConfig.contains("cocaine_plants")) return;
        Set<String> keys = cocaineplantsConfig.getConfigurationSection("cocaine_plants").getKeys(false);
        for (String key : keys) {
            String[] parts = key.split(":");
            UUID worldUUID = UUID.fromString(parts[0]);
            String[] coords = parts[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);

            Block block = plugin.getServer().getWorld(worldUUID).getBlockAt(x, y, z);

            // Set the correct block type in case it was reset to something else
            block.setType(Material.valueOf(cocaineplantsConfig.getString("cocaine_plants." + key + ".type")));

            // Restore the metadata and age
            block.setMetadata("cocaineplant", new FixedMetadataValue(this.plugin, this));
            int age = cocaineplantsConfig.getInt("cocaine_plants." + key + ".age");
            BlockData blockData = block.getBlockData();
            Ageable ageable = (Ageable) blockData;
            ageable.setAge(age);

            block.setBlockData(blockData);
        }
    }

    private void saveCocainePlantsFile() {
        try {
            cocaineplantsConfig.save(cocaineplantsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
