package de.lars.drugs.handler.languagehandler;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.MessageHandler;
import org.bukkit.Bukkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FirstStartLanguageInitializer {

    private final Drugs plugin;
    private final Configuration config;
    private final String LANGUAGES_DIR = "plugins/Drugs/languages/";
    private final String MESSAGES_FILE = "plugins/Drugs/messages.yml";
    private final String FIRST_START_FILE = "plugins/Drugs/saves/first_start.yml";

    public FirstStartLanguageInitializer(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void initializeLanguageOnFirstStart() {
        File firstStartFile = new File(FIRST_START_FILE);

        if (!firstStartFile.exists()) {
            File firstStartFolder = firstStartFile.getParentFile();
            if (firstStartFolder != null && !firstStartFolder.exists()) {
                firstStartFolder.mkdirs();
            }

            File languageFile = new File(LANGUAGES_DIR + "en.yml");
            File messagesFile = new File(MESSAGES_FILE);

            if (languageFile.exists()) {
                try {
                    Files.copy(languageFile.toPath(), messagesFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    plugin.getLogger().info("Language set to English (en) on first start.");

                    firstStartFile.createNewFile();

                    plugin.getMessageHandler().reloadMessages();

                } catch (IOException e) {
                    plugin.getLogger().severe("An error occurred while setting the language on first start.");
                    e.printStackTrace();
                }
            } else {
                plugin.getLogger().severe("Language file en.yml not found!");
            }
        } else {
            plugin.getLogger().info("This is not the first start. Language not changed.");
        }
    }
}
