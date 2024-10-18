package de.lars.drugs.crafting.Ecstasy;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
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

import static de.lars.drugs.handler.CreatedItems.createpiperonal;

public class PiperonalCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public PiperonalCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "piperonal_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createpiperonal(plugin, config));

        recipe.shape("DS ", "DS ", "D  ");

        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.SUGAR);

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createpiperonal(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.piperonal")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }

}
