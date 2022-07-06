package playercosmetic.playercosmetic;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import playercosmetic.playercosmetic.Commands.cosmetic;

import java.util.ArrayList;
import java.util.List;

public class util {

    public static PlayerCosmetic plugin;
    public util() {
        this.plugin = plugin;
    }
    public static void PlayerOnly() {
        Bukkit.getServer().getLogger().info("Only player can use this command");
    }

    public static String formatText(String text) {
        for (ChatColor style : ChatColor.values())
            try {
                int from = 0;
                while (text.indexOf("&#", from) >= 0) {
                    from = text.indexOf("&#", from) + 1;
                    text = text.replace(text.substring(from - 1, from + 7),
                            net.md_5.bungee.api.ChatColor.of(text.substring(from, from + 7)).toString());
                }
            } catch (Throwable t) {
            }
        return ChatColor.translateAlternateColorCodes('&', text).replace("&", "§");
    }

    public static void MsgAsPrefix(Player player, String text) {
        FileConfiguration message = plugin.messagefile.getConfig();
        String prefix = util.formatText(message.getString("Prefix"));
        text = prefix + util.formatText(text);
        player.sendMessage(text);
    }

    public static Inventory particleMenu() {
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration particlemenufile = plugin.particlemenufile.getConfig();
        Inventory inventory = Bukkit.createInventory(null, 4 * 9, util.formatText(message.getString("Title.Particle")));
        List<String> particle_list = new ArrayList<>(particlemenufile.getConfigurationSection("Particle.").getKeys(false));

        for (int i = 0; i < particle_list.size(); i++) {
            ItemStack item = new ItemStack(Material.GRASS_BLOCK);

            if (item != null) {
                if (particlemenufile.getString("Particle." + particle_list.get(i) + ".icon") != null) {
                    item = new ItemStack(Material.valueOf(particlemenufile.getString("Particle." + particle_list.get(i) + ".icon")));
                } else {
                    Bukkit.getLogger().info("PlayerCosmetic -> Missing item icon : " + particle_list.get(i));
                }

                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_DYE);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                String name = particlemenufile.getString("Particle." + particle_list.get(i) + ".name");
                if (name != null) {
                    meta.setDisplayName(util.formatText(particlemenufile.getString("Particle." + particle_list.get(i) + ".name")));
                } else {
                    Bukkit.getLogger().info("PlayerCosmetic -> Missing item name : " + particle_list.get(i));
                }

                ArrayList<String> lore = new ArrayList<>();
                for (int line = 0; line < particlemenufile.getStringList("Particle." + particle_list.get(i) + ".lore").size(); line++) {
                    lore.add(util.formatText(particlemenufile.getStringList("Particle." + particle_list.get(i) + ".lore").get(line)));
                }

                meta.setLore(lore);
                if (particlemenufile.getBoolean("Particle." + particle_list.get(i) + ".enchant") == true) {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                }

                item.setItemMeta(meta);
                inventory.addItem(item);
            }
        }
        inventory.setItem(27, cosmetic.back());
        inventory.setItem(35, cosmetic.stop());
        return inventory;
    }
}
