package com.automation.pages;

import org.openqa.selenium.By;

public class AccountsOverviewPage extends BasePage{

    private By lnkTransferFunds = By.linkText("Transfer Funds");
    // Grabs the balance column of the very first account in the table
    private By lblFirstAccountBalance = By.xpath("//table[@id = 'accountTable']/tbody/tr[1]/td[2]");

    public void clickTransferFunds(){
        action.click(lnkTransferFunds, "Transfer Funds Menu Link");
    }

    public double getFirstAccountBalance(){
       String balanceText = action.waitForVisibility(lblFirstAccountBalance, "First Account Balance").getText();
       // Clean the string (e.g., "$100.50" -> "100.50") so we can do math on it
       balanceText = balanceText.replace("$", "").replace(",", "");
       return Double.parseDouble(balanceText);
    }

}
