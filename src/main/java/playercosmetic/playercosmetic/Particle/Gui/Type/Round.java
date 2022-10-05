package playercosmetic.playercosmetic.Particle.Gui.Type;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import playercosmetic.playercosmetic.Commands.cosmetic;
import playercosmetic.playercosmetic.GuiItemBuilder;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class Round {

    public static PlayerCosmetic plugin;

    public Round(){
        this.plugin = plugin;
    }

    public static Inventory round_type_gui() {
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration file = plugin.plugin.round_type_file.getConfig();
        Inventory inventory = Bukkit.createInventory(null, 4 * 9, util.formatText(message.getString("Title.Particle")));
        List<String> particle_list = new ArrayList<>(file.getConfigurationSection("Particle.").getKeys(false));

        for (int i = 0; i < particle_list.size(); i++) {
            ItemStack item = GuiItemBuilder.buildItem(file,
                    "Particle." + particle_list.get(i) + ".name",
                    "Particle." + particle_list.get(i) + ".icon",
                    "Particle." + particle_list.get(i) + ".lore",
                    "Particle." + particle_list.get(i) + ".enchant"
                    );
                inventory.addItem(item);
        }
        inventory.setItem(27, cosmetic.back());
        inventory.setItem(35, cosmetic.stop());
        return inventory;
    }
}
