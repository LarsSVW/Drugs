package de.lars.drugs.api;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
import org.bukkit.inventory.ItemStack;

public class DrugsAPI {

    private static Drugs plugin;
    private static Configuration config;

    // Setze das Plugin und die Konfiguration (muss beim Plugin-Start aufgerufen werden)
    public static void initialize(Drugs pluginInstance, Configuration configInstance) {
        plugin = pluginInstance;
        config = configInstance;
    }

    // Methode zum Erstellen eines Items anhand des Namens
    public static ItemStack giveItem(String itemName) {
        switch (itemName.toLowerCase()) {
            case "hydrogen":
                return CreatedItems.createHydrogen(plugin, config);
            case "lsd":
                return CreatedItems.createLSD(plugin, config);
            case "lysergicacid":
                return CreatedItems.createLysergicAcid(plugin, config);
            case "sodium":
                return CreatedItems.createNatrium(plugin, config);
            case "propanolamine":
                return CreatedItems.createpropanolamine(plugin, config);
            case "cigarette":
                return CreatedItems.createcigarette(plugin, config);
            case "glue":
                return CreatedItems.createGlue(plugin, config);
            case "joint":
                return CreatedItems.createjoint(plugin, config);
            case "longpapes":
                return CreatedItems.createLongPapes(plugin, config);
            case "pape":
                return CreatedItems.getPapeItem(plugin, config);
            case "zippo":
                return CreatedItems.createZippo(plugin, config);
            case "weedseed":
                return CreatedItems.getSuperSeedItem(plugin);
            case "cocaineplant":
                return CreatedItems.getCocainePlantItem(plugin);
            case "tobaccoplant":
                return CreatedItems.getTobaccoPlant(plugin);
            case "weed":
                return CreatedItems.getSuperWheatItem(plugin);
            case "shroom":
                return CreatedItems.getShroomItem(plugin);
            case "cocaine":
                return CreatedItems.getCocaineItem(plugin);
            case "tobacco":
                return CreatedItems.getTobaccoItem(plugin);
            case "xtc":
                return CreatedItems.createXTC(plugin, config);
            case "isosafrole":
                return CreatedItems.createIsoSafrole(plugin, config);
            case "piperonal":
                return CreatedItems.createpiperonal(plugin, config);
            default:
                return null; 
        }
    }
}
