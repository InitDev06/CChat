package org.alqj.coder.cchat.config;

import org.alqj.coder.cchat.CChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationSettings {

    private final CChat cc;

    private File file;
    private FileConfiguration config;

    public ConfigurationSettings(CChat cc) {
        this.cc = cc;
        createFile();
        config = loadFile();
    }

    private void createFile(){
        file = new File("plugins/CChat", "config.yml");
        if(!file.exists()) cc.saveResource("config.yml", false);
    }

    private FileConfiguration loadFile(){ return YamlConfiguration.loadConfiguration(file); }

    public void saveFile(){
        try{
            config.save(file);
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void reload(){ config = YamlConfiguration.loadConfiguration(file); }

    public FileConfiguration getFile(){ return config; }
}
