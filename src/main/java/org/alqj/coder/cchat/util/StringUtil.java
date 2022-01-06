package org.alqj.coder.cchat.util;

import org.alqj.coder.cchat.CChat;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    private static final CChat cc = CChat.getPlugin(CChat.class);

    public static String setPlaceholders(String text, Player player){
        if(player == null) return null;

        if(text.contains("<player>")) text = text.replace("<player>", player.getName());
        if(text.contains("<player_prefix>")) text = text.replace("<player_prefix>", cc.chat.getPlayerPrefix(player));
        if(text.contains("<player_suffix>")) text = text.replace("<player_suffix>", cc.chat.getPlayerSuffix(player));
        if(text.contains("<level>")) text = text.replace("<level>", player.getLevel() + "");
        if(text.contains("<world>")) text = text.replace("<world>", player.getWorld().getName());
        if(text.contains("<kills>")) text = text.replace("<kills>", Statistic.PLAYER_KILLS + "");
        if(text.contains("<exp>")) text = text.replace("<exp>", player.getTotalExperience() + "");

        return text;
    }
}
