package de.lars.drugs.listener.resourcepack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcePackListener implements Listener {

    private final JavaPlugin plugin;

    public ResourcePackListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Hole den Resource-Pack-Link und den Hash aus der Config
        String resourcePackUrl = plugin.getConfig().getString("resource_pack_url");
        String resourcePackHashString = plugin.getConfig().getString("resource_pack_hash");

        try {
            // Umwandlung des Hash-Strings in ein byte[]
            byte[] resourcePackHash = fromHexString(resourcePackHashString);
            player.setResourcePack(resourcePackUrl, resourcePackHash);
        } catch (IllegalArgumentException e) {
            player.sendMessage("Das Resource-Pack konnte nicht geladen werden (falscher Hash).");
        }

        player.sendMessage("Hey! In order to use a plugin provided by this Server, please use the resourcepack! If you already got it, you can ignore this message!");
    }

    // Funktion zum Konvertieren des Hex-Strings in byte[]
    private byte[] fromHexString(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Ungültige Länge des Hash-Strings.");
        }
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return bytes;
    }

    // Event-Handler für den Status des Resource-Packs
    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        PlayerResourcePackStatusEvent.Status status = event.getStatus();

        switch (status) {
            case SUCCESSFULLY_LOADED:
                player.sendMessage("Resource-Pack wurde erfolgreich geladen!");
                break;
            case DECLINED:
                player.sendMessage("Du hast das Resource-Pack abgelehnt. Bestimmte Funktionen werden möglicherweise nicht korrekt angezeigt.");
                break;
            case FAILED_DOWNLOAD:
                player.sendMessage("Fehler beim Herunterladen des Resource-Packs. Bitte versuche es erneut.");
                break;
            case ACCEPTED:
                player.sendMessage("Das Resource-Pack wird heruntergeladen...");
                break;
            default:
                break;
        }
    }
}
