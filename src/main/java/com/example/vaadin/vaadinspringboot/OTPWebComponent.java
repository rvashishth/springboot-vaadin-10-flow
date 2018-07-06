package com.example.vaadin.vaadinspringboot;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Inline;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/* IMP: don't write forward slash before route path for vaadin as we do in spring*/
/* TODO: how to capture query params */
/* PageConfigurator @Inline, @ViewPort */
/* TODO: How to change page title icon */
/* spring singleton vaadin session scope and ui scope*/
/* vaadin spring configuration com.vaadin.server.DeploymentConfiguration, com.vaadin.server.Constants*/
/* TODO: running vaadin in production mode */
@PageTitle("Airtel - Confirm Purchase")
@Route("confirmotp")
@Inline("aoc.css")
@Viewport("width=device-width")
public class OTPWebComponent extends Div implements HasUrlParameter<String>{

	private static final long serialVersionUID = -1195372135916586582L;

	private String transactionID;
	
	/* TODO: How to use a transaction id for single user session */
	public OTPWebComponent(@Autowired ValidateOTPFormComponent component,
			@Autowired TransactionService transactionService) throws IOException{
		System.out.println("========OTPWebComponent=======");
		
		HorizontalLayout layout = new HorizontalLayout();
		
		ClassPathResource classPathResource = new ClassPathResource("airtel-logo.png");

		//TODO: How to read img from src 
		Image img = new Image("/airtel-logo.png","Airtel");
		
		Label headerMsg = new Label();
		headerMsg.setText("Please confirm purchase by submitting OTP sent to 9876543234");
		headerMsg.addClassName("color-red");
		
		HorizontalLayout productNameLayout = new HorizontalLayout();
		Label productName = new Label();
		productName.setText("Product Name: ");
		productName.addClassName("param-key");	
		Label productName2 = new Label();
		productName2.setText("Ola Ola ringtone");
		productNameLayout.add(productName);
		productNameLayout.add(productName2);
		
		HorizontalLayout priceLayout = new HorizontalLayout();
		Label productPrice = new Label();
		productPrice.setText("Price: ");
		productPrice.addClassName("param-key");
		
		Label productPrice2 = new Label();
		productPrice2.setText("Rs 200");
		priceLayout.add(productPrice); priceLayout.add(productPrice2);
		
		Label enterOTP = new Label();
		enterOTP.setText("Enter OTP");
		enterOTP.addClassName("param-key");
		
		Input otpInputField = new Input();
		otpInputField.setPlaceholder("i.e. 123123");
		otpInputField.addClassName("input-otp");
		
		HorizontalLayout hl = new  HorizontalLayout();
		hl.add(enterOTP); 
		hl.add(otpInputField);
		
		Button submitButton = new Button("Submit");
		submitButton.addClickListener(this::showButtonClickedMessage);
		submitButton.addClassName("button");
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.add(img);
		verticalLayout.add(headerMsg);
		verticalLayout.add(productNameLayout);
		verticalLayout.add(priceLayout);
		verticalLayout.add(hl);
		verticalLayout.add(submitButton);
		verticalLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,submitButton);

		add(verticalLayout);	
    }
	
	public void showButtonClickedMessage(ClickEvent<Button> event){
		System.out.println("=====button clicked====");
	}
	
	public void createOTPValidationView(){
		Optional<UI> ui = getUI();
		System.out.println("ui:"+ui);
		UI ui2 = ui.get();
		System.out.println("ui2:"+ui2);
	}
	
	@Override
	public void setParameter(BeforeEvent event, 
			@OptionalParameter String parameter) {
		System.out.println("======setParameter====");
		if(null == parameter || parameter.isEmpty()){
			setText("Transaction id is required :"+transactionID);
		}else{
			transactionID=parameter;
		}
		//System.out.println("===inside HasUrlParameter-setParameter()"+parameter);
		
	}
}
