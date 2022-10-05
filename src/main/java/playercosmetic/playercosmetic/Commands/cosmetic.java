package playercosmetic.playercosmetic.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import playercosmetic.playercosmetic.GUI.MainGui;
import playercosmetic.playercosmetic.Particle.Gui.Type.Round;
import playercosmetic.playercosmetic.Particle.Gui.TypeSelectGui;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

import java.util.ArrayList;
import java.util.List;

public class cosmetic implements CommandExecutor, TabCompleter {

    public static PlayerCosmetic plugin;
    public cosmetic() {
        this.plugin = plugin;
    }

    public static ItemStack back() {
        FileConfiguration particlemenu = plugin.particlemenufile.getConfig();
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();;
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        if (particlemenu.getString("Item.backtoMainGui.icon") != null) {
            item = new ItemStack(Material.valueOf(particlemenu.getString("Item.backtoMainGui.icon")));
        }
        if (particlemenu.getString("Item.backtoMainGui.name") != null) {
            meta.setDisplayName(util.formatText(particlemenu.getString("Item.backtoMainGui.name")));
        }
        if (particlemenu.getString("Item.backtoMainGui.enchant") != null) {
            meta.addEnchant(Enchantment.DIG_SPEED, 1 , true);
        }
        ArrayList<String> lore = new ArrayList<>();
        List<String> lore_list = particlemenu.getStringList("Item.backtoMainGui.lore");
        for (int line = 0; line < lore_list.size(); line++) {
            lore.add(util.formatText(lore_list.get(line)));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack stop() {
        FileConfiguration particlemenu = plugin.particlemenufile.getConfig();
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();;
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        if (particlemenu.getString("Item.stop.icon") != null) {
            item = new ItemStack(Material.valueOf(particlemenu.getString("Item.stop.icon")));
        }
        if (particlemenu.getString("Item.stop.name") != null) {
            meta.setDisplayName(util.formatText(particlemenu.getString("Item.stop.name")));
        }
        if (particlemenu.getString("Item.stop.enchant") != null) {
            meta.addEnchant(Enchantment.DIG_SPEED, 1 , true);
        }
        ArrayList<String> lore = new ArrayList<>();
        List<String> lore_list = particlemenu.getStringList("Item.stop.lore");
        for (int line = 0; line < lore_list.size(); line++) {
            lore.add(util.formatText(lore_list.get(line)));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            util.playerOnly();
            return true;
        }

        plugin.particlemenufile.reloadConfig();
        Player player = (Player)sender;
        FileConfiguration message = plugin.messagefile.getConfig();

        if (label.equals("cosmetic")) {
            if (args.length == 0) {
                return true;
            }
            if (args[0].equalsIgnoreCase("gui")) {
               player.openInventory(MainGui.MainGui());
               return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission(message.getString("Reload-file.permission"))) {
                    plugin.mainmenufile.reloadConfig();
                    plugin.mainmenufile.saveConfig();
                    plugin.messagefile.reloadConfig();
                    plugin.messagefile.saveConfig();
                    plugin.particlemenufile.reloadConfig();
                    plugin.particlemenufile.saveConfig();
                    plugin.round_type_file.reloadConfig();
                    plugin.round_type_file.saveConfig();
                    util.msgAsPrefix(player, util.formatText(message.getString("Reload-file.message")));
                    return true;
                } else {
                    util.msgAsPrefix(player, util.formatText(message.getString("Reload-file.no-permission")));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("particle")) {
                player.openInventory(TypeSelectGui.ParticleTypeSelectGui());
                return true;
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<String>();
            arguments.add("gui");
            arguments.add("particle");
            arguments.add("reload");
            return arguments;
        }
        return null;
    }
}

