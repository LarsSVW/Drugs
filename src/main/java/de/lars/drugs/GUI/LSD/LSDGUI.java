package de.lars.drugs.GUI.LSD;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.lars.drugs.Drugs;
import de.lars.drugs.handler.CreatedItems;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.crafting.LSD.LysergicAcidCrafting;
import de.lars.drugs.crafting.LSD.NatriumCrafting;
import de.lars.drugs.crafting.LSD.PropanolamineCrafting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.lars.drugs.handler.CreatedItems.createLSD;
import static de.lars.drugs.handler.CreatedItems.*;

public class LSDGUI {
    private final Configuration config;
    private final Drugs plugin;

public LSDGUI(Drugs plugin, Configuration config) {
        this.config = config;
        this.plugin = plugin;
    }

    public void open(Player player) {
        ChestGui gui = new ChestGui(5, "LSD Crafting Recipe");

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

        ItemStack resultitem = createLSD(plugin, config);
        GuiItem resultItem = new GuiItem(resultitem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack lysergicAcidItem = new ItemStack(createLysergicAcid(plugin, config));
        GuiItem lysergicAcidGuiItem = new GuiItem(lysergicAcidItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack natriumItem = new ItemStack(createNatrium(plugin, config));
        GuiItem natriumGuiItem = new GuiItem(natriumItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        ItemStack propanolamineItem = new ItemStack(createpropanolamine(plugin, config));
        GuiItem propanolamineGuiItem = new GuiItem(propanolamineItem, event -> {
            event.getWhoClicked().sendMessage("You can't take it!");
            event.setCancelled(true);
        });

        pane.addItem(propanolamineGuiItem, 1, 1);
        pane.addItem(lysergicAcidGuiItem, 3, 1);
        pane.addItem(natriumGuiItem, 2, 2);
        pane.addItem(propanolamineGuiItem, 3, 3);
        pane.addItem(lysergicAcidGuiItem, 1, 3);

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
