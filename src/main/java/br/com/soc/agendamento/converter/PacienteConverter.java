package br.com.soc.agendamento.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.soc.agendamento.model.Paciente;

@FacesConverter(forClass = Paciente.class)
public class PacienteConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext faceContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Paciente) uiComponent.getAttributes().get(value);
        }
		return null;
	}

	@Override
	public String getAsString(FacesContext faceContext, UIComponent uiComponent, Object value) {
		if (value instanceof Paciente) {
            Paciente entity= (Paciente) value;
            if (entity != null && entity instanceof Paciente && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
		return "";
	}
	
}
