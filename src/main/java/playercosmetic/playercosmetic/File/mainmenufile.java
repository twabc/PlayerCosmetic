package playercosmetic.playercosmetic.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import playercosmetic.playercosmetic.PlayerCosmetic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class mainmenufile {

    private PlayerCosmetic plugin;
    private FileConfiguration mainmenufile = null;
    private File configFile = null;


    public mainmenufile(PlayerCosmetic plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "mainmenu.yml");

        this.mainmenufile = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream a = this.plugin.getResource("particlemenu.yml");
        if (a != null) {
            YamlConfiguration b = YamlConfiguration.loadConfiguration(new InputStreamReader(a));
            this.mainmenufile.setDefaults(b);
        }
    }

    public FileConfiguration getConfig() {
        if (this.mainmenufile == null)
            reloadConfig();
        return this.mainmenufile;
    }

    public void saveConfig() {
        if (this.mainmenufile == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "無法儲存資料夾" + this.getConfig(), e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "mainmenu.yml");

        if (!this.configFile.exists()) {
            this.plugin.saveResource("mainmenu.yml", false);
        }
    }
}