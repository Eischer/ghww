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

    private LineChartModel lineModel;

    @Inject
    SeiderlHistoryService seiderlHistoryService;

    @Inject
    TeamService teamService;

    @PostConstruct
    public void init() {
        if (!teamService.findAllteams().isEmpty()) {
            createLineModels();
        }
    }

    private void createLineModels() {
        lineModel = initLinearModel();
        lineModel.setTitle("Seiderl Wertung");
        lineModel.setLegendPosition("e");

        lineModel.getAxis(AxisType.X).setTickFormat("%.0f");

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(teamService.getMaxCountOfSeiderl()+1);
        yAxis.setTickFormat("%.0f");
        yAxis.setTickInterval("2");
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
    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
}
