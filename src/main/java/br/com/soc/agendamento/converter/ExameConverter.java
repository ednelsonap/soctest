package br.com.soc.agendamento.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.soc.agendamento.model.Exame;

@FacesConverter(forClass = Exame.class)
public class ExameConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext faceContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Exame) uiComponent.getAttributes().get(value);
        }
		return null;
	}

	@Override
	public String getAsString(FacesContext faceContext, UIComponent uiComponent, Object value) {
		if (value instanceof Exame) {
            Exame entity= (Exame) value;
            if (entity != null && entity instanceof Exame && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
		return "";
	}
	
}
