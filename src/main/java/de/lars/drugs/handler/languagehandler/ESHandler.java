package de.lars.drugs.handler.languagehandler;


import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ESHandler {
    private final Drugs plugin;
    private final Map<String, String> messages;

    private final Configuration messageConfig;

    public ESHandler(final Drugs plugin) {
        this.plugin = plugin;
        this.messages = new HashMap<>();
        this.messageConfig = new Configuration(new File(plugin.getDataFolder(), "/languages/es.yml"));
        this.messageConfig.setTemplateName("/languages/es.yml");
        reloadMessages();
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

