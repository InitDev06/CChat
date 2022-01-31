package org.alqj.coder.cchat;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.alqj.coder.cchat.color.Msg;
import org.alqj.coder.cchat.commands.ClearChat;
import org.alqj.coder.cchat.commands.MainCommand;
import org.alqj.coder.cchat.commands.TabComplete;
import org.alqj.coder.cchat.config.ConfigurationSettings;
import org.alqj.coder.cchat.controllers.ListenerController;
import org.alqj.coder.cchat.controllers.VersionController;
import org.alqj.coder.cchat.util.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CChat extends JavaPlugin {

    PluginDescriptionFile pdffile = getDescription();
    private final ConsoleCommandSender log = Bukkit.getConsoleSender();
    private ListenerController lc;
    private VersionController vc;
    private Cooldown cooldown;
    private ConfigurationSettings configurationSettings;

    public Chat chat = null;
    public Permission permission = null;

    public String getAuthor(){
        String AUTHOR = "iAlqjDV";
        return AUTHOR;
    }

    public String getVersion() {
        String VERSION = pdffile.getVersion();
        return VERSION;
    }

    public PluginDescriptionFile getPDFFile(){ return pdffile; }

    @Override
    public void onEnable() {
        long START = System.currentTimeMillis();

        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&e      CChat"));
        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&f Author: &b" + getAuthor()));
        log.sendMessage(Msg.color("&f  Version: &b" + getVersion()));
        log.sendMessage(Msg.color(""));

        try{
            Class.forName("org.spigotmc.SpigotConfig");
        } catch(ClassNotFoundException ex){
            log.sendMessage(Msg.color("&c Could not found a Spigot jar, please install a Spigot jar and restart the server."));
            log.sendMessage(Msg.color("&c The plugin will be deactivated now..."));
            Bukkit.getPluginManager().disablePlugin(this);
        }

        configurationSettings = new ConfigurationSettings(this);
        setupCommands();
        lc = new ListenerController(this);
        cooldown = new Cooldown(this);
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        setupChatService();
        setupPermissionService();
        log.sendMessage(Msg.color("&a Vault hooked correctly"));

        vc = new VersionController(this);

        log.sendMessage(Msg.color("&a Enabled in &6" + (System.currentTimeMillis() - START) + "ms&a."));
        log.sendMessage(Msg.color(""));
    }

    @Override
    public void onDisable(){}

    public Cooldown getCooldowns(){ return cooldown; }

    public ListenerController getListenerController(){ return lc; }
    public ConfigurationSettings getConfigSettings() { return configurationSettings; }

    private void setupCommands(){
        getCommand("cchat").setExecutor(new MainCommand(this));
        getCommand("clearchat").setExecutor(new ClearChat(this));

        getCommand("cchat").setTabCompleter(new TabComplete());
    }

    private boolean setupChatService(){
        RegisteredServiceProvider<Chat> rspChat = getServer().getServicesManager().getRegistration(Chat.class);
        if(rspChat == null) return false;

        chat = rspChat.getProvider();
        return chat != null;
    }

    private boolean setupPermissionService(){
        RegisteredServiceProvider<Permission> rspPermission = getServer().getServicesManager().getRegistration(Permission.class);
        if(rspPermission == null) return false;

        permission = rspPermission.getProvider();
        return permission != null;
    }
}
