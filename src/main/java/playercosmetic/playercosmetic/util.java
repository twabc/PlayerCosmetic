package playercosmetic.playercosmetic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import playercosmetic.playercosmetic.Commands.cosmetic;

public class util {

    public static PlayerCosmetic plugin;
    public util() {
        this.plugin = plugin;
    }
    public static void playerOnly() {
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

    public static void msgAsPrefix(Player player, String text) {
        FileConfiguration message = plugin.messagefile.getConfig();
        String prefix = util.formatText(message.getString("Prefix"));
        text = prefix + util.formatText(text);
        player.sendMessage(text);
    }

    public static void stopParticle(Player player, ItemStack item) {
        ParticleData particleData = new ParticleData(player.getUniqueId());
        FileConfiguration message_file = plugin.plugin.messagefile.getConfig();
        if (item.equals(cosmetic.stop())) {
            if (particleData.hasID(player)) {
                particleData.stopTask(particleData.getID(player));
                particleData.removeID(player);
                player.closeInventory();
                return;
            } else {
                util.msgAsPrefix(player, message_file.getString("Particle.no-particle"));
                player.closeInventory();
                return;
            }
        }
    }
}
