package br.com.vitorcapovilla.capovillaban;

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

public class BanCommand implements CommandExecutor {

    private final Plugin plugin;
    private final Map<Player, Player> targetMap = new HashMap<>();

    public BanCommand(Plugin plugin) {
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
            player.sendMessage("Uso incorreto! Use /ban <player>");
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
        Inventory invBan = Bukkit.createInventory(player, 9 * 3, "Ban Menu");

        ItemStack hack = new ItemStack(Material.SKELETON_SKULL);
        ItemMeta hackBan = hack.getItemMeta();
        hackBan.setDisplayName("Hack");
        hackBan.setLore(Arrays.asList("Banir " + target.getName() + " por uso de hack!"));
        hack.setItemMeta(hackBan);

        invBan.setItem(10, hack);

        ItemStack bugs = new ItemStack(Material.DIAMOND);
        ItemMeta bugsBan = bugs.getItemMeta();
        bugsBan.setDisplayName("Bugs");
        bugsBan.setLore(Arrays.asList("Banir " + target.getName() + " por abuso de bugs!"));
        bugs.setItemMeta(bugsBan);

        invBan.setItem(13, bugs);

        ItemStack clients = new ItemStack(Material.COMPASS);
        ItemMeta clientBan = clients.getItemMeta();
        clientBan.setDisplayName("Clients");
        clientBan.setLore(Arrays.asList("Banir " + target.getName() + " por uso de clients não permitidos!"));
        clients.setItemMeta(clientBan);

        invBan.setItem(16, clients);

        player.openInventory(invBan);

        return true;
    }

    public Map<Player, Player> getTargetMap() {
        return targetMap;
    }
}