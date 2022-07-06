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
import org.jetbrains.annotations.NotNull;
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
        if (particlemenu.getString("Special.backtoMainGui.icon") != null) {
            item = new ItemStack(Material.valueOf(particlemenu.getString("Special.backtoMainGui.icon")));
        }
        if (particlemenu.getString("Special.backtoMainGui.name") != null) {
            meta.setDisplayName(util.formatText(particlemenu.getString("Special.backtoMainGui.name")));
        }
        if (particlemenu.getString("Special.backtoMainGui.enchant") != null) {
            meta.addEnchant(Enchantment.DIG_SPEED, 1 , true);
        }
        ArrayList<String> lore = new ArrayList<>();
        List<String> lore_list = particlemenu.getStringList("Special.backtoMainGui.lore");
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
        if (particlemenu.getString("Special.stop.icon") != null) {
            item = new ItemStack(Material.valueOf(particlemenu.getString("Special.stop.icon")));
        }
        if (particlemenu.getString("Special.stop.name") != null) {
            meta.setDisplayName(util.formatText(particlemenu.getString("Special.stop.name")));
        }
        if (particlemenu.getString("Special.stop.enchant") != null) {
            meta.addEnchant(Enchantment.DIG_SPEED, 1 , true);
        }
        ArrayList<String> lore = new ArrayList<>();
        List<String> lore_list = particlemenu.getStringList("Special.stop.lore");
        for (int line = 0; line < lore_list.size(); line++) {
            lore.add(util.formatText(lore_list.get(line)));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack ParticleType() {
        FileConfiguration mainmenu = plugin.mainmenufile.getConfig();
        ItemStack particle_type = new ItemStack(Material.RED_DYE);
        List<String> lore_list = mainmenu.getStringList("Lore.particle");
        if (mainmenu.getString("Material.particle") != null) {
            particle_type = new ItemStack(Material.valueOf(mainmenu.getString("Material.particle")));
        }
        ItemMeta particle_meta = particle_type.getItemMeta();
        if (mainmenu.getString("Displayname.particle") != null) {
            particle_meta.setDisplayName(util.formatText(mainmenu.getString("Displayname.particle")));
        }

        particle_meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        particle_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        particle_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        particle_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        particle_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        particle_meta.addItemFlags(ItemFlag.HIDE_DYE);
        particle_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);

        ArrayList<String> lore = new ArrayList<>();
        for (int line = 0; line < lore_list.size(); line++) {
            lore.add(util.formatText(lore_list.get(line)));
        }
        particle_meta.setLore(lore);

        if (mainmenu.getString("Enchant.particle") != null) {
            if (mainmenu.getBoolean("Enchant.particle") == true) {
                particle_meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            }
        }
        particle_type.setItemMeta(particle_meta);
        return particle_type;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            util.PlayerOnly();
            return true;
        }

        plugin.particlemenufile.reloadConfig();
        Player player = (Player)sender;
        FileConfiguration message = plugin.messagefile.getConfig();
        FileConfiguration particlemenufile = plugin.particlemenufile.getConfig();
        FileConfiguration mainmenu = plugin.mainmenufile.getConfig();
        Inventory inventory2 = Bukkit.createInventory(null, 3 * 9,util.formatText(message.getString("Title.Main")));

        if (label.equals("cosmetic")) {
            if (args.length == 0) {
                return true;
            }
            if (args[0].equalsIgnoreCase("gui")) {
               inventory2.setItem(10, ParticleType());
               player.openInventory(inventory2);
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
                    util.MsgAsPrefix(player, util.formatText(message.getString("Reload-file.message")));
                    return true;
                } else {
                    util.MsgAsPrefix(player, util.formatText(message.getString("Reload-file.no-permission")));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("particle")) {
                player.openInventory(util.particleMenu());
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

