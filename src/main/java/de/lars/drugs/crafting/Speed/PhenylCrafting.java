package de.lars.drugs.crafting.Speed;

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

public class PhenylCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public PhenylCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "phenyl_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createPhenyl(plugin, config));

        recipe.shape("S S", " R ", "S S");

        recipe.setIngredient('S', Material.SUGAR_CANE);
        recipe.setIngredient('R', Material.ROSE_BUSH);

        Bukkit.addRecipe(recipe);

        // Listener registrieren
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createPhenyl(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.phenyl")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
