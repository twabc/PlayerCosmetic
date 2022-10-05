package playercosmetic.playercosmetic.Event.ParticleGuiEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import playercosmetic.playercosmetic.Particle.Gui.Type.Round;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class ParticleTypeSelect implements Listener {

    public PlayerCosmetic plugin;
    public ParticleTypeSelect() {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventory = player.getOpenInventory();
        ItemStack item = event.getCurrentItem();
        FileConfiguration messageFile = plugin.messagefile.getConfig();
        FileConfiguration particleType = plugin.particletypeselectfile.getConfig();
        List<String> itemList = new ArrayList<>(particleType.getConfigurationSection("Particle-type.").getKeys(false));

        String itemName = "";
        if (item != null && item.getItemMeta() != null) {
            itemName = item.getItemMeta().getDisplayName().replace('§', '&');
        } else {
            return;
        }

        if (inventory.getTitle().equals(util.formatText(messageFile.getString("Title.Particle-select-type")))) {
            event.setCancelled(true);
            for (int i = 0; i < itemList.size(); i++) {
                if (particleType.getString("Particle-type." + itemList.get(i) + ".name").equalsIgnoreCase(itemName)) {
                    if (itemList.get(i).equalsIgnoreCase("round")) {
                        player.openInventory(Round.round_type_gui());
                        return;
                    }
                }
            }
        }
    }
}
