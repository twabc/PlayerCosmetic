package playercosmetic.playercosmetic.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import playercosmetic.playercosmetic.PlayerCosmetic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Round_type_file {

    private PlayerCosmetic plugin;
    private FileConfiguration file = null;
    private File configFile = null;


    public Round_type_file(PlayerCosmetic plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "particle-round.yml");

        this.file = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream a = this.plugin.getResource("particle-round.yml");
        if (a != null) {
            YamlConfiguration b = YamlConfiguration.loadConfiguration(new InputStreamReader(a));
            this.file.setDefaults(b);
        }
    }

    public FileConfiguration getConfig() {
        if (this.file == null)
            reloadConfig();
        return this.file;
    }

    public void saveConfig() {
        if (this.file == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "無法儲存資料夾" + this.getConfig(), e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "particle-round.yml");

        if (!this.configFile.exists()) {
            this.plugin.saveResource("particle-round.yml", false);
        }
    }
}