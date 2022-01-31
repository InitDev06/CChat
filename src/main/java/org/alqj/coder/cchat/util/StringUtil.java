package org.alqj.coder.cchat.util;

import org.alqj.coder.cchat.CChat;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final CChat cc = CChat.getPlugin(CChat.class);
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");
    private static char COLOR_CHAR = ChatColor.COLOR_CHAR;

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

    public static String toHexColors(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer(text.length() + 4 * 8);

        while(matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }
}
