package me.amitnave.compassbuy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends JavaPlugin {
    public static ItemStack compass = new ItemStack(Material.COMPASS);
    public static Economy economy;

    @Override
    public void onEnable() {
        this.getCommand("buycompass").setExecutor(new CompassHandler());
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName(ChatColor.AQUA + "Player Tracker");
        compassMeta.setLore(Collections.emptyList());
        compassMeta.getLore().add(ChatColor.GRAY + "Tracks the nearest player!");
        compass.setItemMeta(compassMeta);
        setupEconomy();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                CompassHandler.updatePlayers();
            }
        }, 0, 10);
    }
    @Override
    public void onDisable() {

    }
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
