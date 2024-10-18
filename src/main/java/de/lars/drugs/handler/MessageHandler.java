package de.lars.drugs.handler;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.ChatColor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class MessageHandler {

    private final Drugs plugin;
    private final Map<String, String> messages;

    private Configuration messageConfig;

    public MessageHandler(final Drugs plugin) {
        this.plugin = plugin;
        this.messages = new HashMap<>();
        this.messageConfig = new Configuration(new File(plugin.getDataFolder(), "messages.yml"));
        messageConfig.load();
        checkifexist();
        reloadMessages();
    }
    public void checkifexist() {
        String language = plugin.getConfig().getString("language", "en.yml");
        String[] lang = language.split("\\.");
        String configLanguage = messageConfig.getString("language");

        if (configLanguage == null) {
            plugin.getLogger().warning("Language configuration is missing in messages.yml.");
        } else if (!configLanguage.equalsIgnoreCase(lang[0])) {
            messageConfig.getFile().delete();
            this.messageConfig = new Configuration(new File(plugin.getDataFolder(), "messages.yml"));
            this.messageConfig.setTemplateName("/languages/" + language);
        }
    }



    public void reloadMessages() {
        messages.clear();
        messageConfig.load();
        for (String key : messageConfig.getKeys(false)) {
            messages.put(key.toLowerCase(),
                    ChatColor.translateAlternateColorCodes('&', messageConfig.getString(key)));
        }
    }

    public Optional<String> getMessage(final String key) {
        return Optional.ofNullable(messages.get(key.toLowerCase()));
    }

}
