package de.lars.drugs.GUI.heroin;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.handler.CreatedItems;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.crafting.Heroin.HeroinLiquidCrafting;
import de.lars.drugs.crafting.Heroin.SyringeCrafting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static de.lars.drugs.handler.CreatedItems.*;

public class HeroinGUI {
    private final Configuration config;
    private final Drugs plugin;

    public HeroinGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Heroin Crafting Recipe");

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

        // Result Item (Heroin)
        ItemStack resultitem = CreatedItems.createHeroin(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        // Crafting Ingredients
        ItemStack syringeItem = createSyringe(plugin, config); // Create syringe item
        GuiItem syringeGuiItem = new GuiItem(syringeItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack heroinLiquidItem = createLiquid(plugin, config); // Create heroin liquid item
        GuiItem heroinLiquidGuiItem = new GuiItem(heroinLiquidItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        // Set up borders
        for (int i = 0; i < 9; i++) {
            pane.addItem(borderItem, i, 0); // Top border
            pane.addItem(borderItem, i, 4); // Bottom border
        }
        for (int i = 1; i < 4; i++) {
            pane.addItem(borderItem, 0, i); // Left border
            pane.addItem(borderItem, 8, i); // Right border
        }

        // Crafting Recipe Area
        pane.addItem(new GuiItem(new ItemStack(Material.AIR)), 1, 1); // Empty slot
        pane.addItem(syringeGuiItem, 2, 2); // 'S' position
        pane.addItem(heroinLiquidGuiItem, 2, 3); // 'H' position

        // Results Item
        pane.addItem(resultsItem, 5, 2);

        // Final Result Item (Heroin)
        pane.addItem(resultItem, 7, 2);

        // Add the pane to the GUI
        gui.addPane(pane);

        // Disable taking items out of the GUI
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        // Show the GUI to the player
        gui.show(player);
    }
}
