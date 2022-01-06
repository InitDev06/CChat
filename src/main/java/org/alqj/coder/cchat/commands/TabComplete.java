package org.alqj.coder.cchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String la, String[] args) {
        if(args.length >= 1){
            if(cmd.getName().equalsIgnoreCase("cchat")){
                if(sender.hasPermission("cchat.cmd.list") && sender.hasPermission("cchat.cmd.reload")) return Arrays.asList("commands", "reload");
            }
        }
        return null;
    }
}
