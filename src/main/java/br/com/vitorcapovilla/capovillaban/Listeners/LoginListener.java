package br.com.vitorcapovilla.capovillaban.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Map;

public class LoginListener implements Listener {

    private final Map<String, String> banMessages;

    public LoginListener(Map<String, String> banMessages) {
        this.banMessages = banMessages;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        if (event.getPlayer().isBanned() == true){
            if (banMessages.containsKey(playerName)) {
                String kickMessage = banMessages.get(playerName);
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, kickMessage);
            }
        }
    }
}
