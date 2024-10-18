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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;

import java.util.ArrayList;
import java.util.List;

public class SafroleCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public SafroleCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "safrole_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, CreatedItems.createSafrole(plugin, config));

        recipe.shape("ES ", "ES ", "S  ");

        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('S', Material.SWEET_BERRIES);

        Bukkit.addRecipe(recipe);

        // Listener registrieren
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(CreatedItems.createSafrole(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.safrole")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }


}
