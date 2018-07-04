package at.eischer.jsfconverter;


import at.eischer.model.GaberlnItem;
import at.eischer.view.GaberlnItemViewBean;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "gaberlnPlayerConverter")
public class GaberlPlayerConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String gaberlnId) {
        ValueExpression vex =
                facesContext.getApplication().getExpressionFactory()
                        .createValueExpression(facesContext.getELContext(),
                                "#{gaberlnItemViewBean}", GaberlnItemViewBean.class);
        GaberlnItemViewBean gaberlnItemViewBean = (GaberlnItemViewBean) vex.getValue(facesContext.getELContext());
        return gaberlnItemViewBean.getGaberlnItemById(Long.valueOf(gaberlnId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object gaberlnPlayer) {
        return Long.toString(((GaberlnItem) gaberlnPlayer).getId());
    }
}
