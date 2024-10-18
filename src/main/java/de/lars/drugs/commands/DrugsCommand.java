package de.lars.drugs.commands;

import de.lars.drugs.Drugs;
import de.lars.drugs.GUI.*;
import de.lars.drugs.GUI.LSD.*;
import de.lars.drugs.GUI.Speed.PhenylGUI;
import de.lars.drugs.GUI.Speed.SpeedGUI;
import de.lars.drugs.GUI.ecstasy.EcstasyGUI;
import de.lars.drugs.GUI.ecstasy.IsoSafroleGUI;
import de.lars.drugs.GUI.ecstasy.PiperonalGUI;
import de.lars.drugs.GUI.ecstasy.SafroleGUI;
import de.lars.drugs.GUI.heroin.HeroinGUI;
import de.lars.drugs.GUI.heroin.HeroinLiquidGUI;
import de.lars.drugs.GUI.heroin.SyringeGUI;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.drugshop.commands.BuyCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DrugsCommand implements CommandExecutor, TabCompleter {

    private final GiveDrugsCommand giveDrugsCommand;
    private final BuyCommand buyCommand;
    private final ReloadCommand reloadCommand;
    private final Drugs plugin;
    private Configuration config;

    public DrugsCommand(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.giveDrugsCommand = new GiveDrugsCommand(plugin, config);
        this.buyCommand = new BuyCommand(plugin, config);
        this.reloadCommand = new ReloadCommand(plugin, config);
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("drugs")) {
            if (args.length == 0) {
                sender.sendMessage("Usage: /drugs <give|buy|info|reload>");
                return false;
            }

            String subCommand = args[0];

            switch (subCommand.toLowerCase()) {
                case "give":
                    return giveDrugsCommand.execute(sender, args);
                case "buy":
                    return buyCommand.execute(sender, args);
                case "reload":
                    return reloadCommand.execute(sender, args);
                case "info":
                    if (args.length < 2) {
                        sender.sendMessage("Usage: /drugs info <drug>");
                        return false;
                    }
                    String drug = args[1].toLowerCase();
                    openDrugInfoGUI(drug, player);
                    return true;
                default:
                    sender.sendMessage("Unknown subcommand. Usage: /drugs <give|buy|info>");
                    return false;
            }
        }
        return false;
    }

    private void openDrugInfoGUI(String drug, Player player) {
        switch (drug) {
            case "cocaine":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "cocaineseed":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "ecstasy":
                new EcstasyGUI(plugin, config).open(player);
                break;
            case "isosafrole":
                new IsoSafroleGUI(plugin, config).open(player);
                break;
            case "safrole":
                new SafroleGUI(plugin, config).open(player);
                break;
            case "piperonal":
                new PiperonalGUI(plugin, config).open(player);
                break;
            case "xtc":
                new EcstasyGUI(plugin, config).open(player);
                break;
            case "weed":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "joint":
                new JointGUI(plugin, config).open(player);
                break;
            case "longpapes":
                new LongPapeGUI(plugin, config).open(player);
                break;
            case "weedseed":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "glue":
                new GlueGUI(plugin, config).open(player);
                break;
            case "zippo":
                new ZippoGUI(plugin, config).open(player);
                break;
            case "lsd":
                new LSDGUI(plugin, config).open(player);
                break;
            case "hydrogen":
                new HydrogenGUI(plugin, config).open(player);
                break;
            case "lysergicacid":
                new LysergicAcidGUI(plugin, config).open(player);
                break;
            case "propanolamine":
                new PropanolamineGUI(plugin, config).open(player);
                break;
            case "sodium":
                new NatriumGUI(plugin, config).open(player);
                break;
            case "cigarette":
                new CigaretteGUI(plugin, config).open(player);
                break;
            case "papes":
                new PapeGUI(plugin, config).open(player);
                break;
            case "tobacco":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "tobaccoseed":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "speed":
                new SpeedGUI(plugin, config).open(player);
                break;
            case "phenyl":
                new PhenylGUI(plugin, config).open(player);
                break;
            case "heroin":
                new HeroinGUI(plugin, config).open(player);
                break;
            case "heroinliquid":
                new HeroinLiquidGUI(plugin, config).open(player);
                break;
            case "syringe":
                new SyringeGUI(plugin, config).open(player);
                break;
            case "shrooms":
                player.sendMessage("§0§l[§a§lDrugs§0§l] §cNo Crafting available! Only harvesting!");
            case "test":
                new PapeGUI(plugin, config).open(player);
                break;
            default:
                player.sendMessage("Unknown drug. Available options: cocaine, cocaineseed, ecstasy, xtc, weed, lsd, cigarette, speed, heroin, shrooms.");
                break;
        }
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
        } else if (args.length == 2 && args[0].equalsIgnoreCase("buy")) {
            completions.add("cigarette");
            completions.add("cocaine");
            completions.add("ecstasy");
            completions.add("lsd");
            completions.add("shrooms");
            completions.add("weed");
            completions.add("speed");
            completions.add("heroin");
    }   else if (args.length == 1) {
            completions.add("give");
            completions.add("buy");
            completions.add("info");
            completions.add("reload");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
            completions.add("cocaine");
            completions.add("cocaineseed");
            completions.add("ecstasy");
            completions.add("isosafrole");
            completions.add("safrole");
            completions.add("piperonal");
            completions.add("xtc");
            completions.add("weed");
            completions.add("joint");
            completions.add("longpapes");
            completions.add("weedseed");
            completions.add("glue");
            completions.add("zippo");
            completions.add("lsd");
            completions.add("hydrogen");
            completions.add("lysergicacid");
            completions.add("propanolamine");
            completions.add("sodium");
            completions.add("cigarette");
            completions.add("papes");
            completions.add("tobacco");
            completions.add("tobaccoseed");
            completions.add("speed");
            completions.add("phenyl");
            completions.add("heroin");
            completions.add("heroinliquid");
            completions.add("syringe");
            completions.add("shrooms");
        }
        return completions;
    }
}
