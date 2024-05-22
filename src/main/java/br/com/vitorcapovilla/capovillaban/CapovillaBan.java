package br.com.vitorcapovilla.capovillaban;

import br.com.vitorcapovilla.capovillaban.Commands.BanCommand;
import br.com.vitorcapovilla.capovillaban.Commands.TempBanCommand;
import br.com.vitorcapovilla.capovillaban.Listeners.BanListener;
import br.com.vitorcapovilla.capovillaban.Listeners.LoginListener;
import br.com.vitorcapovilla.capovillaban.Listeners.TempBanListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CapovillaBan extends JavaPlugin {

    @Override
    public void onEnable() {
        BanCommand banCommand = new BanCommand(this);
        this.getCommand("ban").setExecutor(banCommand);

        TempBanCommand TempBanCommand = new TempBanCommand(this);
        this.getCommand("tempban").setExecutor(TempBanCommand);

        TempBanListener tempBanListener = new TempBanListener(this, TempBanCommand.getTargetMap());
        getServer().getPluginManager().registerEvents(tempBanListener, this);

        BanListener banListener = new BanListener(this, banCommand.getTargetMap());
        getServer().getPluginManager().registerEvents(banListener, this);

        LoginListener loginListener = new LoginListener(banListener.getBanMessages());
        getServer().getPluginManager().registerEvents(loginListener, this);
    }
}
