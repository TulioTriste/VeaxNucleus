package dev.veax.nucleus.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.*;

public class CooldownManager {

    private Table<String, UUID, Long> cooldowns;

    public CooldownManager() {
        this.cooldowns = HashBasedTable.create();
    }

    public void setCooldown(String cooldown, UUID uuid, long time) {
        if (time < 1L) {
            this.cooldowns.remove(cooldown, uuid);
        }
        else {
            this.cooldowns.put(cooldown, uuid, time);
        }
    }

    public long getCooldown(String cooldown, UUID uuid) {
        return this.cooldowns.get(cooldown, uuid) != null ? this.cooldowns.get(cooldown, uuid) : 0;
    }

    public void clearCooldowns() {
        this.cooldowns.clear();
    }
}
