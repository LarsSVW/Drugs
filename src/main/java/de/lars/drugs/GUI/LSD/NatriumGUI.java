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

import static de.lars.drugs.handler.CreatedItems.createNatrium;

public class NatriumGUI {
    private final Configuration config;
    private final Drugs plugin;

    public NatriumGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Natrium Crafting Recipe");

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

        ItemStack resultitem = createNatrium(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack sugarCaneItem = new ItemStack(Material.SUGAR_CANE);
        GuiItem sugarCaneGuiItem = new GuiItem(sugarCaneItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack dandelionItem = new ItemStack(Material.DANDELION);
        GuiItem dandelionGuiItem = new GuiItem(dandelionItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        pane.addItem(sugarCaneGuiItem, 1, 1);
        pane.addItem(sugarCaneGuiItem, 3, 1);
        pane.addItem(dandelionGuiItem, 2, 2);
        pane.addItem(sugarCaneGuiItem, 1, 3);
        pane.addItem(sugarCaneGuiItem, 3, 3);

        pane.addItem(resultsItem, 5, 2);

        pane.addItem(resultItem, 7, 2);

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
