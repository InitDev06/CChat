package org.alqj.coder.cchat.controllers;

import org.alqj.coder.cchat.CChat;
import org.alqj.coder.cchat.listeners.DeveloperJoin;
import org.alqj.coder.cchat.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerController {

    private final CChat cc;

    private PlayerListener PLAYER;
    private DeveloperJoin DEVELOPER;

    public ListenerController(CChat cc){
        this.cc = cc;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(PLAYER = new PlayerListener(cc), cc);
        pm.registerEvents(DEVELOPER = new DeveloperJoin(cc), cc);
    }
}
