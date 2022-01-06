package org.alqj.coder.cchat.commands;

import org.alqj.coder.cchat.CChat;
import org.alqj.coder.cchat.color.Msg;
import org.alqj.coder.cchat.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ClearChat implements CommandExecutor {

    private final CChat cc;
    private final FileConfiguration configuration;
    private Sound sound;
    private int volume;
    private int pitch;

    public ClearChat(CChat cc){
        this.cc = cc;
        this.configuration = cc.cSettings.getConfiguration();
        Optional<XSound> xs = XSound.matchXSound(configuration.getString("options.sounds.permission"));
        if(xs.isPresent()) this.sound = xs.get().parseSound();
        else this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();

        this.volume = configuration.getInt("options.sounds.volume");
        this.pitch = configuration.getInt("options.sounds.pitch");
    }

    private Sound getSound(){ return sound; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String la, String[] args) {
        String prefix = configuration.getString("options.messages.prefix");
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("cchat.cmd.clear")){
                for(Player connected : Bukkit.getOnlinePlayers()){
                    if(connected.hasPermission("cchat.bypass.cooldown")){
                        String message = configuration.getString("options.messages.clear_bypass");
                        message = message.replace("<prefix>", prefix);
                        connected.sendMessage(Msg.color(message));
                        return true;
                    } else {
                        for(int i = 0 ; i < 250 ; i++){
                            connected.sendMessage("");
                        }
                        String message = configuration.getString("options.messages.chat_cleared");
                        message = message.replace("<player>", player.getName());
                        message = message.replace("<prefix>", prefix);
                        connected.sendMessage(Msg.color(message));
                    }
                }
                return true;
            } else {
                String message = configuration.getString("options.messages.not_permission");
                message = message.replace("<prefix>", prefix);
                if(configuration.getBoolean("options.sounds.reproduce")) execute(player);
                player.sendMessage(Msg.color(message));
                return false;
            }
        } else {
            String message = configuration.getString("options.messages.not_console");
            message = message.replace("<prefix>", prefix);
            sender.sendMessage(Msg.color(message));
            return false;
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }
}
