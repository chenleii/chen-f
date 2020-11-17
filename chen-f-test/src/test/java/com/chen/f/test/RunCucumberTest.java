package com.chen.f.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//@io.cucumber.junit.platform.engine.Cucumber
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        plugin = {"pretty", "summary",
                "html:target/cucumber/cucumber-report.html",
                "json:target/cucumber/cucumber.json",
                // maven-cucumber-reporting插件必须在这目录，暂时没找到解决办法。
                "json:target/cucumber.json"}
)
public class RunCucumberTest {

}
