package playercosmetic.playercosmetic.Event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import playercosmetic.playercosmetic.PlayerCosmetic;
import playercosmetic.playercosmetic.util;

public class Trail {

    public static PlayerCosmetic plugin;
    public Player player;
    public Trail(Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    private int TaskID;


    public void startParticle(String particle_type) {

        TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin.plugin, new Runnable() {

            double var = 0;
            Location location, first, second;
            ParticleData particle = new ParticleData(player.getUniqueId());

            @Override
            public void run() {
                if (!particle.hasID(player)) {
                    particle.SetID(TaskID);
                }

                var += Math.PI / 6;

                location = player.getLocation();
                first = location.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
                second = location.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));

                player.getWorld().spawnParticle(Particle.valueOf(particle_type), first, 0);
                player.getWorld().spawnParticle(Particle.valueOf(particle_type), second, 0);
            }
        }, 0, 1);
    }

    static int task;
    public static void testParticle(Player player, String particle_type) {
        Trail trail = new Trail(player);
        FileConfiguration messagefile = plugin.messagefile.getConfig();
        ParticleData particleData = new ParticleData(player.getUniqueId());
        trail.startParticle(particle_type);
        final int[] second = {plugin.messagefile.getConfig().getInt("Particle.examination-second")};
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin.plugin, new Runnable() {
            @Override
            public void run() {
                 player.sendTitle(util.formatText(messagefile.getString("Particle.examination-title")).replace("%second%", String.valueOf(second[0])), "", 20, 20, 20);
                 second[0] = second[0] - 1;
                 if (second[0] == 0 || second[0] < 0) {
                     if (particleData.hasID(player)) {
                         particleData.stopTask(particleData.getID(player));
                         particleData.removeID(player);
                         Bukkit.getScheduler().cancelTask(task);
                     }
                 }
            }
        }, 0, 20);
    }
}
