package playercosmetic.playercosmetic.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import playercosmetic.playercosmetic.ParticleData;
import playercosmetic.playercosmetic.TargetParticle;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ParticleData particleData = new ParticleData(player.getUniqueId());
        TargetParticle targetParticle = new TargetParticle(player.getUniqueId());
        if (particleData.hasID(player)) {
            particleData.stopTask(particleData.getID(player));
            particleData.removeID(player);
        }
        targetParticle.removeTarget(player);
    }
}
