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

import java.text.SimpleDateFormat;
import java.util.*;

public class TempBanListener implements Listener {

    private final CapovillaBan main;
    private final Map<Player, Player> targetMap;
    private final Map<String, String> tempBanMessages;

    public TempBanListener(CapovillaBan main, Map<Player, Player> targetMap) {
        this.main = main;
        this.targetMap = targetMap;
        this.tempBanMessages = new HashMap<>();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals("TempBan Menu") && event.getCurrentItem() != null) {
            ItemStack clickedItem = event.getCurrentItem();
            int slot = event.getSlot();

            Set<Integer> unrestrictedSlots = new HashSet<>(Arrays.asList(10, 13, 16));

            // Recuperar o alvo do mapa
            Player target = targetMap.get(player);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Erro ao encontrar o jogador alvo.");
                return;
            }

            String TempBanMessage;
            String kickMessage;
            Date TimeTempBan;

            Date now = new Date();
            long duasHoras = 2 * 60 * 60 * 1000; // 2 horas em milissegundos
            long vinteEQuatroHoras = 24 * 60 * 60 * 1000; // 24 horas em milissegundos

            switch (slot) {
                case 10: // Slot do item "Hack"
                    TempBanMessage = "Preconceito";
                    TimeTempBan = new Date(now.getTime() + duasHoras);
                    kickMessage = generateBanMessage("Preconceito", TimeTempBan, player.getName());
                    break;
                case 13: // Slot do item "Bugs"
                    TempBanMessage = "Divulgação de Servidores";
                    TimeTempBan = new Date(now.getTime() + duasHoras);
                    kickMessage = generateBanMessage("Divulgação de Servidores", TimeTempBan, player.getName());
                    break;
                case 16: // Slot do item "Clients"
                    TempBanMessage = "Discurso de ódio";
                    TimeTempBan = new Date(now.getTime() + vinteEQuatroHoras);
                    kickMessage = generateBanMessage("Discurso de Ódio", TimeTempBan, player.getName());
                    break;
                default:
                    return;
            }

            Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), TempBanMessage, TimeTempBan, player.getName());
            player.sendMessage(ChatColor.RED + "Você baniu temporariamente o jogador " + target.getName() + " por " + TempBanMessage.toLowerCase() + "!");
            Bukkit.broadcastMessage(ChatColor.RED + "\n" + "Banimento:");
            Bukkit.broadcastMessage(ChatColor.RED + "Jogador " + target.getName() + " foi banido temporariamente!");
            Bukkit.broadcastMessage(ChatColor.RED + "Motivo: " + TempBanMessage);
            Bukkit.broadcastMessage(ChatColor.RED + "Tempo: " + formatarData(TimeTempBan)); // Formatação da data
            Bukkit.broadcastMessage(ChatColor.RED + "Autor: " + player.getName());
            Bukkit.broadcastMessage("");

            // Armazenar a mensagem de banimento
            tempBanMessages.put(target.getName(), kickMessage);

            // Expulsar o jogador com a mensagem personalizada
            target.kickPlayer(kickMessage);

            // Fechar o inventário depois de clicar
            player.closeInventory();
        }
    }

    // Método para formatar a data
    private String formatarData(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(data);
    }

    private String generateBanMessage(String reason, Date TimeTempBan, String author) {
        return ChatColor.RED + "Você está temporariamente banido do servidor!\nMotivo: " + reason + "!\nTempo: " + formatarData(TimeTempBan) + "\nAutor: " + author;
    }

    public Map<String, String> getBanMessages() {
        return tempBanMessages;
    }
}
