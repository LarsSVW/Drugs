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

public class TobaccoListener implements Listener {
    private final Configuration config;
    private final Drugs plugin;
    private File tobaccoplantsFile;
    private FileConfiguration tobaccoplantsConfig;

    public TobaccoListener(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        initTobaccoPlantsFile();
        loadTobaccoPlants();
    }

    private void initTobaccoPlantsFile() {
        tobaccoplantsFile = new File(plugin.getDataFolder(), "tobaccoplants.yml");
        if (!tobaccoplantsFile.exists()) {
            try {
                tobaccoplantsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tobaccoplantsConfig = YamlConfiguration.loadConfiguration(tobaccoplantsFile);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item != null && item.getType() == Material.WHEAT_SEEDS && item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(plugin.getMessageHandler().getMessage("tobacco_seed_name").orElse("§eTobacco Seed"))) {
                Block block = event.getBlock();
                block.setMetadata("tobaccoplant", new FixedMetadataValue(this.plugin, this));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.WHEAT && block.hasMetadata("tobaccoplant")) {
            boolean isFullyGrown = this.isFullyGrown(block);
            event.setCancelled(true);
            block.setType(Material.AIR);
            if (isFullyGrown) {
                block.getWorld().dropItemNaturally(block.getLocation(), CreatedItems.getTobaccoItem(plugin));
                block.getWorld().dropItemNaturally(block.getLocation(), this.getTobaccoSeed(2));
            } else {
                block.getWorld().dropItemNaturally(block.getLocation(), this.getTobaccoSeed(1));
            }

            block.removeMetadata("tobaccoplant", this.plugin);
            removeTobaccoPlant(block);
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

    private ItemStack getTobaccoSeed(int amount) {
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
    public void onPluginDisable(PluginDisableEvent event, Block block){
        if (event.getPlugin().equals(Bukkit.getPluginManager().getPlugin(plugin.getName()))){
            String key = block.getWorld().getUID() + ":" + block.getX() + "," + block.getY() + "," + block.getZ();
            BlockData blockData = block.getBlockData();
            int age = ((Ageable) blockData).getAge();
            Bukkit.getPlayer("SVW_Lars").sendMessage(age + "stage");
            tobaccoplantsConfig.set("tobacco_plants." + key + ".age", age);
            tobaccoplantsConfig.set("tobacco_plants." + key + ".type", block.getType().toString());
            saveTobaccoPlantsFile();
        }
    }

    private void removeTobaccoPlant(Block block) {
        String key = block.getWorld().getUID() + ":" + block.getX() + "," + block.getY() + "," + block.getZ();
        tobaccoplantsConfig.set("tobacco_plants." + key, null);
        saveTobaccoPlantsFile();
    }

    private void loadTobaccoPlants() {
        if (!tobaccoplantsConfig.contains("tobacco_plants")) return;
        Set<String> keys = tobaccoplantsConfig.getConfigurationSection("tobacco_plants").getKeys(false);
        for (String key : keys) {
            String[] parts = key.split(":");
            UUID worldUUID = UUID.fromString(parts[0]);
            String[] coords = parts[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);

            Block block = plugin.getServer().getWorld(worldUUID).getBlockAt(x, y, z);

            // Set the correct block type in case it was reset to something else
            block.setType(Material.valueOf(tobaccoplantsConfig.getString("tobacco_plants." + key + ".type")));

            // Restore the metadata and age
            block.setMetadata("tobaccoplant", new FixedMetadataValue(this.plugin, this));
            int age = tobaccoplantsConfig.getInt("tobacco_plants." + key + ".age");
            BlockData blockData = block.getBlockData();
                Ageable ageable = (Ageable) blockData;
                ageable.setAge(age);

                block.setBlockData(blockData);
        }
    }

    private void saveTobaccoPlantsFile() {
        try {
            tobaccoplantsConfig.save(tobaccoplantsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
