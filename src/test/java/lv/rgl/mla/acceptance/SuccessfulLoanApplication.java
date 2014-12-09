package lv.rgl.mla.acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.CoreMatchers;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

/**
 * Created by rihards.gladisevs on 09.12.2014..
 */
public class SuccessfulLoanApplication {

    private BigDecimal amount;
    private Integer term;

    @Given("^client need a loan with amount EUR (\\d+) for term of (\\d+) days$")
    public void client_need_a_loan(BigDecimal amount, Integer term) {
        this.amount = amount;
        this.term = term;
    }

    @When("^client applied for a loan with status (\\d+)$")
    public void client_made_an_application(Integer statusCode) {
        given()
            .param("amount", amount)
            .param("term", term)
        .expect()
            .statusCode(statusCode)
        .when()
            .post("/loans").then();
    }

    @Then("^client has loan in history")
    public void client_has_loan_in_history() {
        when()
            .get("/loans")
        .then()
            .body("[0]", CoreMatchers.not(CoreMatchers.nullValue()));
    }
}
