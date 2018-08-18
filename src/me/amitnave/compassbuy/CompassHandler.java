package me.amitnave.compassbuy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CompassHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (!Main.economy.has(player, 7500)) {
                player.sendMessage(ChatColor.RED + "Insufficient funds");
            } else {
                Main.economy.withdrawPlayer(player, 7500);
                player.getInventory().addItem(Main.compass);
                player.sendMessage(ChatColor.GREEN + "Successfully bought 1 Player Tracker for $7500");

            }
        } else {
            sender.sendMessage(ChatColor.RED +"You cannot run this command in console.");
        }
        return true;
    }

    public static void updatePlayers() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getInventory().getItemInMainHand().equals(Main.compass)) {
                if(Bukkit.getOnlinePlayers().size() > 1) {
                    p.setCompassTarget(nearestPlayer(p).getLocation());
                }
            }
        }
    }

    public static Player nearestPlayer(Player player) {
        if(Bukkit.getOnlinePlayers().size() <= 1) {
            return null;
        }
            HashMap<Double, Player> playerDistances = new HashMap<Double, Player>();

Bukkit.getOnlinePlayers().stream()
        .filter(p -> p != player)
        .forEach(p -> playerDistances.put(p.getLocation().distance(player.getLocation()), p));
        List<Double> keysList = new ArrayList<Double>();
        for ( Map.Entry<Double, Player> entry : playerDistances.entrySet()) {
            keysList.add(entry.getKey());
        }
        Collections.sort(keysList);
    return playerDistances.get(keysList.get(0));
    }
}
