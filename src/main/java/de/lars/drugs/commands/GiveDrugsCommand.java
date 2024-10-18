package de.lars.drugs.commands;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveDrugsCommand {
    private final Configuration config;
    private final Drugs plugin;

    public GiveDrugsCommand(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }
        public boolean execute(CommandSender sender, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessageHandler().getMessage("only_players").orElse("%prefix% §cOnly players can execute this command!").replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 4) {
            player.sendMessage(plugin.getMessageHandler().getMessage("wrong_usage").orElse("%prefix% §cWrong usage! Correct usage: %usage%").replace("%usage%", "/drugs give DRUGTYPE DRUG AMOUNT").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }
        if (!player.hasPermission("drugs.get")) {
            player.sendMessage(plugin.getMessageHandler().getMessage("no_authorization").orElse("%prefix% §cYou dont have the rights to do this!").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        String drug = args[2].toLowerCase();
        int amount;

        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(plugin.getMessageHandler().getMessage("invalid_amount").orElse("%prefix% §eInvalid amount specified!").replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        ItemStack drugItem;
        String drugname = args[2];

        if (drug.equals("hydrogen")) {
            drugItem = CreatedItems.createHydrogen(plugin, config);
        } else if (drug.equals("lsd")) {
            drugItem = CreatedItems.createLSD(plugin, config);
        } else if (drug.equals("lysergicacid")) {
            drugItem = CreatedItems.createLysergicAcid(plugin, config);
        } else if (drug.equals("sodium")) {
            drugItem = CreatedItems.createNatrium(plugin, config);
        } else if (drug.equals("propanolamine")) {
            drugItem = CreatedItems.createpropanolamine(plugin, config);
        } else if (drug.equals("cigarette")) {
            drugItem = CreatedItems.createcigarette(plugin, config);
        } else if (drug.equals("glue")) {
            drugItem = CreatedItems.createGlue(plugin, config);
        } else if (drug.equals("joint")) {
            drugItem = CreatedItems.createjoint(plugin, config);
        } else if (drug.equals("longpapes")) {
            drugItem = CreatedItems.createLongPapes(plugin, config);
        } else if (drug.equals("papes")) {
            drugItem = CreatedItems.getPapeItem(plugin, config);
        } else if (drug.equals("zippo")) {
            drugItem = CreatedItems.createZippo(plugin, config);
        } else if (drug.equals("weedseed")) {
            drugItem = CreatedItems.getSuperSeedItem(plugin);
        } else if (drug.equals("cocaineseed")) {
            drugItem = CreatedItems.getCocainePlantItem(plugin);
        } else if (drug.equals("tobaccoseed")) {
            drugItem = CreatedItems.getTobaccoPlant(plugin);
        } else if (drug.equals("weed")) {
            drugItem = CreatedItems.getSuperWheatItem(plugin);
        } else if (drug.equals("shroom")) {
            drugItem = CreatedItems.getShroomItem(plugin);
        } else if (drug.equals("cocaine")) {
            drugItem = CreatedItems.getCocaineItem(plugin);
        } else if (drug.equals("tobacco")) {
            drugItem = CreatedItems.getTobaccoItem(plugin);
        } else if (drug.equals("xtc")) {
            drugItem = CreatedItems.createXTC(plugin, config);
        } else if (drug.equals("isosafrole")) {
            drugItem = CreatedItems.createIsoSafrole(plugin, config);
        } else if (drug.equals("piperonal")) {
            drugItem = CreatedItems.createpiperonal(plugin, config);
        } else if (drug.equals("safrole")) {
            drugItem = CreatedItems.createSafrole(plugin, config);
        } else if (drug.equals("speed")) {
            drugItem = CreatedItems.createSpeed(plugin, config);
        } else if (drug.equals("phenyl")) {
            drugItem = CreatedItems.createPhenyl(plugin, config);
        } else if (drug.equals("heroinliquid")) {
            drugItem = CreatedItems.createLiquid(plugin, config);
        } else if (drug.equals("heroin")) {
            drugItem = CreatedItems.createHeroin(plugin, config);
        } else if (drug.equals("syringe")) {
            drugItem = CreatedItems.createSyringe(plugin,config);
        } else {
            player.sendMessage(plugin.getMessageHandler().getMessage("invalid_drug").orElse("%prefix% §eInvalid drug specified!").replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        drugItem.setAmount(amount);

        player.getInventory().addItem(drugItem);
        player.sendMessage(plugin.getMessageHandler().getMessage("drug_received").orElse("You received %amount% %drugname%").replace("%amount%", String.valueOf(amount)).replace("%drugname%", drugname));

        return true;
    }


}
