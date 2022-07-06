package playercosmetic.playercosmetic.Event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleData {

    public final UUID uuid;
    public static Map<UUID, Integer> particle = new HashMap<UUID, Integer>();

    public ParticleData(UUID uuid) {
        this.uuid = uuid;
    }

    public void SetID(int id) {
        particle.put(uuid, id);
    }

    public int getID(Player player) {
        return particle.get(player.getUniqueId());
    }

    public boolean hasID(Player player) {
        if (particle.containsKey(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    public void removeID(Player player) {
        particle.remove(player.getUniqueId());
    }

    public void stopTask(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }
}
