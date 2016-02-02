package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.Constants;

public class LeagueTournamentStatisticsTable extends Table implements ResultTable {

    private NextRoundTable nextRoundTable;
    private LastRoundTable lastRoundTable;

    private Table leagueTournamentStatisticsTable = new Table();

    public LeagueTournamentStatisticsTable(NextRoundTable nextRoundTable, LastRoundTable lastRoundTable) {
        this.nextRoundTable = nextRoundTable;
        this.lastRoundTable = lastRoundTable;

        leagueTournamentStatisticsTable.add(nextRoundTable.getTable()).row();
        leagueTournamentStatisticsTable.add(lastRoundTable.getTable()).pad(Constants.UI_ELEMENTS_INDENT);
    }

    @Override
    public Group getTable() {
        return leagueTournamentStatisticsTable;
    }

    @Override
    public void highlightTeam(Team team) {
        nextRoundTable.highlightTeam(team);
        lastRoundTable.highlightTeam(team);
    }
}