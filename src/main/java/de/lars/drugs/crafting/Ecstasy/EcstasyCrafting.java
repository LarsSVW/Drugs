package de.lars.drugs.crafting.Ecstasy;

import de.lars.drugs.Drugs;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.handler.CreatedItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permissible;

import static de.lars.drugs.handler.CreatedItems.createXTC;


public class EcstasyCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public EcstasyCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes(Configuration config) {
        NamespacedKey key = new NamespacedKey(plugin, "Ecstasy_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createXTC(plugin, config));

        recipe.shape("IPS", "PPS", "IIS");

        recipe.setIngredient('S', new RecipeChoice.ExactChoice(CreatedItems.createSafrole(plugin, config)));
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(CreatedItems.createpiperonal(plugin, config)));
        recipe.setIngredient('I', new RecipeChoice.ExactChoice(CreatedItems.createIsoSafrole(plugin, config)));

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createXTC(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.xtc")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }


}