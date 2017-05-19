package at.eischer.view;

import at.eischer.model.SeiderlHistory;
import at.eischer.model.Team;
import at.eischer.services.SeiderlHistoryService;
import at.eischer.services.TeamService;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        lineModel = initLinearModel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        configureAxes(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lineModel.setLegendPosition("nw");
        lineModel.setLegendPlacement(LegendPlacement.INSIDE);

    }

    private void configureAxes(String currentDateTime) {
        DateAxis xAxis = new DateAxis("Letzten 12 Stunden");
        xAxis.setTickAngle(-50);
        xAxis.setMax(currentDateTime + " 19:00:00");
        xAxis.setTickFormat("%H:%M");
        xAxis.setTickInterval("1 hour");
        xAxis.setMin(currentDateTime + " 09:00:00");
        xAxis.setMax(currentDateTime + " 19:00:00");
        lineModel.getAxes().put(AxisType.X, xAxis);


        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(teamService.getMaxCountOfSeiderl()+1);
        yAxis.setTickInterval("5");
        yAxis.setLabel("Seidlanzahl");
    }

    private LineChartModel initLinearModel(String currentDateTime) {
        LineChartModel model = new LineChartModel();


        for (Team team : teamService.findAllteams()) {
            LineChartSeries seiderlSeries = new LineChartSeries();
            seiderlSeries.setLabel(team.getName());
            List<SeiderlHistory> historyPerTeam = seiderlHistoryService.getSeiderHistoryByTeam(team);
            if (!historyPerTeam.isEmpty()) {
                for (SeiderlHistory historyEntry : historyPerTeam) {
                    String localDateTime = historyEntry.getLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    seiderlSeries.set(localDateTime, historyEntry.getSeiderlCounter());
                }
            }
            // Now also add the current value to the series
            seiderlSeries.set(currentDateTime, team.getSeiderlCounter());
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
