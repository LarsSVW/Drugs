package de.lars.drugs.GUI;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static de.lars.drugs.handler.CreatedItems.*;

public class CigaretteGUI {
    private final Configuration config;
    private final Drugs plugin;

    public CigaretteGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Cigarette Crafting Recipe");

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

        // Final Result Item (Cigarette)
        ItemStack resultitem = createcigarette(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        // Crafting Ingredients (Paper, Tobacco)
        ItemStack paperItem = new ItemStack(getPapeItem(plugin, config));
        GuiItem paperGuiItem = new GuiItem(paperItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack tobaccoItem = new ItemStack(getTobaccoItem(plugin));
        GuiItem tobaccoGuiItem = new GuiItem(tobaccoItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        pane.addItem(paperGuiItem, 1, 1);
        pane.addItem(tobaccoGuiItem, 2, 2);
        pane.addItem(paperGuiItem, 1, 2);
        pane.addItem(paperGuiItem, 1, 3);
        pane.addItem(paperGuiItem, 2, 1);
        pane.addItem(paperGuiItem, 2, 3);
        pane.addItem(paperGuiItem, 3, 1);
        pane.addItem(paperGuiItem,3,2);
        pane.addItem(paperGuiItem,3,3);

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
