package com.github.salavatjs.easyscoreboard.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;
import java.util.function.Function;

public abstract class GeneralScoreboard {

    protected Map<Player, Scoreboard> playersWithScoreboards;

    protected Function<Player, String> displayName;

    protected boolean requireNewScoreboard;

    protected GeneralScoreboard(Map<Player, Scoreboard> playersWithScoreboards, Function<Player, String> displayName) {
        this.playersWithScoreboards = playersWithScoreboards;
        this.displayName = displayName;
        requireNewScoreboard = true;
    }

    protected GeneralScoreboard(Map<Player, Scoreboard> playersWithScoreboards, Function<Player, String> displayName, boolean requireNewScoreboard) {
        this.playersWithScoreboards = playersWithScoreboards;
        this.displayName = displayName;
        this.requireNewScoreboard = requireNewScoreboard;
    }

    public abstract void update();

    public void addPlayer(Player player) {
        if (playersWithScoreboards.containsKey(player)) return;
        Scoreboard scoreboard;
        if (requireNewScoreboard == true) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }
        else scoreboard = player.getScoreboard();
        player.setScoreboard(scoreboard);
        playersWithScoreboards.put(player, scoreboard);
    }

    public void removePlayer(Player player) {
        playersWithScoreboards.remove(player);
    }

    protected void removeAllEntries(Scoreboard scoreboard) {
        scoreboard.getEntries().forEach(entry -> {
            scoreboard.resetScores(entry);
        });
    }

    public boolean isRequireNewScoreboard() {
        return requireNewScoreboard;
    }

    public void setRequireNewScoreboard(boolean requireNewScoreboard) {
        this.requireNewScoreboard = requireNewScoreboard;
    }

    public void removeAllPlayers() {
        playersWithScoreboards.clear();
    }

    public Map<Player, Scoreboard> getPlayersWithScoreboards() {
        return playersWithScoreboards;
    }

    public void setPlayersWithScoreboards(Map<Player, Scoreboard> playersWithScoreboards) {
        this.playersWithScoreboards = playersWithScoreboards;
    }

    public Function<Player, String> getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Function<Player, String> displayName) {
        this.displayName = displayName;
    }

}
