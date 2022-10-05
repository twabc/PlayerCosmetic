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

public class TypeSelectGui {

    public static PlayerCosmetic plugin;
    public TypeSelectGui() {
        this.plugin = plugin;
    }

    public static Inventory ParticleTypeSelectGui() {
        FileConfiguration messageFile = plugin.plugin.messagefile.getConfig();
        FileConfiguration particleTypeSelectFile = plugin.plugin.particletypeselectfile.getConfig();
        List<String> particle_select_list = new ArrayList<>(particleTypeSelectFile.getConfigurationSection("Particle-type.").getKeys(false));
        Inventory inventory = Bukkit.createInventory(null, 4 * 9 , util.formatText(messageFile.getString("Title.Particle-select-type")));

        for (int amount = 0; amount < particle_select_list.size(); amount ++) {
            ItemStack item = GuiItemBuilder.buildItem(particleTypeSelectFile,
                    "Particle-type." + particle_select_list.get(amount) + ".name",
                    "Particle-type." + particle_select_list.get(amount) + ".icon",
                    "Particle-type." + particle_select_list.get(amount) + ".lore",
                    "Particle-type." + particle_select_list.get(amount) + ".enchant");
            inventory.setItem(particleTypeSelectFile.getInt("Particle-type." + particle_select_list.get(amount) + ".slot"), item);
        }

        return inventory;
    }
}
