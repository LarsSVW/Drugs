package de.lars.drugs.commands;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LanguageProvidedCommand implements CommandExecutor {

    private final Drugs plugin;
    private final Configuration config;
    public LanguageProvidedCommand(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            sender.sendMessage("§aLanguages provided: english(en), german(de), french(fr), russian(ru),spanish(es), turkish(tr)");
            sender.sendMessage("§aCommand to change the language: /languageswitch LANGUAGE");
            sender.sendMessage("§aFor example: /languageswitch en to switch the language to english");
        return true;
    }
}

