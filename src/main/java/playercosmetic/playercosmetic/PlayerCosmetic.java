package playercosmetic.playercosmetic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import playercosmetic.playercosmetic.Commands.cosmetic;
import playercosmetic.playercosmetic.Event.CloseInventory;
import playercosmetic.playercosmetic.Event.MainGuiEvent;
import playercosmetic.playercosmetic.Event.ParticleGuiEvent.ParticleSelect;
import playercosmetic.playercosmetic.Event.ParticleGuiEvent.ParticleTypeEvent.ParticleRoundTypeEvent;
import playercosmetic.playercosmetic.Event.ParticleGuiEvent.ParticleTypeSelect;
import playercosmetic.playercosmetic.Event.PlayerQuit;
import playercosmetic.playercosmetic.Files.*;

public final class PlayerCosmetic extends JavaPlugin {

    public static PlayerCosmetic plugin;
    public static message messagefile;
    public static Round_type_file round_type_file;
    public static particlemenufile particlemenufile;
    public static mainmenufile mainmenufile;
    public static particletypefile particletypeselectfile;

    @Override
    public void onEnable() {
        plugin = this;
        setupCommand();
        setupTabComplete();

        //Regist MainGuiEvent InventoryClick event
        this.getServer().getPluginManager().registerEvents(new MainGuiEvent(), this);

        //Regist Particle Gui InventoryClick event
        this.getServer().getPluginManager().registerEvents(new ParticleRoundTypeEvent(), this);

        this.getServer().getPluginManager().registerEvents(new ParticleSelect(), this);
        this.getServer().getPluginManager().registerEvents(new ParticleTypeSelect(), this);

        //Regist PlayerQuit event
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        //Regist InventoryClose event
        this.getServer().getPluginManager().registerEvents(new CloseInventory(), this);

        //Setup files
        this.messagefile = new message(this);
        this.particlemenufile = new particlemenufile(this);
        this.mainmenufile = new mainmenufile(this);
        this.round_type_file = new Round_type_file(this);
        this.particletypeselectfile = new particletypefile(this);


        for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleData particleData = new ParticleData(player.getUniqueId());
            TargetParticle targetParticle = new TargetParticle(player.getUniqueId());
            particleData.removeID(player);
            targetParticle.removeTarget(player);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerCosmetic plugin disable");
        messagefile.saveConfig();
        particlemenufile.saveConfig();
        mainmenufile.saveConfig();
        round_type_file.saveConfig();
    }

    public void setupCommand() {
        getCommand("cosmetic").setExecutor(new cosmetic());
    }

    public void setupTabComplete() {
        getCommand("cosmetic").setTabCompleter(new cosmetic());
    }
}
