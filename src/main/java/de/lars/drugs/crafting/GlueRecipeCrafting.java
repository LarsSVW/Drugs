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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;

import java.util.ArrayList;
import java.util.List;
import static de.lars.drugs.handler.CreatedItems.*;


public class GlueRecipeCrafting implements Listener {

    private final Drugs plugin;
    private final Configuration config;

    public GlueRecipeCrafting(Drugs plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(plugin, "glue_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, createGlue(plugin, config));

        recipe.shape("ABC", "DEF", "GHI");

        setIngredientIfNotAir(recipe, 'A', config.getString("glue_material_a", "AIR"));
        setIngredientIfNotAir(recipe, 'B', config.getString("glue_material_b", "AIR"));
        setIngredientIfNotAir(recipe, 'C', config.getString("glue_material_c", "AIR"));
        setIngredientIfNotAir(recipe, 'D', config.getString("glue_material_d", "AIR"));
        setIngredientIfNotAir(recipe, 'E', config.getString("glue_material_e", "AIR"));
        setIngredientIfNotAir(recipe, 'F', config.getString("glue_material_f", "AIR"));
        setIngredientIfNotAir(recipe, 'G', config.getString("glue_material_g", "AIR"));
        setIngredientIfNotAir(recipe, 'H', config.getString("glue_material_h", "AIR"));
        setIngredientIfNotAir(recipe, 'I', config.getString("glue_material_i", "AIR"));

        Bukkit.addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private void setIngredientIfNotAir(ShapedRecipe recipe, char key, String materialName) {
        Material material = Material.getMaterial(materialName);
        if (material != null && material != Material.AIR) {
            recipe.setIngredient(key, material);
        }
    }


    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result != null && result.isSimilar(createGlue(plugin, config))) {
            Permissible player = (Permissible) event.getView().getPlayer();

            if (!player.hasPermission("drugs.craft.glue")) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }

    private static Material getMaterialFromConfig(Configuration config, String key) {
        String materialName = config.getString(key, "AIR");
        Material material = Material.getMaterial(materialName);
        return material != null ? material : Material.AIR;
    }
}
