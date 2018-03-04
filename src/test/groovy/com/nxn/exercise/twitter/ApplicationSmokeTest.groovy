package com.nxn.exercise.twitter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
class ApplicationSmokeTest extends Specification {

    @Autowired
    WebApplicationContext context

    def "Should boot up without any errors"(){
        expect: "web application context exists"
        context != null
    }
}
