package at.eischer.jsfconverter;


import at.eischer.model.Team;
import at.eischer.view.FinalPhaseView;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "finalTeamConverter")
public class FinalTeamConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String teamId) {
        ValueExpression vex =
                facesContext.getApplication().getExpressionFactory()
                        .createValueExpression(facesContext.getELContext(),
                                "#{finalPhaseView}", FinalPhaseView.class);
        FinalPhaseView finalPhaseView = (FinalPhaseView) vex.getValue(facesContext.getELContext());
        return finalPhaseView.getTeamById(Long.valueOf(teamId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object team) {
        return Long.toString(((Team) team).getId());
    }
}
