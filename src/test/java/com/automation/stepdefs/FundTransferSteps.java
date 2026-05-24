package com.automation.stepdefs;

import com.automation.context.TestContext;
import com.automation.pages.AccountsOverviewPage;
import com.automation.pages.TransferFundsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class FundTransferSteps {

    private TestContext testContext;
    private AccountsOverviewPage accountsPage;
    private TransferFundsPage transferPage;

    public FundTransferSteps(TestContext context){
        this.testContext = context;
        this.accountsPage = new AccountsOverviewPage();
        this.transferPage = new TransferFundsPage();
    }

    @When("I note the initial account balance")
    public void i_note_the_initial_account_balance() {
        double initialBalance = accountsPage.getFirstAccountBalance();

        // Save the balance into our PicoContainer state
        testContext.setContext("initialBalance", initialBalance);
        System.out.println("Initial Balance noted: $" + initialBalance);
    }

    @When("I transfer {int} dollars to another account")
    public void i_transfer_dollars_to_another_account(Integer amount){
        accountsPage.clickTransferFunds();
        transferPage.transferAmount(String.valueOf(amount));

        // Save the transfer amount to the state
        testContext.setContext("transferAmount", (double) amount);

        // Navigate back to check the updated balance
        transferPage.goToAccountsOverview();
    }

    @Then("the account balance should reflect the deduction")
    public void the_account_balance_should_reflect_the_deduction(){
        // Retrieve the saved values from the context
        double initialBalance = (double) testContext.getContext("initialBalance");
        double transferAmount = (double) testContext.getContext("transferAmount");

        // Calculate the expected math logic
        double expectedBalance = initialBalance - transferAmount;

        // Fetch the actual updated balance from the UI
        double actualBalance = accountsPage.getFirstAccountBalance();
        System.out.println("Expected Balance: $" + expectedBalance);
        System.out.println("Actual Balance: $" + actualBalance);

        Assert.assertEquals(actualBalance, expectedBalance, "Balance did not match after transfer!");

    }

}
