package de.lars.drugs.crafting.Heroin;

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
import static de.lars.drugs.handler.CreatedItems.*;

import java.util.ArrayList;
import java.util.List;

import static de.lars.drugs.handler.CreatedItems.createHeroin;

public class HeroinCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public HeroinCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "heroin_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createHeroin(plugin, config));

        recipe.shape("   ", " S ", " H ");

        recipe.setIngredient('S', new RecipeChoice.ExactChoice(createSyringe(plugin, config)));
        recipe.setIngredient('H', new RecipeChoice.ExactChoice(createLiquid(plugin, config)));

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createHeroin(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.heroin")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }

}
