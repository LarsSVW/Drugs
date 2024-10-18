package de.lars.drugs.crafting;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;

import java.util.ArrayList;
import java.util.List;
import static de.lars.drugs.handler.CreatedItems.*;

public class LongPapeCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public LongPapeCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "long_pape_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createLongPapes(plugin, config));

        recipe.shape("   ", "   ", "PGP");

        recipe.setIngredient('G', new RecipeChoice.ExactChoice(createGlue(plugin, config)));
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(getPapeItem(plugin, config)));

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createLongPapes(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.long_papes")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }

}
