package com.example.vaadin.vaadinspringboot;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Airtel - Confirm Purchase")
@Route("validateotp")
public class HelloFormRouter extends Div implements HasUrlParameter<String>{

	private static final long serialVersionUID = 1273665514632355417L;

	public HelloFormRouter() {
		/*TransactionDTO dto = new TransactionDTO();
		dto.setProductName("ringtone");
		dto.setPrice("20 rs");
		
		Input otpField = new Input();
		
		Binder<TransactionDTO> binder = new Binder<>();
		binder.bind(otpField, TransactionDTO::getOtp, TransactionDTO::setOtp);*/
		
		FormLayout validateOTPForm = new FormLayout();
		Label headerMsg = new Label();
		headerMsg.setText("Please confirm purchase by submitting otp sent to 9876543234");
		
		TextField otpTextField = new TextField();
		otpTextField.setPlaceholder("i.e. 123455");
		validateOTPForm.addFormItem(otpTextField, "Enter OTP");
		
		Button button = new Button("Vaadin button",new Icon(VaadinIcon.DOCTOR_BRIEFCASE));
		button.addClickListener(this::showButtonClickedMessage);
		button.setSizeUndefined();
		validateOTPForm.add(button);
		
		validateOTPForm.setSizeFull();
		
		add(validateOTPForm);	
	}

	public void showButtonClickedMessage(ClickEvent<Button> event){
		System.out.println("=====button clicked====");
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {

	}
}
