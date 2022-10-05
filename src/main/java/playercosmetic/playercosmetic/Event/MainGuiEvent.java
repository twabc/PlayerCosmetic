package playercosmetic.playercosmetic.Event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import playercosmetic.playercosmetic.Particle.Gui.TypeSelectGui;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

public class MainGuiEvent implements Listener {

    public PlayerCosmetic plugin;

    public MainGuiEvent() {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        FileConfiguration message = plugin.plugin.messagefile.getConfig();
        InventoryView inventory = player.getOpenInventory();
        ItemStack item = event.getCurrentItem();

        if (inventory.getTitle().equals(util.formatText(message.getString("Title.Main")))) {
            event.setCancelled(true);
            if (item != null) {
                if (item.getItemMeta().equals(playercosmetic.playercosmetic.GUI.MainGui.ParticleType().getItemMeta())) {
                    player.openInventory(TypeSelectGui.ParticleTypeSelectGui());
                    return;
                }
            }
            return;
        } else {
            return;
        }
    }
}
