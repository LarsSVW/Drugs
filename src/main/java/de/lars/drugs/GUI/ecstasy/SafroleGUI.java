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

public class SafroleGUI {
    private final Configuration config;
    private final Drugs plugin;

    public SafroleGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Safrole Crafting Recipe");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        ItemStack borderitem = CreatedItems.createGUIBorder(plugin, config);
        GuiItem borderItem = new GuiItem(borderitem, event -> {
            event.getWhoClicked().sendMessage("Border Item");
            event.setCancelled(true);
        });

        ItemStack resultsitem = CreatedItems.resultGuiItem(plugin, config);
        GuiItem resultsItem = new GuiItem(resultsitem, event -> {
            event.getWhoClicked().sendMessage("Results");
            event.setCancelled(true);
        });

        ItemStack resultitem = CreatedItems.createSafrole(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack emeraldItem = new ItemStack(Material.EMERALD);
        GuiItem emeraldGuiItem = new GuiItem(emeraldItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack sweetBerriesItem = new ItemStack(Material.SWEET_BERRIES);
        GuiItem sweetBerriesGuiItem = new GuiItem(sweetBerriesItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        for (int i = 0; i < 9; i++) {
            pane.addItem(borderItem, i, 0); // Top border
            pane.addItem(borderItem, i, 4); // Bottom border
        }
        for (int i = 1; i < 4; i++) {
            pane.addItem(borderItem, 0, i); // Left border
            pane.addItem(borderItem, 8, i); // Right border
        }

        pane.addItem(emeraldGuiItem, 1, 1); // 'E' position
        pane.addItem(sweetBerriesGuiItem, 2, 1); // 'S' position
        pane.addItem(emeraldGuiItem, 1, 2); // 'E' position
        pane.addItem(sweetBerriesGuiItem, 2, 2); // 'S' position
        pane.addItem(sweetBerriesGuiItem, 1,3);

        pane.addItem(new GuiItem(new ItemStack(Material.AIR)), 2, 1); // Empty slot
        pane.addItem(new GuiItem(new ItemStack(Material.AIR)), 2, 2); // Empty slot

        pane.addItem(resultsItem, 5, 2);

        pane.addItem(resultItem, 7, 2);

        gui.addPane(pane);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        gui.show(player);
    }
}
