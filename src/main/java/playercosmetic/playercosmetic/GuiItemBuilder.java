package playercosmetic.playercosmetic;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiItemBuilder {

    public PlayerCosmetic plugin;
    public GuiItemBuilder() {
        this.plugin = plugin;
    }

    public static ItemStack buildItem(FileConfiguration file, String displayNamePath, String materialPath, String lorePath, String enchantEffect) {
        if (file == null) {
            return null;
        }

        ItemStack item = new ItemStack(Material.GRASS_BLOCK);

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        if (file.getString(materialPath) != null) {
            item = new ItemStack(Material.valueOf(file.getString(materialPath)));
        }

        if (file.getStringList(lorePath) != null) {
            List<String> lore = new ArrayList<String>();
            List<String> loreFromFile = file.getStringList(lorePath);
            for (int i = 0; i < loreFromFile.size(); i++) {
                lore.add(util.formatText(loreFromFile.get(i)));
            }
            meta.setLore(lore);
        }

        if (file.getBoolean(enchantEffect)) {
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        }

        if (displayNamePath != null) {
            meta.setDisplayName(util.formatText(file.getString(displayNamePath)));
        }

        item.setItemMeta(meta);

        return item;
    }
}
