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


public class CigaretteCrafting implements Listener {
    private final Drugs plugin;
    private final Configuration config;

    public CigaretteCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "cigarette_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createcigarette(plugin, config));
        recipe.shape("PPP", "PWP", "PPP");

        recipe.setIngredient('P', new RecipeChoice.ExactChoice(getPapeItem(plugin, config)));
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(getTobaccoItem(plugin)));

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createcigarette(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.cigarette")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
