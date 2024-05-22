package br.com.vitorcapovilla.capovillaban;

import org.bukkit.plugin.java.JavaPlugin;

public class CapovillaBan extends JavaPlugin {

    @Override
    public void onEnable() {
        BanCommand banCommand = new BanCommand(this);
        this.getCommand("ban").setExecutor(banCommand);

        BanListener banListener = new BanListener(this, banCommand.getTargetMap());
        getServer().getPluginManager().registerEvents(banListener, this);

        LoginListener loginListener = new LoginListener(banListener.getBanMessages());
        getServer().getPluginManager().registerEvents(loginListener, this);
    }
}
