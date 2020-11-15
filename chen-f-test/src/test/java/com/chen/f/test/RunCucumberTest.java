package com.chen.f.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//@io.cucumber.junit.platform.engine.Cucumber
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:feature",
        plugin = {"pretty", "summary",
                "html:target/cucumber/cucumber-report.html",
                "json:target/cucumber/cucumber.json"}
)
public class RunCucumberTest {

}
