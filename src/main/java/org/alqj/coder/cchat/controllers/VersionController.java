package org.alqj.coder.cchat.controllers;

import org.alqj.coder.cchat.CChat;
import org.alqj.coder.cchat.color.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class VersionController {

    private final CChat cc;
    private final ConsoleCommandSender log;

    public VersionController(CChat cc){
        this.cc = cc;
        this.log = Bukkit.getConsoleSender();
        setupVersion();
    }

    private void setupVersion(){
        try{
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            switch(version){
                case "v1_8_R1":
                case "v1_8_R2":
                    log.sendMessage(Msg.color("&c You're using a older release from 1.8, please update to the release &e1.8_R3&c."));
                    Bukkit.getPluginManager().disablePlugin(cc);
                    return;
                case "v1_8_R3":
                    log.sendMessage(Msg.color("&6 Detected &e1.8_R3 &6release"));
                    return;
                case "v1_9_R1":
                    log.sendMessage(Msg.color("&c You're using a older release from 1.9, please update to the release &e1.9_R2&c."));
                    Bukkit.getPluginManager().disablePlugin(cc);
                    return;
                case "v1_9_R2":
                    log.sendMessage(Msg.color("&6 Detected &e1.9_R2 &6release"));
                    return;
                case "v1_10_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.10_R1 &6release"));
                    return;
                case "v1_11_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.11_R1 &6release"));
                    return;
                case "v1_12_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.12_R1 &6release"));
                    return;
                case "v1_13_R1":
                    log.sendMessage(Msg.color("&c You're using a older release from 1.13, please update to the release &e1.13_R2&c."));
                    Bukkit.getPluginManager().disablePlugin(cc);
                    return;
                case "v1_13_R2":
                    log.sendMessage(Msg.color("&6 Detected &e1.13_R2 &6release"));
                    return;
                case "v1_14_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.14_R1 &6release"));
                    return;
                case "v1_15_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.15_R1 &6release"));
                    return;
                case "v1_16_R1":
                case "v1_16_R2":
                    log.sendMessage(Msg.color("&c You're using a older release from 1.16, please update to the release &e1.16_R3&c."));
                    Bukkit.getPluginManager().disablePlugin(cc);
                    return;
                case "v1_16_R3":
                    log.sendMessage(Msg.color("&6 Detected &e1.16_R3 &6release"));
                    return;
                case "v1_17_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.17_R1 &6release"));
                    return;
                case "v1_18_R1":
                    log.sendMessage(Msg.color("&6 Detected &e1.16_R1 &6release"));
                    return;
            }
            log.sendMessage(Msg.color("&c Your server version is not compatible with this plugin..."));
            Bukkit.getPluginManager().disablePlugin(cc);
        } catch(ArrayIndexOutOfBoundsException ex){
            log.sendMessage(Msg.color("&c An occurred a error to check your server version."));
            Bukkit.getPluginManager().disablePlugin(cc);
        }
    }
}
