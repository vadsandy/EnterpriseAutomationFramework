package com.automation.pages;

import org.openqa.selenium.By;

public class TransferFundsPage extends BasePage {

    private By drpFromAccount = By.id("fromAccountId");
    private By drpToAccount = By.id("toAccountId");
    private By txtAmount = By.id("amount");
    private By btnTransfer = By.xpath("//input[@value='Transfer']");
    private By lblSuccessMessage = By.xpath("//h1[contains(text(),'Transfer Complete!')]");
    private By lnkAccountsOverview = By.linkText("Accounts Overview");

    public void transferAmount(String amount) {
        action.waitForVisibility(drpFromAccount, "From Account Dropdown");

        // NEW CHANGE: Logic to ensure we transfer to a different account if available
        int toAccountOptions = action.getDropdownOptionsCount(drpToAccount, "To Account Dropdown");
        if (toAccountOptions > 1) {
            // Selects the second option in the list (index 1)
            action.selectByIndex(drpToAccount, 1, "To Account Dropdown");
        }

        action.type(txtAmount, amount, "Transfer Amount Field");
        action.click(btnTransfer, "Transfer Button");
        action.waitForVisibility(lblSuccessMessage, "Transfer Complete Message");
    }

    public void goToAccountsOverview() {
        action.click(lnkAccountsOverview, "Accounts Overview Menu Link");
    }
}