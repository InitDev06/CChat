package org.alqj.coder.cchat.util;

import org.alqj.coder.cchat.CChat;

import java.util.HashMap;

public class Cooldown {

    private final CChat cc;
    private final HashMap<String, Integer> cooldowns;

    public Cooldown(CChat cc){
        this.cc = cc;
        this.cooldowns = new HashMap<>();
    }

    public void setCooldown(final String uuid){ cooldowns.put(uuid, (int) System.currentTimeMillis() / 1000); }

    public boolean hasCooldown(final String uuid){
        if(!cooldowns.containsKey(uuid)) return false;

        if(cooldowns.containsKey(uuid)){
            if(((int) System.currentTimeMillis() / 1000) - cooldowns.get(uuid) < cc.getConfigSettings().getFile().getInt("options.cooldown_time")){
                return true;
            }
        }
        return false;
    }

    public void removeCooldown(final String uuid){ cooldowns.remove(uuid); }

    public Integer getCooldown(final String uuid){
        if(!cooldowns.containsKey(uuid)) return null;
        return cc.getConfigSettings().getFile().getInt("options.cooldown_time") - (((int) System.currentTimeMillis() / 1000) - cooldowns.get(uuid));
    }
}
