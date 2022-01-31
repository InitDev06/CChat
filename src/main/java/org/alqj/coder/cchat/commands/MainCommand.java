package org.alqj.coder.cchat.commands;

import org.alqj.coder.cchat.CChat;
import org.alqj.coder.cchat.color.Msg;
import org.alqj.coder.cchat.config.ConfigurationSettings;
import org.alqj.coder.cchat.xseries.XSound;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private final CChat cc;
    private Sound sound;
    private Sound sound1;
    private int volume;
    private int pitch;

    public MainCommand(CChat cc){
        this.cc = cc;
        Optional<XSound> xs = XSound.matchXSound(cc.getConfigSettings().getFile().getString("options.sounds.permission"));
        Optional<XSound> xs1 = XSound.matchXSound(cc.getConfigSettings().getFile().getString("options.sounds.reload"));
        if(xs.isPresent() || xs1.isPresent()){
            this.sound = xs.get().parseSound();
            this.sound1 = xs1.get().parseSound();
        } else {
            this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();
            this.sound1 = XSound.UI_BUTTON_CLICK.parseSound();
        }
        this.volume = cc.getConfigSettings().getFile().getInt("options.sounds.volume");
        this.pitch = cc.getConfigSettings().getFile().getInt("options.sounds.pitch");
    }

    private Sound getSound(){ return sound; }

    private Sound getSound1(){ return sound1; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String la, String[] args) {
        FileConfiguration configuration = cc.getConfigSettings().getFile();
        String prefix = configuration.getString("options.messages.prefix");
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(Msg.color(prefix + "&7 Running on version &b" + cc.getVersion() + "&7 by &a" + cc.getAuthor()));
                return true;
            }

            if(args[0].equalsIgnoreCase("commands")){
                if(player.hasPermission("cchat.cmd.commands")){
                    String cmds = configuration.getString("options.messages.list_cmds");
                    String[] list = cmds.split("\\n");
                    for(int i = 0 ; i < list.length ; i++){
                        String commands = list[i];
                        commands = Msg.color(commands);
                        player.sendMessage(commands);
                    }
                } else {
                    String message = configuration.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    if(configuration.getBoolean("options.sounds.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if(player.hasPermission("cchat.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    cc.getConfigSettings().reload();
                    String message = configuration.getString("options.messages.reload");
                    message = message.replace("<prefix>", prefix);
                    message = message.replace("<ms>", (System.currentTimeMillis() - RELOAD) + "");
                    if(configuration.getBoolean("options.sounds.reproduce")) execute1(player);
                    player.sendMessage(Msg.color(message));
                } else {
                    String message = configuration.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    if(configuration.getBoolean("options.sounds.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            String message = configuration.getString("options.messages.not_command");
            message = message.replace("<prefix>", prefix);
            player.sendMessage(Msg.color(message));
            return false;

        } else {
            if(args.length == 0){
                sender.sendMessage(Msg.color(prefix + "&7 Running on version &b" + cc.getVersion() + "&7 by &a" + cc.getAuthor()));
                return true;
            }

            if(args[0].equalsIgnoreCase("commands")){
                if(sender.hasPermission("cchat.cmd.commands")){
                    String cmds = configuration.getString("options.messages.list_cmds");
                    String[] list = cmds.split("\\n");
                    for(int i = 0 ; i < list.length ; i++){
                        String commands = list[i];
                        commands = Msg.color(commands);
                        sender.sendMessage(commands);
                    }
                } else {
                    String message = configuration.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    sender.sendMessage(Msg.color(message));
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if(sender.hasPermission("cchat.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    cc.getConfigSettings().reload();
                    String message = configuration.getString("options.messages.reload");
                    message = message.replace("<prefix>", prefix);
                    message = message.replace("<ms>", (System.currentTimeMillis() - RELOAD) + "");
                    sender.sendMessage(Msg.color(message));
                } else {
                    String message = configuration.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    sender.sendMessage(Msg.color(message));
                }
                return true;
            }

            String message = configuration.getString("options.messages.not_command");
            message = message.replace("<prefix>", prefix);
            sender.sendMessage(Msg.color(message));
            return false;
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }

    private void execute1(Player player){ player.playSound(player.getLocation(), getSound1(), getVolume(), getPitch()); }
}
