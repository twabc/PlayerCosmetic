package playercosmetic.playercosmetic.Particle.Gui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import playercosmetic.playercosmetic.GuiItemBuilder;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class ParticleSelectGui {

    public static PlayerCosmetic plugin;

    public ParticleSelectGui() {
        this.plugin = plugin;
    }

    public static Inventory ParticleSelectGui() {
        FileConfiguration message = plugin.plugin.messagefile.getConfig();
        FileConfiguration particle_select_file = plugin.particlemenufile.getConfig();
        List<String> particle_select_list = new ArrayList<>(particle_select_file.getConfigurationSection("Item.").getKeys(false));

        Inventory inventory = Bukkit.createInventory(null, 9 * 4, util.formatText(message.getString("Title.Particle-select")));
        for (int amount = 0; amount < particle_select_list.size(); amount++) {
            ItemStack item = GuiItemBuilder.buildItem(particle_select_file,
                    "Item." + particle_select_list.get(amount) + ".name",
                    "Item." + particle_select_list.get(amount) + ".icon",
                    "Item." + particle_select_list.get(amount) + ".lore",
                    "Item." + particle_select_list.get(amount) + ".enchant");
            inventory.setItem(particle_select_file.getInt("Item." + particle_select_list.get(amount) + ".slot"), item);
           }
        return inventory;
    }
}
