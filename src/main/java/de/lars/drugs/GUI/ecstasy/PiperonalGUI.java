package de.lars.drugs.GUI.ecstasy;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.handler.CreatedItems;
import de.lars.drugs.config.Configuration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PiperonalGUI {
    private final Configuration config;
    private final Drugs plugin;

    public PiperonalGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Piperonal Crafting Recipe");

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

        // Result Item (Piperonal)
        ItemStack resultitem = CreatedItems.createpiperonal(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        // Crafting Ingredients
        ItemStack diamondItem = new ItemStack(Material.DIAMOND);
        GuiItem diamondGuiItem = new GuiItem(diamondItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack sugarItem = new ItemStack(Material.SUGAR);
        GuiItem sugarGuiItem = new GuiItem(sugarItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        for (int i = 0; i < 9; i++) {
            pane.addItem(borderItem, i, 0);
            pane.addItem(borderItem, i, 4);
        }
        for (int i = 1; i < 4; i++) {
            pane.addItem(borderItem, 0, i);
            pane.addItem(borderItem, 8, i);
        }

        pane.addItem(diamondGuiItem, 1, 1);
        pane.addItem(sugarGuiItem, 2, 1);
        pane.addItem(diamondGuiItem, 1, 2);
        pane.addItem(sugarGuiItem, 2, 2);
        pane.addItem(diamondGuiItem, 1, 3);

        pane.addItem(new GuiItem(new ItemStack(Material.AIR)), 2, 1); // Empty slot
        pane.addItem(new GuiItem(new ItemStack(Material.AIR)), 2, 2); // Empty slot

        pane.addItem(resultsItem, 5, 2);

        pane.addItem(resultItem, 7, 2);

        gui.addPane(pane);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        gui.show(player);
    }
}
