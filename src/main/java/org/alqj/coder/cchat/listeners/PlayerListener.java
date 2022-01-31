package org.alqj.coder.cchat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.alqj.coder.cchat.CChat;
import org.alqj.coder.cchat.color.Msg;
import org.alqj.coder.cchat.config.ConfigurationSettings;
import org.alqj.coder.cchat.util.StringUtil;
import org.alqj.coder.cchat.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

public class PlayerListener implements Listener {

    private final CChat cc;
    private Sound sound;
    private int volume;
    private int pitch;

    public PlayerListener(CChat cc){
        this.cc = cc;
        Optional<XSound> xs = XSound.matchXSound(cc.getConfigSettings().getFile().getString("options.sounds.cooldown"));
        if(xs.isPresent()) this.sound = xs.get().parseSound();
        else this.sound = XSound.BLOCK_NOTE_BLOCK_PLING.parseSound();

        this.volume = cc.getConfigSettings().getFile().getInt("options.sounds.volume");
        this.pitch = cc.getConfigSettings().getFile().getInt("options.sounds.pitch");
    }

    private Sound getSound(){ return sound; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @EventHandler
    public void onGroupMessage(AsyncPlayerChatEvent ev){
        FileConfiguration configuration = cc.getConfigSettings().getFile();
        String prefix = configuration.getString("options.messages.prefix");
        Player player = ev.getPlayer();

        if(player.hasPermission("cchat.bypass.cooldown")){
            String group = cc.permission.getPrimaryGroup(player);
            if(player.hasPermission("cchat.color")) ev.setMessage(Msg.color(ev.getMessage()));
            String format;
            if(configuration.getConfigurationSection("options.chat.groups." + group) != null){
                format = configuration.getString("options.chat.groups." + group + ".format");
            } else {
                format = configuration.getString("options.chat.default_format");
            }

            format = StringUtil.setPlaceholders(format, player);
            format = PlaceholderAPI.setPlaceholders(player, format);
            format = format.replace("<message>", ev.getMessage());
            format = Msg.color(format);
            if(Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18")) StringUtil.toHexColors(format);

            String mode = configuration.getString("options.chat.click.mode");

            String cmd_action = configuration.getString("options.chat.click.actions.command");
            String suggest_action = configuration.getString("options.chat.click.actions.suggest");
            String url_action = configuration.getString("options.chat.click.actions.url");

            TextComponent component = new TextComponent(format);
            if(mode.equals("COMMAND")) component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd_action));
            if(mode.equals("SUGGEST")) component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest_action));
            if(mode.equals("URL")) component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url_action));
            String hover;
            if(configuration.getConfigurationSection("options.chat." + group + ".hover") != null){
                hover = configuration.getString("options.chat." + group + ".hover");
                return;
            }
            hover = configuration.getString("options.chat.default_hover");

            assert hover != null;
            hover = PlaceholderAPI.setPlaceholders(player, hover);
            hover = StringUtil.setPlaceholders(hover, player);
            hover = Msg.color(hover);
            if(Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18")) StringUtil.toHexColors(hover);
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));

            ev.setFormat(null);
            for(Player connected : Bukkit.getOnlinePlayers()){
                connected.spigot().sendMessage(component);
            }
            return;
        } else {
            if(cc.getCooldowns().hasCooldown(player.getUniqueId().toString())){
                ev.setCancelled(true);
                String message = configuration.getString("options.messages.cooldown_activated");
                message = message.replace("<prefix>", prefix);
                message = message.replace("<time>", cc.getCooldowns().getCooldown(player.getUniqueId().toString()) + "");
                if(configuration.getBoolean("options.sounds.reproduce")) execute(player);
                player.sendMessage(Msg.color(message));
                return;
            }
            cc.getCooldowns().setCooldown(player.getUniqueId().toString());
            String group = cc.permission.getPrimaryGroup(player);
            if(player.hasPermission("cchat.color")) ev.setMessage(Msg.color(ev.getMessage()));
            String format;
            if(configuration.getConfigurationSection("options.chat.groups." + group) != null){
                format = configuration.getString("options.chat.groups." + group + ".format");
            } else {
                format = configuration.getString("options.chat.default_format");
            }

            format = StringUtil.setPlaceholders(format, player);
            format = PlaceholderAPI.setPlaceholders(player, format);
            format = format.replace("<message>", ev.getMessage());
            format = Msg.color(format);
            if(Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18")) StringUtil.toHexColors(format);

            String mode = configuration.getString("options.chat.click.mode");

            String cmd_action = configuration.getString("options.chat.click.actions.command");
            String suggest_action = configuration.getString("options.chat.click.actions.suggest");
            String url_action = configuration.getString("options.chat.click.actions.url");

            TextComponent component = new TextComponent(format);
            if(mode.equals("COMMAND")) component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd_action));
            if(mode.equals("SUGGEST")) component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest_action));
            if(mode.equals("URL")) component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url_action));
            String hover;
            if(configuration.getConfigurationSection("options.chat." + group + ".hover") != null){
                hover = configuration.getString("options.chat." + group + ".hover");
                return;
            }
            hover = configuration.getString("options.chat.default_hover");

            assert hover != null;
            hover = PlaceholderAPI.setPlaceholders(player, hover);
            hover = StringUtil.setPlaceholders(hover, player);
            hover = Msg.color(hover);
            if(Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18")) StringUtil.toHexColors(hover);
            hover = configuration.getString("options.chat.default_hover");
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));

            ev.setFormat(null);
            for(Player connected : Bukkit.getOnlinePlayers()){
                connected.spigot().sendMessage(component);
            }
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }
}
