package de.lars.drugs.commands;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.crafting.ZippoCrafting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ReloadCommand {

    private final Drugs plugin;
    private final Configuration config;

    public ReloadCommand(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("Drugs.reload")) {
            sender.sendMessage(plugin.getMessageHandler().getMessage("no_authorization").orElse("§cYou dont have the rights to do this!").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            togglePlugin(sender, plugin, config);
        } else {
            sender.sendMessage(plugin.getMessageHandler().getMessage("wrong_usage").orElse("%prefix% §cWrong usage! Correct usage: %usage%").replace("%usage%", "/drl reload").replace("prefix", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
        }

        return true;
    }

    public void togglePlugin(CommandSender sender, Drugs plugin, Configuration config) {
        plugin.getServer().getPluginManager().disablePlugin(plugin);
        sender.sendMessage(plugin.getMessageHandler().getMessage("reload_started").orElse("§aReload started!").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
        plugin.getServer().getPluginManager().enablePlugin(plugin);
        sender.sendMessage(plugin.getMessageHandler().getMessage("reload_finished").orElse("§aReload finished!").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
    }
}
