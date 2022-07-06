package playercosmetic.playercosmetic;

import org.bukkit.plugin.java.JavaPlugin;
import playercosmetic.playercosmetic.Commands.cosmetic;
import playercosmetic.playercosmetic.Event.InventoryClick;
import playercosmetic.playercosmetic.Event.PlayerQuit;
import playercosmetic.playercosmetic.File.mainmenufile;
import playercosmetic.playercosmetic.File.particlemenufile;
import playercosmetic.playercosmetic.File.message;

public final class PlayerCosmetic extends JavaPlugin {

    public static PlayerCosmetic plugin;
    public static message messagefile;
    public static particlemenufile particlemenufile;
    public static mainmenufile mainmenufile;

    @Override
    public void onEnable() {
        plugin = this;
        setupCommand();
        setupTabComplete();
        this.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.messagefile = new message(this);
        this.particlemenufile = new particlemenufile(this);
        this.mainmenufile = new mainmenufile(this);
    }

    @Override
    public void onDisable() {
        messagefile.saveConfig();
        particlemenufile.saveConfig();
        mainmenufile.saveConfig();
    }

    public void setupCommand() {
        getCommand("cosmetic").setExecutor(new cosmetic());
    }

    public void setupTabComplete() {
        getCommand("cosmetic").setTabCompleter(new cosmetic());
    }
}
