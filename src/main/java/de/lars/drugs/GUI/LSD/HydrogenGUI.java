package de.lars.drugs.GUI.LSD;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.handler.CreatedItems;
import de.lars.drugs.config.Configuration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.lars.drugs.handler.CreatedItems.*;

public class HydrogenGUI {
    private final Configuration config;
    private final Drugs plugin;

    public HydrogenGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Hydrogen Crafting Recipe");

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

        // Result Item (Hydrogen)
        ItemStack resultitem = createHydrogen(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        // Crafting Ingredients (Water Bucket and Torch)
        ItemStack waterBucketItem = new ItemStack(Material.WATER_BUCKET);
        GuiItem waterBucketGuiItem = new GuiItem(waterBucketItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack torchItem = new ItemStack(Material.TORCH);
        GuiItem torchGuiItem = new GuiItem(torchItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        pane.addItem(waterBucketGuiItem, 2, 2);
        pane.addItem(torchGuiItem, 2, 3);

        pane.addItem(resultsItem, 5, 2);

        pane.addItem(resultItem, 7, 2);

        for (int i = 0; i < 9; i++) {
            pane.addItem(borderItem, i, 0); // Top border
            pane.addItem(borderItem, i, 4); // Bottom border
        }
        for (int i = 1; i < 4; i++) {
            pane.addItem(borderItem, 0, i); // Left border
            pane.addItem(borderItem, 8, i); // Right border
        }

        gui.addPane(pane);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        gui.show(player);
    }
}
