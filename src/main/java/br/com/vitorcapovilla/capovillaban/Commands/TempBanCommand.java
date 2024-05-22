package br.com.vitorcapovilla.capovillaban.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TempBanCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Map<Player, Player> targetMap = new HashMap<>();

    public TempBanCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Este comando só pode ser executado por jogadores.");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length == 0) {
            player.sendMessage("Uso incorreto! Use /tempban <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("O jogador especificado não está online ou não existe.");
            return true;
        }

        // Armazene o alvo no mapa
        targetMap.put(player, target);

        // Se chegou até aqui, o jogador e o alvo estão online e existem
        Inventory invTempBan = Bukkit.createInventory(player, 9 * 3, "TempBan Menu");

        ItemStack hack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta hackBan = hack.getItemMeta();
        hackBan.setDisplayName("Preconceito");
        hackBan.setLore(Arrays.asList("Banir temporariamente " + target.getName() + " por ser preconceituoso!"));
        hack.setItemMeta(hackBan);

        invTempBan.setItem(10, hack);

        ItemStack bugs = new ItemStack(Material.NAME_TAG);
        ItemMeta bugsBan = bugs.getItemMeta();
        bugsBan.setDisplayName("Divulgação de Servidores");
        bugsBan.setLore(Arrays.asList("Banir temporariamente " + target.getName() + " por divulgar de servidores!"));
        bugs.setItemMeta(bugsBan);

        invTempBan.setItem(13, bugs);

        ItemStack clients = new ItemStack(Material.COMPASS);
        ItemMeta clientBan = clients.getItemMeta();
        clientBan.setDisplayName("Discurso de Ódio");
        clientBan.setLore(Arrays.asList("Banir temporariamente " + target.getName() + " por discurso de ódio!"));
        clients.setItemMeta(clientBan);

        invTempBan.setItem(16, clients);

        player.openInventory(invTempBan);

        return true;
    }

    public Map<Player, Player> getTargetMap() {
        return targetMap;
    }

}
