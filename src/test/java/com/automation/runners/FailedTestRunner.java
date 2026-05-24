package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        // NEW CHANGE: Points directly to the rerun text file instead of the features folder
        features = "@target/failed_rerun.txt",
        glue = {"com.automation.stepdefs"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/failed_rerun.txt" // Overwrites the file if retries fail again
        },
        monochrome = true,
        publish = false
)
public class FailedTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}