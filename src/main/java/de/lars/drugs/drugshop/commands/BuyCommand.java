package de.lars.drugs.drugshop.commands;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.lars.drugs.handler.CreatedItems.*;

public class BuyCommand{
    private final Configuration config;
    private final Drugs plugin;
    private final Map<String, Double> itemPrices;  // Map to store item prices
    private boolean vaultInstalled; // Flag to check if Vault is installed

    public BuyCommand(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
        this.itemPrices = new HashMap<>();

        // Initializing prices for each item
        itemPrices.put("hydrogen", config.getDouble("hydrogen_price", 100));
        itemPrices.put("lsd", config.getDouble("lsd_price", 100));
        itemPrices.put("lysergicacid", config.getDouble("lysergicacid_price", 100));
        itemPrices.put("sodium", config.getDouble("sodium_price", 100));
        itemPrices.put("propanolamine", config.getDouble("propanolamine", 100));
        itemPrices.put("cigarette", config.getDouble("cigarette", 100));
        itemPrices.put("glue", config.getDouble("glue", 100));
        itemPrices.put("joint", config.getDouble("joint", 100));
        itemPrices.put("longpapes", config.getDouble("longpapes", 100));
        itemPrices.put("papes", config.getDouble("papes", 100));
        itemPrices.put("zippo", config.getDouble("zippo", 100));
        itemPrices.put("weedseed", config.getDouble("weedseed", 100));
        itemPrices.put("cocaineseed", config.getDouble("cocaineseed", 100));
        itemPrices.put("tobaccoseed", config.getDouble("tobaccoseed", 100));
        itemPrices.put("weed", config.getDouble("weed", 100));
        itemPrices.put("shroom", config.getDouble("shroom", 100));
        itemPrices.put("cocaine", config.getDouble("cocaine", 100));
        itemPrices.put("tobacco", config.getDouble("tobacco", 100));
        itemPrices.put("xtc", config.getDouble("xtc", 100));
        itemPrices.put("isosafrole", config.getDouble("isosafrole", 100));
        itemPrices.put("piperonal", config.getDouble("piperonal", 100));
        itemPrices.put("safrole", config.getDouble("safrole", 100));
        itemPrices.put("speed", config.getDouble("speed", 100));
        itemPrices.put("phenyl", config.getDouble("phenyl", 100));
        itemPrices.put("heroinliquid", config.getDouble("heroinliquid", 100));
        itemPrices.put("heroin", config.getDouble("heroin", 100));
        itemPrices.put("syringe", config.getDouble("syringe", 100));

        // Check if Vault is installed
        vaultInstalled = plugin.getServer().getPluginManager().getPlugin("Vault") != null;
    }


    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessageHandler().getMessage("only_players")
                    .orElse("%prefix% §cOnly players can execute this command!")
                    .replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 4) {
            player.sendMessage(plugin.getMessageHandler().getMessage("wrong_usage")
                    .orElse("%prefix% §cWrong usage! Correct usage: %usage%")
                    .replace("%usage%", "/drugs buy DRUGTYPE DRUGNAME AMOUNT")
                    .replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        if (!player.hasPermission("drugs.get")) {
            player.sendMessage(plugin.getMessageHandler().getMessage("no_authorization")
                    .orElse("%prefix% §cYou don't have the rights to do this!")
                    .replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        if (!vaultInstalled) {
            player.sendMessage("§cVault not installed! Contact an Administrator.");
            return true;
        }

        String drug = args[2].toLowerCase();
        int amount;

        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(plugin.getMessageHandler().getMessage("invalid_amount")
                    .orElse("%prefix% §eInvalid amount specified!")
                    .replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        ItemStack drugItem;
        String drugname = args[2];

        // Validate if the drug exists in the price list
        if (!itemPrices.containsKey(drug)) {
            player.sendMessage(plugin.getMessageHandler().getMessage("invalid_drug")
                    .orElse("%prefix% §eInvalid drug specified!")
                    .replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }
        if (!player.hasPermission("drugs.buy")){
            player.sendMessage(plugin.getMessageHandler().getMessage("no_permission_to_buy")
                    .orElse("%prefix% §eInvalid drug specified!")
                    .replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
         }


        double price = itemPrices.get(drug) * amount;
        if (!plugin.getEconomy().withdrawPlayer(player, price).transactionSuccess()) {
            player.sendMessage(plugin.getMessageHandler().getMessage("not_enough_money")
                    .orElse("%prefix% §cYou don't have enough money to buy this!")
                    .replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        switch (drug) {
            case "hydrogen":
                drugItem = CreatedItems.createHydrogen(plugin, config);
                break;
            case "lsd":
                drugItem = createLSD(plugin, config);
                break;
            case "lysergicacid":
                drugItem = createLysergicAcid(plugin, config);
                break;
            case "sodium":
                drugItem = CreatedItems.createNatrium(plugin, config);
                break;
            case "propanolamine":
                drugItem = createpropanolamine(plugin, config);
                break;
            case "cigarette":
                drugItem = createcigarette(plugin, config);
                break;
            case "glue":
                drugItem = createGlue(plugin, config);
                break;
            case "joint":
                drugItem = createjoint(plugin, config);
                break;
            case "longpapes":
                drugItem = createLongPapes(plugin, config);
                break;
            case "papes":
                drugItem = getPapeItem(plugin, config);
                break;
            case "zippo":
                drugItem = createZippo(plugin, config);
                break;
            case "weedseed":
                drugItem = getSuperSeedItem(plugin);
                break;
            case "cocaineseed":
                drugItem = getCocainePlantItem(plugin);
                break;
            case "tobaccoseed":
                drugItem = getTobaccoPlant(plugin);
                break;
            case "weed":
                drugItem = getSuperWheatItem(plugin);
                break;
            case "shroom":
                drugItem = getShroomItem(plugin);
                break;
            case "cocaine":
                drugItem = getCocaineItem(plugin);
                break;
            case "tobacco":
                drugItem = getTobaccoItem(plugin);
                break;
            case "xtc":
                drugItem = createXTC(plugin, config);
                break;
            case "isosafrole":
                drugItem = createIsoSafrole(plugin, config);
                break;
            case "piperonal":
                drugItem = createpiperonal(plugin, config);
                break;
            case "safrole":
                drugItem = createSafrole(plugin, config);
                break;
            case "speed":
                drugItem = createSpeed(plugin, config);
                break;
            case "phenyl":
                drugItem = createPhenyl(plugin, config);
                break;
            case "heroinliquid":
                drugItem = createLiquid(plugin, config);
                break;
            case "heroin":
                drugItem = createHeroin(plugin, config);
                break;
            case "syringe":
                drugItem = createSyringe(plugin, config);
                break;
            default:
                player.sendMessage(plugin.getMessageHandler().getMessage("invalid_drug")
                        .orElse("%prefix% §eInvalid drug specified!")
                        .replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
                return true;
        }

        drugItem.setAmount(amount);
        player.getInventory().addItem(drugItem);
        player.sendMessage(plugin.getMessageHandler().getMessage("buy_success")
                .orElse("%prefix% §aYou have successfully bought %amount% %item%!")
                .replace("%amount%", String.valueOf(amount))
                .replace("%item%", drugname)
                .replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 3 && args[1].equalsIgnoreCase("ecstasy")) {
            completions.add("IsoSafrole");
            completions.add("safrole");
            completions.add("piperonal");
            completions.add("XTC");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("lsd")) {
            completions.add("Hydrogen");
            completions.add("LSD");
            completions.add("Lysergicacid");
            completions.add("propanolamine");
            completions.add("Sodium");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("cigarette")) {
            completions.add("Cigarette");
            completions.add("papes");
            completions.add("Tobacco");
            completions.add("TobaccoSeed");
            completions.add("Zippo");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("weed")) {
            completions.add("Glue");
            completions.add("Joint");
            completions.add("Longpapes");
            completions.add("Weed");
            completions.add("WeedSeed");
            completions.add("Zippo");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("cocaine")) {
            completions.add("Cocaine");
            completions.add("CocaineSeed");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("speed")) {
            completions.add("Speed");
            completions.add("Phenyl");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("heroin")) {
            completions.add("Heroin");
            completions.add("Heroinliquid");
            completions.add("Syringe");
        } else if (args.length == 3 && args[1].equalsIgnoreCase("shrooms")) {
            completions.add("Shroom");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            completions.add("cigarette");
            completions.add("cocaine");
            completions.add("ecstasy");
            completions.add("lsd");
            completions.add("shrooms");
            completions.add("weed");
            completions.add("speed");
            completions.add("heroin");
        } else if (args.length == 1 && "drugs".startsWith(args[0].toLowerCase())) {
            completions.add("buy");
        }
        return completions;
    }
}