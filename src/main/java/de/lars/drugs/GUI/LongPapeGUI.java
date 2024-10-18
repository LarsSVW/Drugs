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

public class LongPapeGUI {
    private final Configuration config;
    private final Drugs plugin;

    public LongPapeGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Long Pape Crafting Recipe");

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

        ItemStack resultitem = createLongPapes(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack glueMaterial = new ItemStack(config.getString("glue_material", "AIR").equals("AIR") ? Material.AIR : Material.valueOf(config.getString("glue_material")));
        GuiItem guiItemG = new GuiItem(glueMaterial, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack papeMaterial = new ItemStack(config.getString("pape_material", "AIR").equals("AIR") ? Material.AIR : Material.valueOf(config.getString("pape_material")));
        GuiItem guiItemP = new GuiItem(papeMaterial, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        pane.addItem(guiItemP, 1, 2); // P
        pane.addItem(guiItemG, 2, 2);  // G
        pane.addItem(guiItemP, 3, 2);  // P

        pane.addItem(resultsItem, 5, 4);

        pane.addItem(resultItem, 7, 4);

        for (int i = 0; i < 9; i++) {
            pane.addItem(borderItem, i, 0);
            pane.addItem(borderItem, i, 4);
        }
        for (int i = 1; i < 4; i++) {
            pane.addItem(borderItem, 0, i);
            pane.addItem(borderItem, 8, i);
        }

        gui.addPane(pane);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        gui.show(player);
    }
}
