package com.example.vaadin.vaadinspringboot;

import org.springframework.context.annotation.Scope;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

@Tag(Tag.DIV)
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ValidateOTPFormComponent extends Component {

	private static final long serialVersionUID = -2586787695242495512L;

	Element labelElement = new Element("label");
	Element inputElement = new Element("input");

	public ValidateOTPFormComponent() {
		
		inputElement.synchronizeProperty("value", "change");
		getElement().appendChild(labelElement, inputElement);
	}

	public String getLabel() {
		return labelElement.getText();
	}

	public String getValue() {
		return inputElement.getProperty("value");
	}

	public void setLabel(String label) {
		labelElement.setText(label);
	}

	public void setValue(String value) {
		inputElement.setProperty("value", value);
	}
}
