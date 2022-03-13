package com.github.salavatjs.easyscoreboard.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.function.Function;

public class AutoFilledScoreboard extends GeneralScoreboard{

    private Function<Player, List<String>> lines;

    public AutoFilledScoreboard(Function<Player, String> displayName, Function<Player, List<String>> lines) {
        super(new HashMap<>(), displayName);
        this.lines = lines;
    }

    public AutoFilledScoreboard(Function<Player, String> displayName, Function<Player, List<String>> lines, boolean requireNewScoreboard) {
        super(new HashMap<>(), displayName, requireNewScoreboard);
        this.lines = lines;
    }

    public void update() {
        playersWithScoreboards.forEach(((player, scoreboard) -> {
            removeAllEntries(scoreboard);
            String displayName = this.displayName.apply(player);
            List<String> lines = this.lines.apply(player);
            Objective objective = scoreboard.getObjective(player.getName());
            if (objective == null) objective = scoreboard.registerNewObjective(player.getName(), "dummy", displayName);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            for (int i = 0; i < lines.size(); i++) {
                Score score = objective.getScore(lines.get(i));
                score.setScore(i);
            }
        }));
    }

    public Function<Player, List<String>> getLines() {
        return lines;
    }

    public void setLines(Function<Player, List<String>> lines) {
        this.lines = lines;
    }

}
