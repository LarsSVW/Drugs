package de.lars.drugs.GUI.heroin;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.handler.CreatedItems;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.crafting.Heroin.HeroinLiquidCrafting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static de.lars.drugs.handler.CreatedItems.*;

public class HeroinLiquidGUI {
    private final Configuration config;
    private final Drugs plugin;

    public HeroinLiquidGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Heroin Liquid Crafting Recipe");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        // Border Item
        ItemStack borderitem = CreatedItems.createGUIBorder(plugin, config);
        GuiItem borderItem = new GuiItem(borderitem, event -> {
            event.getWhoClicked().sendMessage("Border Item");
            event.setCancelled(true);
        });

        // Results Item
        ItemStack resultsitem = CreatedItems.resultGuiItem(plugin, config);
        GuiItem resultsItem = new GuiItem(resultsitem, event -> {
            event.getWhoClicked().sendMessage("Results");
            event.setCancelled(true);
        });

        // Result Item (Heroin Liquid)
        ItemStack resultitem = createLiquid(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        // Crafting Ingredients
        ItemStack[] ingredientItems = new ItemStack[9];
        for (char c = 'A'; c <= 'I'; c++) {
            String materialKey = "heroin_liquid_material_" + Character.toLowerCase(c);
            String materialString = config.getString(materialKey, "AIR");
            Material material = Material.matchMaterial(materialString);
            ingredientItems[c - 'A'] = (material != null && material != Material.AIR) ? new ItemStack(material) : new ItemStack(Material.AIR);
        }

        // Set crafting recipe area
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ingredientItems[i * 3 + j].getType() != Material.AIR) {
                    GuiItem ingredientGuiItem = new GuiItem(ingredientItems[i * 3 + j], event -> {
                        event.getWhoClicked().sendMessage("You can't take it!");
                        event.setCancelled(true);
                    });
                    pane.addItem(ingredientGuiItem, j + 1, i + 1); // Fill crafting area
                }
            }
        }

        // Results Item
        pane.addItem(resultsItem, 5, 2);

        // Final Result Item (Heroin Liquid)
        pane.addItem(resultItem, 7, 2);

        // Set up borders
        for (int i = 0; i < 9; i++) {
            pane.addItem(borderItem, i, 0); // Top border
            pane.addItem(borderItem, i, 4); // Bottom border
        }
        for (int i = 1; i < 4; i++) {
            pane.addItem(borderItem, 0, i); // Left border
            pane.addItem(borderItem, 8, i); // Right border
        }

        // Add the pane to the GUI
        gui.addPane(pane);

        // Disable taking items out of the GUI
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        // Show the GUI to the player
        gui.show(player);
    }
}
