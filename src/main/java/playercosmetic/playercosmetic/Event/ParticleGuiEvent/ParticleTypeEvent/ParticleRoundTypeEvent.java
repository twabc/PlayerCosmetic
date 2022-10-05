package playercosmetic.playercosmetic.Event.ParticleGuiEvent.ParticleTypeEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import playercosmetic.playercosmetic.Commands.cosmetic;
import playercosmetic.playercosmetic.Particle.Gui.TypeSelectGui;
import playercosmetic.playercosmetic.ParticleData;
import playercosmetic.playercosmetic.Particle.Gui.ParticleSelectGui;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.TargetParticle;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class ParticleRoundTypeEvent implements Listener {

    public PlayerCosmetic plugin;
    public ParticleRoundTypeEvent() {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration file = plugin.round_type_file.getConfig();
        FileConfiguration particle_select_file = plugin.particlemenufile.getConfig();
        InventoryView inventory = player.getOpenInventory(); //get player open inventory
        ItemStack item = event.getCurrentItem(); //get click item

        List<String> particle_list = new ArrayList<>(file.getConfigurationSection("Particle.").getKeys(false));

        String item_name = "";
        if (item != null && item.getItemMeta() != null) {
            item_name = item.getItemMeta().getDisplayName().replace('§', '&');
        } else {
            return;
        }

        if (inventory.getTitle().equals(util.formatText(message.getString("Title.Particle")))) {
            event.setCancelled(true);
            ParticleData particleData = new ParticleData(player.getUniqueId());
            TargetParticle targetParticle = new TargetParticle(player.getUniqueId());
            if (event.isLeftClick()) {
                for (int i = 0; i < particle_list.size(); i++) {
                    String particle = particle_list.get(i);
                    if (item.equals(cosmetic.back())) {
                        player.openInventory(TypeSelectGui.ParticleTypeSelectGui());
                        return;
                    }
                    if (item.equals(cosmetic.stop())) {
                        if (particleData.hasID(player)) {
                            particleData.stopTask(particleData.getID(player));
                            particleData.removeID(player);
                            player.closeInventory();
                            return;
                        } else {
                            util.msgAsPrefix(player, message.getString("Particle.no-particle"));
                            player.closeInventory();
                            return;
                        }
                    }
                    if (file.getString("Particle." + particle + ".name").equalsIgnoreCase(item_name)) {
                        if (particleData.hasID(player)) {
                            particleData.stopTask(particleData.getID(player));
                            particleData.removeID(player);
                        }
                        Inventory select_inventory = ParticleSelectGui.ParticleSelectGui();
                        select_inventory.setItem(0, item);
                        targetParticle.removeTarget(player);
                        targetParticle.SetParticle(file);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.openInventory(select_inventory);
                            }
                        }.runTaskLater(plugin.plugin, 20);
                        return;
                    }
                }
            }
        } else {
            return;
        }
    }
}
