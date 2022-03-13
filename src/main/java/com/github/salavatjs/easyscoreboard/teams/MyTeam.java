package com.github.salavatjs.easyscoreboard.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTeam {

    private List<Player> players;

    private String name;

    private String prefix, suffix, displayName;

    private ChatColor color;

    private boolean canSeeFriendlyInvisibles, allowFriendlyFire;

    private Team.OptionStatus nametagVisible, deathMessageVisible, collidable;

    public MyTeam(String name) {
        this.name = name;
        players = new ArrayList<>();
        canSeeFriendlyInvisibles = false;
        allowFriendlyFire = false;
    }

    public void update() {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            Team team = getTeam(onlinePlayer);
            players.forEach(teamPlayer -> {
                if (!team.hasEntry(teamPlayer.getName())) team.addEntry(teamPlayer.getName());
            });
            if (prefix != null) team.setPrefix(prefix);
            if (suffix != null) team.setSuffix(suffix);
            if (color != null) team.setColor(color);
            if (displayName != null) team.setDisplayName(displayName);
            if (nametagVisible != null) team.setOption(Team.Option.NAME_TAG_VISIBILITY, nametagVisible);
            if (deathMessageVisible != null) team.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, deathMessageVisible);
            if (collidable != null) team.setOption(Team.Option.COLLISION_RULE, collidable);
            team.setAllowFriendlyFire(allowFriendlyFire);
            team.setCanSeeFriendlyInvisibles(canSeeFriendlyInvisibles);
        });
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) players.add(player);
    }

    public Team.OptionStatus getNametagVisible() {
        return nametagVisible;
    }

    public void setNametagVisible(Team.OptionStatus nametagVisible) {
        this.nametagVisible = nametagVisible;
    }

    public Team.OptionStatus getDeathMessageVisible() {
        return deathMessageVisible;
    }

    public void setDeathMessageVisible(Team.OptionStatus deathMessageVisible) {
        this.deathMessageVisible = deathMessageVisible;
    }

    public Team.OptionStatus getCollidable() {
        return collidable;
    }

    public void setCollidable(Team.OptionStatus collidable) {
        this.collidable = collidable;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isCanSeeFriendlyInvisibles() {
        return canSeeFriendlyInvisibles;
    }

    public void setCanSeeFriendlyInvisibles(boolean canSeeFriendlyInvisibles) {
        this.canSeeFriendlyInvisibles = canSeeFriendlyInvisibles;
    }

    public boolean isAllowFriendlyFire() {
        return allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public ChatColor getColor() {
        return color;
    }

    private Team getTeam(Player player) {
        Team team = player.getScoreboard().getTeam(name);
        if (team == null) team = player.getScoreboard().registerNewTeam(name);
        return team;
    }

}
