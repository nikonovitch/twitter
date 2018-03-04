package com.nxn.exercise.twitter

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber)
@CucumberOptions(
        format = ["pretty", "html:target/cucumber"],
        strict = true,
        features = ["src/test/resources/cucumber"]
)
class ApplicationE2ETest {
}
