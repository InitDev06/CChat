package org.alqj.coder.cchat.listeners;

import org.alqj.coder.cchat.CChat;
import org.alqj.coder.cchat.color.Msg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeveloperJoin implements Listener {

    private final CChat cc;

    public DeveloperJoin(CChat cc){
        this.cc = cc;
    }

    @EventHandler
    public void onDeveloperJoin(PlayerJoinEvent ev){
        Player player = ev.getPlayer();

        if(player.getName().equals("iAlqjDV")){
            player.sendMessage("");
            player.sendMessage(Msg.color("&7 Welcome again developer! &a:D"));
            player.sendMessage(Msg.color("&7 This server is running the plugin &eCChat &6" + cc.getVersion()));
            player.sendMessage("");
        }
    }
}
