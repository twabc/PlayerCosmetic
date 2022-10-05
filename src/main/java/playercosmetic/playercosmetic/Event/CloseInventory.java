package playercosmetic.playercosmetic.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import playercosmetic.playercosmetic.GUI.MainGui;
import playercosmetic.playercosmetic.Particle.Gui.ParticleSelectGui;
import playercosmetic.playercosmetic.Particle.Gui.Type.Round;
import playercosmetic.playercosmetic.TargetParticle;
import playercosmetic.playercosmetic.Particle.Gui.TypeSelectGui;

public class CloseInventory implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        TargetParticle targetParticle = new TargetParticle(player.getUniqueId());
        if (inventory.equals(MainGui.MainGui()) || inventory.equals(Round.round_type_gui()) || inventory.equals(ParticleSelectGui.ParticleSelectGui())
                || inventory.equals(TypeSelectGui.ParticleTypeSelectGui())) {
            targetParticle.removeTarget(player);
            return;
        }
    }
}
