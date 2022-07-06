package playercosmetic.playercosmetic.Event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import playercosmetic.playercosmetic.Commands.cosmetic;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class InventoryClick implements Listener {

    public PlayerCosmetic plugin;
    public InventoryClick() {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration particlemenufile = plugin.particlemenufile.getConfig();
        InventoryView inventory = player.getOpenInventory();
        ItemStack item = event.getCurrentItem();
        List<String> particle_list = new ArrayList<>(particlemenufile.getConfigurationSection("Particle.").getKeys(false));
        List<String> item_list = new ArrayList<>(particlemenufile.getConfigurationSection("Item.").getKeys(false));
        String item_name = "";
        if (item != null && item.getItemMeta() != null) {
            item_name = item.getItemMeta().getDisplayName().replace('§', '&');
        } else {
            return;
        }
        if (inventory.getTitle().equals(util.formatText(message.getString("Title.Particle")))) {
            event.setCancelled(true);
            int second = 0;
            ParticleData particleData = new ParticleData(player.getUniqueId());
            if (event.isLeftClick()) {
                 for (int i = 0; i < particle_list.size(); i++) {
                    String particle = particle_list.get(i);
                    if (item.equals(cosmetic.back())) {
                            player.chat("/cosmetic gui");
                            return;
                    }
                    if (item.equals(cosmetic.stop())) {
                        if (particleData.hasID(player)) {
                            particleData.stopTask(particleData.getID(player));
                            particleData.removeID(player);
                            player.closeInventory();
                            return;
                        } else {
                            util.MsgAsPrefix(player, message.getString("Particle.no-particle"));
                            return;
                        }
                    }
                     if (particlemenufile.getString("Particle." + particle + ".name").equalsIgnoreCase(item_name)) {
                         if (particleData.hasID(player)) {
                            particleData.stopTask(particleData.getID(player));
                            particleData.removeID(player);
                         }
                         Inventory inventory2 = Bukkit.createInventory(null, 9 * 4, util.formatText(message.getString("Title.Particle-select")));
                         inventory2.setItem(0, item);
                         for (int amount = 0; amount < item_list.size(); amount++) {
                             ItemStack itemStack = new ItemStack(Material.HOPPER);
                             if (particlemenufile.getString("Item." + item_list.get(amount) + ".icon") != null) {
                                 itemStack = new ItemStack(Material.valueOf(particlemenufile.getString("Item." + item_list.get(amount) + ".icon")));
                             } else {
                                 Bukkit.getLogger().info("PlayerCosmetic -> Missing item icon : " + item_list.get(amount));
                             }

                             ItemMeta meta = itemStack.getItemMeta();

                             meta.addItemFlags(ItemFlag.HIDE_DYE);
                             meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                             meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                             meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                             meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                             meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                             String name = particlemenufile.getString("Item." + item_list.get(amount) + ".name");
                             if (name != null) {
                                 meta.setDisplayName(util.formatText(particlemenufile.getString("Item." + item_list.get(amount) + ".name")));
                             } else {
                                 Bukkit.getLogger().info("PlayerCosmetic -> Missing item name : " + item_list.get(amount));
                             }

                             ArrayList<String> lore = new ArrayList<>();
                             for (int line = 0; line < particlemenufile.getStringList("Item." + item_list.get(amount) + ".lore").size(); line++) {
                                 lore.add(util.formatText(particlemenufile.getStringList("Item." + item_list.get(amount) + ".lore").get(line)));
                             }
                             meta.setLore(lore);

                             if (particlemenufile.getBoolean("Item." + item_list.get(amount) + ".enchant") == true) {
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                             }

                             itemStack.setItemMeta(meta);
                             inventory2.setItem(particlemenufile.getInt("Item." + item_list.get(amount) + ".slot"), itemStack);
                         }
                        player.openInventory(inventory2);
                        return;
                     }
                 }
            }
        }
        if (inventory.getTitle().equalsIgnoreCase(util.formatText(message.getString("Title.Main")))) {
            event.setCancelled(true);
            if (item != null) {
                if (item.getItemMeta().equals(cosmetic.ParticleType().getItemMeta())) {
                    player.chat("/cosmetic particle");
                }
            }
            return;
        }
        if (inventory.getTitle().equals(util.formatText(message.getString("Title.Particle-select")))) {
            event.setCancelled(true);
            Trail trail = new Trail(player);
            for (int i = 0; i < particle_list.size(); i++) {
                String particle = particle_list.get(i);
                if (particlemenufile.getString("Particle." + particle + ".name").equalsIgnoreCase(item_name)) {
                    ParticleData particleData = new ParticleData(player.getUniqueId());
                    if (particleData.hasID(player)) {
                        particleData.stopTask(particleData.getID(player));
                        particleData.removeID(player);
                    }
                    if (player.hasPermission(particlemenufile.getString("Particle." + particle + ".permission"))) {
                        trail.startParticle(particlemenufile.getString("Particle." + particle + ".particle-type"));
                        player.closeInventory();
                        util.MsgAsPrefix(player, message.getString("Particle.change-particle").replace("%particle%", item_name));
                    } else {
                        util.MsgAsPrefix(player, message.getString("Particle.no-permission").replace("%particle%", item_name));
                        player.closeInventory();
                        return;
                    }
                    return;
                }
            }
            String target_item_name = " ";
            for (int amount = 0; amount < item_list.size(); amount++) {
                if (particlemenufile.getString("Item." + item_list.get(amount) + ".name").equalsIgnoreCase(item_name)) {
                    target_item_name = item_list.get(amount);
                }
            }
            if (particlemenufile.getString("Item." + target_item_name + ".action").equalsIgnoreCase("test")) {
                for (int i = 0; i < particle_list.size(); i++) {
                    if (util.formatText(particlemenufile.getString("Particle." + particle_list.get(i) + ".name")).equalsIgnoreCase(util.formatText(inventory.getItem(0).getItemMeta().getDisplayName()))) {
                        trail.testParticle(player, particlemenufile.getString("Particle." + particle_list.get(i) + ".particle-type"));
                        player.closeInventory();
                        return;
                    }
                }
            }
            if (particlemenufile.getString("Item." + target_item_name + ".action").equalsIgnoreCase("back")) {
                player.chat("/cosmetic particle");
                return;
            }
        }
    }

}
