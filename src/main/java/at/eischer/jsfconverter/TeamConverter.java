package at.eischer.jsfconverter;


import at.eischer.model.Team;
import at.eischer.view.SpielManagementView;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "teamConverter")
public class TeamConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String teamId) {
        boolean isHomeTeam = (Boolean) uiComponent.getAttributes().get("isHomeTeam");
        ValueExpression vex =
                facesContext.getApplication().getExpressionFactory()
                        .createValueExpression(facesContext.getELContext(),
                                "#{spielManagementView}", SpielManagementView.class);

        SpielManagementView spielManagementView = (SpielManagementView) vex.getValue(facesContext.getELContext());
        return spielManagementView.getTeamById(Long.valueOf(teamId), isHomeTeam);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object team) {
        return Long.toString(((Team) team).getId());
    }
}
