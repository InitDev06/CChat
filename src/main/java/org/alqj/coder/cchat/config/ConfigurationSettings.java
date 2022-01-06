package org.alqj.coder.cchat.config;

import org.alqj.coder.cchat.CChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationSettings {

    private final CChat cc;
    private double configVersion = 0.1;
    private FileConfiguration configuration;
    private File cfile;

    public ConfigurationSettings(CChat cc){
        this.cc = cc;
        setup();
    }


    private void setup(){
        if(!cc.getDataFolder().exists()) cc.getDataFolder().mkdir();
        cfile = new File(cc.getDataFolder(), "config.yml");
        if(!cfile.exists()){
            new FileCopy().copy(cc.getResource("config.yml"), cfile);
        }
        configuration = new YamlConfiguration().loadConfiguration(cfile);
        if(getConfiguration().getDouble("options.config") != configVersion){
            cc.getLogger().severe("Your config file is outdated! Your version: " + getConfiguration().getDouble("options.config") + "required version: " + configVersion);
            cc.getServer().getPluginManager().disablePlugin(cc);
        }
    }

    public FileConfiguration getConfiguration(){ return configuration; }

    public void saveConfig(){
        try{
            configuration.save(cfile);
        } catch(IOException ex){
            ex.printStackTrace();;
        }
    }

    public void reloadConfig(){ configuration = YamlConfiguration.loadConfiguration(cfile); }
}
