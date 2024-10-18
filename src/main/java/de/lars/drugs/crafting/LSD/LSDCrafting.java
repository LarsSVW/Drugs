package de.lars.drugs.crafting.LSD;

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

import static de.lars.drugs.handler.CreatedItems.*;

public class LSDCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public LSDCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "LSD_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createLSD(plugin, config));

        recipe.shape("P L", " N ", "L P");

        recipe.setIngredient('L', new RecipeChoice.ExactChoice(createLysergicAcid(plugin, config)));
        recipe.setIngredient('N', new RecipeChoice.ExactChoice(createNatrium(plugin, config)));
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(createpropanolamine(plugin, config)));

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createLSD(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.lsd")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
