package playercosmetic.playercosmetic;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TargetParticle {

        public final UUID uuid;
        public static Map<UUID, FileConfiguration> particle = new HashMap<UUID, FileConfiguration>();

        public TargetParticle(UUID uuid) {
            this.uuid = uuid;
        }

        public void SetParticle(FileConfiguration particle_file) {
            particle.put(uuid, particle_file);
        }

        public FileConfiguration getTargetFile(Player player) {
            return particle.get(player.getUniqueId());
        }

        public boolean hasParticle(Player player) {
            if (particle.containsKey(player.getUniqueId())) {
                return true;
            } else {
                return false;
            }
        }

        public void removeTarget(Player player) {
            if (hasParticle(player)) {
                particle.remove(player.getUniqueId());
            }
        }
}
