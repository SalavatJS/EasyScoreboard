package com.github.salavatjs.easyscoreboard.scoreboards;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SelfFilledScoreboard extends GeneralScoreboard {

    private Function<Player, Map<String, Integer>> lines;

    public SelfFilledScoreboard(Function<Player, String> displayName, Function<Player, Map<String, Integer>> lines) {
        super(new HashMap<>(), displayName);
        this.lines = lines;
    }

    public SelfFilledScoreboard(Function<Player, String> displayName, Function<Player, Map<String, Integer>> lines, boolean requireNewScoreboard) {
        super(new HashMap<>(), displayName, requireNewScoreboard);
        this.lines = lines;
    }

    @Override
    public void update() {
        playersWithScoreboards.forEach(((player, scoreboard) -> {
            removeAllEntries(scoreboard);
            String displayName = this.displayName.apply(player);
            Map<String, Integer> lines = this.lines.apply(player);
            Objective objective = scoreboard.getObjective(player.getName());
            if (objective == null) objective = scoreboard.registerNewObjective(player.getName(), "dummy", displayName);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            Objective finalObjective = objective;
            lines.forEach((line, value) -> {
                Score score = finalObjective.getScore(line);
                score.setScore(value);
            });
        }));
    }

    public Function<Player, Map<String, Integer>> getLines() {
        return lines;
    }

    public void setLines(Function<Player, Map<String, Integer>> lines) {
        this.lines = lines;
    }

}
