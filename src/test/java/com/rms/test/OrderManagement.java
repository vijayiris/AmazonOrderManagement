package com.rms.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.rms.utility.Xls_Reader;


public class OrderManagement {

	public WebDriver driver;
	public WebDriverWait wait;
	public  Properties prop;
	
	
	
	Xls_Reader xlsReader = new Xls_Reader("C:\\Amazon\\OrderTemplate.xlsx") ;
	//Xls_Reader xlsReader = new Xls_Reader("./TestDataExcel/OrderTemplate.xlsx") ;
	
	
	
	
	
	public OrderManagement(){
		
		//System.setProperty("webdriver.chrome.driver", "./browser_exe/chromedriver.exe");		
		System.setProperty("webdriver.chrome.driver", "C:\\Amazon\\chromedriver.exe");
		
		try {
			prop = new Properties();
			//FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\config.properties");
			//FileInputStream ip = new FileInputStream(("./config.properties"));
			
			FileInputStream ip = new FileInputStream(("C:\\Amazon\\config.properties"));
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	//@BeforeMethod
	@Test
	public void setUp() throws InterruptedException {		
				
	    driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 15);		
		driver.manage().window().maximize();
		driver.get("https://www.amazon.com/");	   
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-accountList")));
		WebElement signIn = driver.findElement(By.id("nav-link-accountList"));
		signIn.click();
		String WAITBETWEENPAGES = prop.getProperty("waitbetweenpages");
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_email")));
		WebElement mobileOrEmailId = driver.findElement(By.id("ap_email"));
		mobileOrEmailId.click();
		Thread.sleep(1000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		mobileOrEmailId.sendKeys(prop.getProperty("username"));
		WebElement continueBtn = driver.findElement(By.id("continue"));
		continueBtn.click();
		
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
		WebElement passwordApp = driver.findElement(By.id("ap_password"));
		passwordApp.sendKeys(prop.getProperty("password"));
		WebElement signInInput = driver.findElement(By.id("signInSubmit"));
		signInInput.click();
		Thread.sleep(10000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		
		
	int numberOfRowsInExcel = xlsReader.getRowCount("order");	
	for(int i=2;i<=numberOfRowsInExcel;i++) {
			
	    String orderStatus = xlsReader.getCellData("order", 16, i);
	    if(orderStatus.equals("")) {
	    		   
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='nav-cart-count']")));
	   WebElement cartIcon = driver.findElement(By.xpath("//span[@id='nav-cart-count']"));
	   cartIcon.click();
	   Thread.sleep(2000);
	   Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));	
		List <WebElement> deleteAlreadyCartItems = driver.findElements(By.xpath("//input[@data-action='delete']"));
			for(WebElement deleteItemsLink : deleteAlreadyCartItems ) {
				deleteItemsLink.click();
				Thread.sleep(2000);
		}
		
		WebElement globalSearch = driver.findElement(By.id("twotabsearchtextbox"));
		globalSearch.click();		
		globalSearch.sendKeys(xlsReader.getCellData("order", 0, i));
		WebElement searchLensIcon = driver.findElement(By.xpath("//input[@class='nav-input' and @type='submit']"));
		searchLensIcon.click();
		Thread.sleep(5000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		WebElement productImage = driver.findElement(By.xpath("//a[contains(@class,'a-link-normal s-no-outline')]"));
		
		productImage.click();
		Thread.sleep(2000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		String Quantity  = xlsReader.getCellData("order", 1, i).split("\\.")[0];
		WebElement quantityElement = driver.findElement(By.id("a-autoid-0-announce"));	
		quantityElement.click();
		Thread.sleep(1000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement> quantityDropdownList = driver.findElements(By.xpath("//a[@class='a-dropdown-link']"));
		for(WebElement quanityToBeSelected : quantityDropdownList) {
			
			String QuantityString  = quanityToBeSelected.getText();			
			if(QuantityString.equals(Quantity)) {
				quanityToBeSelected.click();
				break;
			}
			
		}
		
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
		WebElement addToCartBtn = driver.findElement(By.id("add-to-cart-button"));
		addToCartBtn.click();
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'a-checkbox sc-gift-option a-align-top a-size-small')]/label/input")));
		WebElement giftCheckBox = driver.findElement(By.xpath("//div[contains(@class,'a-checkbox sc-gift-option a-align-top a-size-small')]/label/input"));
		giftCheckBox.click();
		Thread.sleep(2000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlb-ptc-btn-native")));
		WebElement proceedToCheckOut = driver.findElement(By.id("hlb-ptc-btn-native"));
		proceedToCheckOut.click();
		Thread.sleep(2000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement> continueBtnOnSharePaymentPage = driver.findElements(By.xpath("//input[@title='Continue' and @class='a-button-input']"));
		for(WebElement continueBtnPayment : continueBtnOnSharePaymentPage ) {
			continueBtnPayment.click();
			Thread.sleep(1000);
		}
		
		
		
		List <WebElement> passwordAppList = driver.findElements(By.id("ap_password"));
		for(WebElement passwordField :passwordAppList ) {
			
			passwordField.sendKeys(prop.getProperty("password"));
			WebElement signInInput1 = driver.findElement(By.id("signInSubmit"));
			signInInput1.click();
			Thread.sleep(3000);
		}
		
			
		WebElement totalAddressCount = driver.findElement(By.xpath("//div[contains(@class,'a-text-center pagination')]/span"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", totalAddressCount);
		
		Thread.sleep(1000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		WebElement fullName = driver.findElement(By.id("enterAddressFullName"));
		fullName.clear();
		fullName.click();
		fullName.sendKeys(xlsReader.getCellData("order", 4, i));
		WebElement addressline1 = driver.findElement(By.id("enterAddressAddressLine1"));
		addressline1.click();
		addressline1.clear();
		addressline1.sendKeys(xlsReader.getCellData("order", 5, i));
		
		WebElement city = driver.findElement(By.id("enterAddressCity"));
		city.click();
		city.clear();
		city.sendKeys(xlsReader.getCellData("order", 7, i));
		WebElement state = driver.findElement(By.id("enterAddressStateOrRegion"));
		state.click();
		state.clear();
		state.sendKeys(xlsReader.getCellData("order", 8, i));
		
		WebElement postalCode = driver.findElement(By.id("enterAddressPostalCode"));
		postalCode.click();
		postalCode.clear();
		postalCode.sendKeys(xlsReader.getCellData("order", 9, i).split("\\.")[0]);
		
		
		String countryToBeSelected = xlsReader.getCellData("order", 10, i);
		Select countryDropdown = new Select(driver.findElement(By.id("enterAddressCountryCode")));
		countryDropdown.selectByValue(countryToBeSelected);
		
		WebElement phoneNumber = driver.findElement(By.id("enterAddressPhoneNumber"));
		phoneNumber.click();
		phoneNumber.clear();
		phoneNumber.sendKeys(xlsReader.getCellData("order", 11, i));
		WebElement useAddressOnlyOnceCheckBox = driver.findElement(By.xpath("//input[@name='hideAddressFromDefaultAddressBook' and @value=1]"));
		useAddressOnlyOnceCheckBox.click();
		Thread.sleep(2000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		WebElement deliverToThisAddress = driver.findElement(By.xpath("//input[contains(@class,'a-button-text submit-button-with-name') and @name='shipToThisAddress']"));
		deliverToThisAddress.click();
		Thread.sleep(2000);
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		
		//If Error exist in Address
		List <WebElement> errorMessageIfAddressIsWrong = driver.findElements(By.xpath("//div[contains(@class,'a-box a-alert a-alert-error a-spacing-base ')]"));
        int ifErrorMessageExistInAddress = errorMessageIfAddressIsWrong.size();
        if(ifErrorMessageExistInAddress>0) {
        	xlsReader.setCellData("order", "Place your order", i, "InValid Address"); 
        	driver.get("https://www.amazon.com/");
        }
		
		
		
		List <WebElement> deliverToThisAddress1 = driver.findElements(By.xpath("//input[@name='useSelectedAddress']"));
		for(WebElement deliverToThisAddressBtn : deliverToThisAddress1 ) {
			deliverToThisAddressBtn.click();
			Thread.sleep(4000);
		}
		
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement> saveGiftOptionBtnList = driver.findElements(By.xpath("//span[contains(@class,'a-button a-button-primary chewbacca-enabled-save-gift-options-button')]/span/input"));
		for(WebElement saveGiftOptionBtn : saveGiftOptionBtnList ) {
			saveGiftOptionBtn.click();
			Thread.sleep(4000);
			break;
		}
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));		
		List <WebElement>  chooseYourShippingOptionContinue = driver.findElements(By.xpath("//span[contains(@class,'sosp-continue-button a-button a-button-primary a-button-span12 a-padding-none  continue-button ')]/span/input"));	
		for(WebElement chooseYourShippingOptionBtnBeforePayment : chooseYourShippingOptionContinue) {
			chooseYourShippingOptionBtnBeforePayment.click();
			Thread.sleep(3000);
			break;
		}
		
		
		
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement>  continueBtnBeforePaymentList = driver.findElements(By.xpath("//*[@id='shippingOptionFormId']/div[1]/div[2]/div/span[1]/span/input"));
		for(WebElement continueBtnBeforePayment : continueBtnBeforePaymentList) {
			continueBtnBeforePayment.click();
			Thread.sleep(3000);
			break;
		}

		
		
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement> cardsAttachedToUser = driver.findElements(By.xpath("//span[contains(@class,'a-color-secondary pmts-cc-number')]"));		
		for(WebElement cardlist : cardsAttachedToUser) {
			
			String cardDigit = cardlist.getText();
			String cardLastCharacters = cardDigit.substring(cardDigit.lastIndexOf("n") + 1);
			System.out.println("cardDigit   "+cardLastCharacters);
			String creditCardToBeSelected = xlsReader.getCellData("order", 15, i).split("\\.")[0];
			if(cardLastCharacters.trim().contains(creditCardToBeSelected)) {
				cardlist.click();
				break;
			}
			
		}
		  
		Thread.sleep(2000);
		List <WebElement>  finalContinueForpayment = driver.findElements(By.xpath("//input[@name='ppw-widgetEvent:SetPaymentPlanSelectContinueEvent']"));
		for(WebElement finalContinueForpaymenteBtnBeforePayment : finalContinueForpayment) {
			finalContinueForpaymenteBtnBeforePayment.click();
			Thread.sleep(3000);
			break;
		}
		
		
		
			
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement>  placeYourOrderBtn = driver.findElements(By.xpath("//input[contains(@class,'a-button-text place-your-order-button')]"));
		for(WebElement finalPlaceYourOrderBtn : placeYourOrderBtn) {
			finalPlaceYourOrderBtn.click();
			Thread.sleep(3000);
			break;
		}
		
		Thread.sleep(Integer.parseInt(WAITBETWEENPAGES));
		List <WebElement>  duplicateplaceYourOrderBtn = driver.findElements(By.xpath("//input[@value='Place this duplicate order' and @name='forcePlaceOrder']"));
		for(WebElement duplicatePlaceOrderBtn : duplicateplaceYourOrderBtn) {
			duplicatePlaceOrderBtn.click();
			Thread.sleep(2000);
			break;
		}

		List <WebElement> isConfirmationMessageDisplayed  = driver.findElements(By.xpath("//h2[@class='a-color-success']"));
		
        if (isConfirmationMessageDisplayed.size()>0) {
    		xlsReader.setCellData("order", "Place your order", i, "OrderPlaced Successfully");    		
    		System.out.println("number of item processed "+i);
        }
	
       
        Thread.sleep(4000);
        String WAITAFTEREVERYITEMPROCESSED = prop.getProperty("waitafteritemprocessed");
        Thread.sleep(Integer.parseInt(WAITAFTEREVERYITEMPROCESSED));
        
        String numberOfRecordAfterWhichPauseRequired = prop.getProperty("numberofrecordafterwhichpuaserequired");
        int numberOfRecordsAfterWhichPauseRequired = Integer.parseInt(numberOfRecordAfterWhichPauseRequired);
        if(i%numberOfRecordsAfterWhichPauseRequired==0) {
        
            String WAITAFTERSPECIFICRECORDPROCESSING = prop.getProperty("waitafterprocessingspeficnumberofrecord");
            Thread.sleep(Integer.parseInt(WAITAFTERSPECIFICRECORDPROCESSING));
        	
        }
        
        
		}
		
		}
		
	    tearDown();
	}
	



	public void tearDown() {
		driver.quit();
	}
	


}