package de.lars.drugs.handler;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class CreatedItems {
    private final Configuration config;
    private final Drugs plugin;

    public CreatedItems(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }
    public static ItemStack createHydrogen(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("hydrogen_name").orElse("§eHydrogen"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("hydrogen_lore").orElse("§eYou need it to create LSD."));
        meta.setLore(lore);
        meta.setCustomModelData(440);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createLSD(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("lsd_name").orElse("§eLSD"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("lsd_lore").orElse("§eYou can use it to get high."));
        meta.setLore(lore);
        meta.setCustomModelData(441);
        item.setItemMeta(meta);

        return item;
    }


    public static ItemStack createLysergicAcid(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("lysergic_acid_name").orElse("§eLysergic Acid"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("lysergic_acid_lore").orElse("§eYou need it to create LSD"));
        meta.setLore(lore);
        meta.setCustomModelData(442);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createNatrium(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("sodium_name").orElse("§eSodium"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("sodium_lore").orElse("§eYou need it to create LSD."));
        meta.setLore(lore);
        meta.setCustomModelData(443);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createpropanolamine(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("propanolamine_name").orElse("§ePropanolamine"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("propanolamine_lore").orElse("§eYou need it to create LSD."));
        meta.setLore(lore);
        meta.setCustomModelData(444);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createcigarette(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("cigarette_lore").orElse("§eYou can use it to get high."));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("cigarette_name").orElse("§eCigarette"));
        meta.setCustomModelData(445);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGlue(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.getMessageHandler().getMessage("glue_bottle_name").orElse("§eGlue"));
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("glue_bottle_lore").orElse("§eYou need it to create long papes."));
        meta.setLore(lore);
        meta.setCustomModelData(446);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createjoint(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("joint_lore").orElse("§eYou can use it to get high."));
        meta.setLore(lore);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("joint_name").orElse("§eJoint"));
        meta.setCustomModelData(447);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createLongPapes(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("long_papes_name").orElse("§eLong Papes"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("long_papes_lore").orElse("§eYou need it to create a joint."));
        meta.setLore(lore);
        meta.setCustomModelData(448);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getPapeItem(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.getMessageHandler().getMessage("papes_name").orElse("§ePapes"));
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("papes_lore").orElse("§eYou need it to craft a cigarette or long papes."));
        meta.setLore(lore);
        meta.setCustomModelData(449);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createZippo(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("zippo_name").orElse("§eZippo"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("zippo_lore").orElse("§eYou need it to light up a joint or a cigarette."));
        meta.setLore(lore);
        meta.setCustomModelData(450);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getSuperSeedItem(Drugs plugin) {
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

    public static ItemStack getCocainePlantItem(Drugs plugin) {
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

    public static ItemStack getTobaccoPlant(Drugs plugin) {
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

    public static ItemStack getSuperWheatItem(Drugs plugin) {
        ItemStack itemStack = new ItemStack(Material.WHEAT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("weed_name").orElse("§eWeed"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("weed_lore").orElse("§eYou need it to roll a joint."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(454);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getShroomItem(Drugs plugin) {
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

    public static ItemStack getCocaineItem(Drugs plugin) {
        ItemStack itemStack = new ItemStack(Material.SUGAR);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("cocaine_name").orElse("§eCocaine"));
        List<String > lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("cocaine_lore").orElse("§eYou can use it to get high."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(465);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getTobaccoItem(Drugs plugin) {
        ItemStack itemStack = new ItemStack(Material.WHEAT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        itemMeta.setDisplayName(plugin.getMessageHandler().getMessage("tobacco_name").orElse("§eTobacco"));
        List<String> lore = new ArrayList();
        lore.add(plugin.getMessageHandler().getMessage("tobacco_lore").orElse("You need it to roll a cigarette."));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(457);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack createXTC(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("xtc_name").orElse("§eEcstasy"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("xtc_lore").orElse("§eYou can use it to get high."));
        meta.setLore(lore);
        meta.setCustomModelData(458);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createIsoSafrole(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("isosafrole_name").orElse("§eIsosafrole"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("isosafrole_lore").orElse("§eYou need it to create Ecstasy."));
        meta.setLore(lore);
        meta.setCustomModelData(459);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createpiperonal(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("piperonal_name").orElse("§ePiperonal"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("piperonal_lore").orElse("§eYou need it to create Ecstasy."));
        meta.setLore(lore);
        meta.setCustomModelData(460);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createSafrole(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("safrole_name").orElse("§eSafrole"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("safrole_lore").orElse("§eYou need it to create Ecstasy."));
        meta.setLore(lore);
        meta.setCustomModelData(461);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createPhenyl(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("phenyl_name").orElse("§ePhenyl"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("phenyl_lore").orElse("§eYou need it to create Speed."));
        meta.setLore(lore);
        meta.setCustomModelData(462);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createSpeed(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("speed_name").orElse("§eSpeed"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("speed_lore").orElse("§eYou can use it to get high."));
        meta.setLore(lore);
        meta.setCustomModelData(463);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createLiquid(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.WATER_BUCKET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.getMessageHandler().getMessage("heroin_liquid_name").orElse("§eHeroin"));
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("heroin_liquid_lore").orElse("§eYou need it to fill your syringe."));
        meta.setLore(lore);
        meta.setCustomModelData(464);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createHeroin(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.setDisplayName(plugin.getMessageHandler().getMessage("heroin_name").orElse("§eHeroin"));
        List<String> lore = new ArrayList<>();
        lore.add(plugin.getMessageHandler().getMessage("heroin_lore").orElse("§eYou can get high with it."));
        meta.setLore(lore);
        meta.setCustomModelData(465);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createSyringe(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.getMessageHandler().getMessage("syringe_name").orElse("§eSyringe"));
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("syringe_lore").orElse("§eYou need it to fill in your heroin"));
        meta.setLore(lore);
        meta.setCustomModelData(466);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createGUIBorder(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.BLACK_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.getMessageHandler().getMessage("gui_border_name").orElse("§c§lGUI Border"));
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("gui_border_lore").orElse("§4Just the Border no part of the Recipe!"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack resultGuiItem(Drugs plugin, Configuration config) {
        ItemStack item = new ItemStack(Material.GREEN_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.getMessageHandler().getMessage("gui_results_name").orElse("§a§lResults:"));
        List<String> lore = new ArrayList<>();
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        lore.add(plugin.getMessageHandler().getMessage("gui_border_lore").orElse("§aTo the right of this item is the result of the crafting"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
    

}
