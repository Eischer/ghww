package at.eischer.view;

import at.eischer.model.SeiderlHistory;
import at.eischer.model.Team;
import at.eischer.services.SeiderlHistoryService;
import at.eischer.services.TeamService;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ChartView {

    private LineChartModel lineModel1;

    @Inject
    SeiderlHistoryService seiderlHistoryService;

    @Inject
    TeamService teamService;

    @PostConstruct
    public void init() {
        createLineModels();
    }

    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Seiderl Wertung");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(teamService.getMaxCountOfSeiderl()+1);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        for (Team team : teamService.findAllteams()) {
            LineChartSeries seiderlSeries = new LineChartSeries();
            seiderlSeries.setLabel(team.getName());
            List<SeiderlHistory> historyPerTeam = seiderlHistoryService.getSeiderHistoryByTeam(team);
            int counter = 0;
            if (!historyPerTeam.isEmpty()) {
                for (SeiderlHistory historyEntry : historyPerTeam) {
                    seiderlSeries.set(counter, historyEntry.getSeiderlCounter());
                    counter++;
                }
            }
            // Now also add the current value to the series
            seiderlSeries.set(counter, team.getSeiderlCounter());
            model.addSeries(seiderlSeries);
        }
        return model;
    }

    /**
     * GETTER and SETTERS
     */
    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }
}
