package playercosmetic.playercosmetic.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import playercosmetic.playercosmetic.PlayerCosmetic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class particletypefile {

    private PlayerCosmetic plugin;
    private FileConfiguration particletypeselectfile = null;
    private File configFile = null;


    public particletypefile(PlayerCosmetic plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "particletypeselect.yml");

        this.particletypeselectfile = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream a = this.plugin.getResource("particletypeselect.yml");
        if (a != null) {
            YamlConfiguration b = YamlConfiguration.loadConfiguration(new InputStreamReader(a));
            this.particletypeselectfile.setDefaults(b);
        }
    }

    public FileConfiguration getConfig() {
        if (this.particletypeselectfile == null)
            reloadConfig();
        return this.particletypeselectfile;
    }

    public void saveConfig() {
        if (this.particletypeselectfile == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "無法儲存資料夾" + this.getConfig(), e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "particletypeselect.yml");

        if (!this.configFile.exists()) {
            this.plugin.saveResource("particletypeselect.yml", false);
        }
    }
}