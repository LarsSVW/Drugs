package de.lars.drugs.GUI.ecstasy;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.handler.CreatedItems;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.crafting.Ecstasy.IsoSafroleCrafting;
import de.lars.drugs.crafting.Ecstasy.PiperonalCrafting;
import de.lars.drugs.crafting.Ecstasy.SafroleCrafting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.lars.drugs.handler.CreatedItems.*;

public class EcstasyGUI {
    private final Configuration config;
    private final Drugs plugin;

    public EcstasyGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "Ecstasy Crafting Recipe");

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

        ItemStack resultitem = CreatedItems.createXTC(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack safroleItem = createSafrole(plugin, config);
        GuiItem safroleGuiItem = new GuiItem(safroleItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack piperonalItem = createpiperonal(plugin, config);
        GuiItem piperonalGuiItem = new GuiItem(piperonalItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack isoSafroleItem = createIsoSafrole(plugin, config);
        GuiItem isoSafroleGuiItem = new GuiItem(isoSafroleItem, event -> {
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

        pane.addItem(isoSafroleGuiItem, 1, 1);
        pane.addItem(piperonalGuiItem, 1, 2);
        pane.addItem(isoSafroleGuiItem, 1, 3);
        pane.addItem(piperonalGuiItem, 2, 1);
        pane.addItem(piperonalGuiItem, 2, 2);
        pane.addItem(isoSafroleGuiItem, 2, 3);
        pane.addItem(safroleGuiItem, 3, 1);
        pane.addItem(safroleGuiItem, 3,2);
        pane.addItem(safroleGuiItem, 3,3);

        pane.addItem(resultsItem, 5, 2);

        pane.addItem(resultItem, 7, 2);

        gui.addPane(pane);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        gui.show(player);
    }
}
