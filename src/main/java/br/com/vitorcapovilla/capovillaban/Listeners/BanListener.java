package br.com.vitorcapovilla.capovillaban.Listeners;

import br.com.vitorcapovilla.capovillaban.CapovillaBan;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;

public class BanListener implements Listener {

    private CapovillaBan main;
    private final Map<Player, Player> targetMap;
    private final Map<String, String> banMessages;

    public BanListener(CapovillaBan main, Map<Player, Player> targetMap) {
        this.main = main;
        this.targetMap = targetMap;
        this.banMessages = new HashMap<>();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals("Ban Menu") && event.getCurrentItem() != null) {
            ItemStack clickedItem = event.getCurrentItem();
            int slot = event.getSlot();

            Set<Integer> unrestrictedSlots = new HashSet<>(Arrays.asList(10, 13, 16));

            // Recuperar o alvo do mapa
            Player target = targetMap.get(player);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Erro ao encontrar o jogador alvo.");
                return;
            }

            String banMessage = "";
            String kickMessage = "";
            switch (slot) {
                case 10: // Slot do item "Hack"
                    banMessage = "Uso de hack";
                    kickMessage = generateBanMessage("Uso de hack", player.getName());
                    break;
                case 13: // Slot do item "Bugs"
                    banMessage = "Abuso de bugs";
                    kickMessage = generateBanMessage("Abuso de bugs", player.getName());
                    break;
                case 16: // Slot do item "Clients"
                    banMessage = "Uso de clients não permitidos";
                    kickMessage = generateBanMessage("Uso de clients não permitidos", player.getName());
                    break;
                default:
                    return;
            }

            Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), banMessage, null, player.getName());
            player.sendMessage(ChatColor.RED + "Você baniu o jogador " + target.getName() + " por " + banMessage.toLowerCase() + "!");
            Bukkit.broadcastMessage(ChatColor.RED + "\n" + "Banimento:");
            Bukkit.broadcastMessage(ChatColor.RED + "Jogador " + target.getName() + " foi banido permanentemente!");
            Bukkit.broadcastMessage(ChatColor.RED + "Motivo: " + banMessage);
            Bukkit.broadcastMessage(ChatColor.RED + "Autor: " + player.getName());
            Bukkit.broadcastMessage("");

            // Armazenar a mensagem de banimento
            banMessages.put(target.getName(), kickMessage);

            // Expulsar o jogador com a mensagem personalizada
            target.kickPlayer(kickMessage);

            // Fechar o inventário depois de clicar
            player.closeInventory();
        }
    }

    private String generateBanMessage(String reason, String author) {
        return ChatColor.RED + "Você está permanentemente banido do servidor!\nMotivo: " + reason + "!\nAutor: " + author;
    }

    public Map<String, String> getBanMessages() {
        return banMessages;
    }
}
