package playercosmetic.playercosmetic.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class MainGui {

    public static PlayerCosmetic plugin;

    public MainGui() {
        this.plugin = plugin;
    }

    public static ItemStack ParticleType() {
        FileConfiguration mainmenu = plugin.mainmenufile.getConfig();
        ItemStack particle_type = new ItemStack(Material.RED_DYE);
        List<String> lore_list = mainmenu.getStringList("Lore.particle");
        if (mainmenu.getString("Material.particle") != null) {
            particle_type = new ItemStack(Material.valueOf(mainmenu.getString("Material.particle")));
        }
        ItemMeta particle_meta = particle_type.getItemMeta();
        if (mainmenu.getString("Displayname.particle") != null) {
            particle_meta.setDisplayName(util.formatText(mainmenu.getString("Displayname.particle")));
        }

        particle_meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        particle_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        particle_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        particle_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        particle_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        particle_meta.addItemFlags(ItemFlag.HIDE_DYE);
        particle_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);

        ArrayList<String> lore = new ArrayList<>();
        for (int line = 0; line < lore_list.size(); line++) {
            lore.add(util.formatText(lore_list.get(line)));
        }
        particle_meta.setLore(lore);

        if (mainmenu.getString("Enchant.particle") != null) {
            if (mainmenu.getBoolean("Enchant.particle") == true) {
                particle_meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            }
        }
        particle_type.setItemMeta(particle_meta);
        return particle_type;
    }

    public static Inventory MainGui() {
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration particlemenufile = plugin.particlemenufile.getConfig();
        FileConfiguration mainmenu = plugin.mainmenufile.getConfig();
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, util.formatText(message.getString("Title.Main")));
        inventory.setItem(10, ParticleType());
        return inventory;
    }
}
