package playercosmetic.playercosmetic.Event.ParticleGuiEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import playercosmetic.playercosmetic.*;
import playercosmetic.playercosmetic.TargetParticle;
import playercosmetic.playercosmetic.ParticleData;
import playercosmetic.playercosmetic.Particle.PlayParticle.Trail;

import java.util.ArrayList;
import java.util.List;

public class ParticleSelect implements Listener {

    public PlayerCosmetic plugin;

    public ParticleSelect() {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration particletype = plugin.particlemenufile.getConfig();
        List<String> item_list = new ArrayList<>(particletype.getConfigurationSection("Item.").getKeys(false));

        InventoryView inventory = player.getOpenInventory();
        ItemStack item = event.getCurrentItem();

        String item_name = "";
        if (item != null && item.getItemMeta() != null) {
            item_name = item.getItemMeta().getDisplayName().replace('§', '&');
        } else {
            return;
        }

        if (inventory.getTitle().equals(util.formatText(message.getString("Title.Particle-select")))) {
            event.setCancelled(true);
            Trail trail = new Trail(player);
            TargetParticle targetParticle = new TargetParticle(player.getUniqueId());
            FileConfiguration file = targetParticle.getTargetFile(player);
            List<String> particle_list = new ArrayList<>(file.getConfigurationSection("Particle.").getKeys(false));
            for (int i = 0; i < particle_list.size(); i++) {
                String particle = particle_list.get(i);
                if (file.getString("Particle." + particle + ".name").equalsIgnoreCase(item_name)) {
                    ParticleData particleData = new ParticleData(player.getUniqueId());
                    if (particleData.hasID(player)) {
                        particleData.stopTask(particleData.getID(player));
                        particleData.removeID(player);
                    }
                    if (player.hasPermission(file.getString("Particle." + particle + ".permission"))) {
                        trail.startParticle(file.getString("Particle." + particle + ".particle-type"));
                        targetParticle.removeTarget(player);
                        player.closeInventory();
                        util.msgAsPrefix(player, message.getString("Particle.change-message").replace("%particle%", item_name));
                    } else {
                        util.msgAsPrefix(player, message.getString("Particle.no-permission").replace("%particle%", item_name));
                        targetParticle.removeTarget(player);
                        player.closeInventory();
                        return;
                    }
                    return;
                }
            }
            String target_item_name = " ";
            for (int amount = 0; amount < item_list.size(); amount++) {
                if (particletype.getString("Item." + item_list.get(amount) + ".name").equalsIgnoreCase(item_name)) {
                    target_item_name = item_list.get(amount);
                    break;
                }
            }
            if (particletype.getString("Item." + target_item_name + ".action").equalsIgnoreCase("test")) {
                for (int i = 0; i < particle_list.size(); i++) {
                    if (util.formatText(file.getString("Particle." + particle_list.get(i) + ".name")).equalsIgnoreCase(util.formatText(inventory.getItem(0).getItemMeta().getDisplayName()))) {
                        trail.testParticle(player, file.getString("Particle." + particle_list.get(i) + ".particle-type"));
                        targetParticle.removeTarget(player);
                        player.closeInventory();
                        return;
                    }
                }
            }
            if (particletype.getString("Item." + target_item_name + ".action").equalsIgnoreCase("back")) {
                player.chat("/cosmetic particle");
                targetParticle.removeTarget(player);
                return;
            }
        } else {
            return;
        }
    }
}
