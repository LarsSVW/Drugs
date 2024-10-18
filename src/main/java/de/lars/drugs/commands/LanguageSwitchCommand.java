package de.lars.drugs.commands;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LanguageSwitchCommand implements CommandExecutor {
    private final Drugs plugin;
    private final Configuration config;
    private final String LANGUAGES_DIR = "plugins/Drugs/languages/";
    private final String MESSAGES_FILE = "plugins/Drugs/messages.yml";

    public LanguageSwitchCommand(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /languageswitch LANGUAGE");
            return false;
        }
        if (!sender.hasPermission("drugs.changelanguage")){
            sender.sendMessage(plugin.getMessageHandler().getMessage("no_authorization").orElse("%prefix% §cYou dont have the rights to do this!").replace("%prefix%", plugin.getMessageHandler().getMessage("prefix").orElse("§0§l[§3§lDrugs§0§l]")));
            return true;
        }
        String language = args[0].toLowerCase();
        if (!isValidLanguage(language)) {
            sender.sendMessage("Invalid language. Supported languages are: de, en, es, fr, ru, tr.");
            return false;
        }

        File languageFile = new File(LANGUAGES_DIR + language + ".yml");
        File messagesFile = new File(MESSAGES_FILE);

        if (!languageFile.exists()) {
            sender.sendMessage("Language file not found: " + languageFile.getPath());
            return false;
        }

        try {
            // Copy the contents of the language file to the messages file
            Files.copy(languageFile.toPath(), messagesFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            sender.sendMessage("Language switched to " + language);
            plugin.getMessageHandler().reloadMessages();
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            plugin.getServer().getPluginManager().enablePlugin(plugin);
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage("An error occurred while switching languages.");
        }

        return true;
    }

    private boolean isValidLanguage(String language) {
        switch (language) {
            case "de":
            case "en":
            case "es":
            case "fr":
            case "ru":
            case "tr":
                return true;
            default:
                return false;
        }
    }
}
