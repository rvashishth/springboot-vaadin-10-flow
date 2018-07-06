package com.example.vaadin.vaadinspringboot;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Inline;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import ch.qos.logback.classic.net.SyslogAppender;

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
@Theme(Lumo.class) 
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OTPWebComponent extends Div implements HasUrlParameter<String>, BeforeEnterObserver{

	private static final long serialVersionUID = -1195372135916586582L;

	private String transactionID;
	
	private Binder<TransactionDTO> binder = new Binder<>();
	
	private Label errorLabel = new Label();
	
	@Autowired 
	private TransactionService transactionService;
	
	/* TODO: How to use a transaction id for single user session */
	public OTPWebComponent() throws IOException{	
		System.out.println("========OTPWebComponent()=======");	
    }
	
	public void submitButtonClickedMessage(ClickEvent<Button> event) {
		System.out.println("=====button clicked====");
		TransactionDTO dto = new TransactionDTO();
		try {
			getChildren().forEach(component -> System.out.println(component.getId()));
			boolean anyMatch = getChildren().anyMatch(component -> component.getId().isPresent()?component.getId().get().equals("errorLabel"):false);
			System.out.println("anyMatch"+anyMatch);
			if(anyMatch) remove(errorLabel);
			binder.writeBean(dto);
			System.out.println("=====button clicked===="+dto);
				
			//TODO: Add or clear OTP Validation msg
		} catch (ValidationException e) {
			System.err.println("==============ValidationException===="+e.getBeanValidationErrors());
			errorLabel.setText("Invalid OTP ");
			errorLabel.addClassName("color-red");
			errorLabel.setId("errorLabel");
			add(errorLabel);
		}
	}
	
	
	@Override
	public void setParameter(BeforeEvent event, 
			@OptionalParameter String parameter) {
		System.out.println("======setParameter====");
		if(null == parameter || parameter.isEmpty()){
			//TODO: Create a suitable web view here and exit after this
			setText("Transaction id is required :"+transactionID);
		}else{
			transactionID=parameter;
		}	
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		System.out.println("===beforeEnter===="+transactionID);
		TransactionDTO trnasactionDTO = transactionService.getTrnasactionDTO(transactionID);
		VerticalLayout vl = prepareConsentPage(trnasactionDTO);
		add(vl);
	}
	
	private VerticalLayout prepareConsentPage(TransactionDTO trnasactionDTO){
		/* this image is loaded from another rest api call */
		Image img = new Image("/airtel-logo","Airtel");
		
		Label headerMsg = new Label();
		headerMsg.setText("Please confirm purchase by submitting OTP sent to "+trnasactionDTO.getMsisdn());
		headerMsg.addClassName("color-red");
		
		HorizontalLayout productNameLayout = new HorizontalLayout();
		Label namekeylabel = new Label();
		namekeylabel.setText("Product Name: ");
		namekeylabel.addClassName("param-key");	
		Label nameValueLabel = new Label();
		nameValueLabel.setText(trnasactionDTO.getProductName());
		//ReadOnlyHasValue<TransactionDTO> nameBinder = new ReadOnlyHasValue<>(dto -> nameValueLabel.setText(dto.getProductName()));
		//binder.bind(nameValueLabel,TransactionDTO::getProductName, TransactionDTO::setProductName);
		
		productNameLayout.add(namekeylabel);
		productNameLayout.add(nameValueLabel);
		HorizontalLayout priceLayout = new HorizontalLayout();
		Label productPriceLabel = new Label();
		
		productPriceLabel.setText("Price: ");
		productPriceLabel.addClassName("param-key");
		
		Label productPrice2 = new Label();
		//TODO: capture currency symbol from application.properties
		productPrice2.setText(trnasactionDTO.getPrice());
		priceLayout.add(productPriceLabel); priceLayout.add(productPrice2);
		
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
		submitButton.addClickListener(this::submitButtonClickedMessage);
		submitButton.addClassName("button");
		
		
		binder.forField(otpInputField)
			.withValidator(name -> name.length() >= 5,"Full name must contain at least three characters")
			.bind(TransactionDTO::getOtp,TransactionDTO::setOtp);
		//otpInputField.addValueChangeListener( event -> binder.validate());
		
		Input msisdn = new Input(); msisdn.setValue(trnasactionDTO.getMsisdn()); msisdn.setVisible(false);
		Input txnId = new Input(); txnId.setValue(transactionID); txnId.setVisible(false);
		binder.forField(msisdn).bind(TransactionDTO::getMsisdn,TransactionDTO::setMsisdn);
		binder.forField(txnId).bind(TransactionDTO::getTxnId,TransactionDTO::setTxnId);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.add(img);
		verticalLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,img);
		verticalLayout.add(new Hr());
		verticalLayout.add(headerMsg);
		verticalLayout.add(productNameLayout);
		verticalLayout.add(priceLayout);
		verticalLayout.add(hl);
		verticalLayout.add(submitButton);
		verticalLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,submitButton);
		return verticalLayout;
	}
	
}
