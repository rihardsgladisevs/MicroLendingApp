package lv.rgl.mla.acceptance;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Assert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;

/**
 * Created by rihards.gladisevs on 09.12.2014..
 */
public class SuccessfulLoanExtend {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    private String loanId;
    private BigDecimal interest;
    private DateTime endDate;

    @Given("^client has successful loan$")
    public void client_has_successful_loan() throws ParseException {
        String loan = get("/loans").asString();
        this.loanId = from(loan).getString("[0].id");
        this.interest = new BigDecimal(from(loan).getString("[0].interest"));
        System.out.println(from(loan).getString("[0].endDatePrepared"));
        Date date = dateFormat.parse(from(loan).getString("[0].endDatePrepared"));
        this.endDate = new DateTime(date);

        Assert.assertThat(loanId, Matchers.not(CoreMatchers.nullValue()));
        Assert.assertThat(interest, Matchers.not(CoreMatchers.nullValue()));
        Assert.assertThat(endDate, Matchers.not(CoreMatchers.nullValue()));
    }

    @When("^client extends the loan expecting status code (.+)$")
    public void client_extends_the_loan(Integer statusCode) {
        given()
            .pathParam("loanId", loanId)
        .expect()
            .statusCode(statusCode)
        .when()
            .post("/loans/{loanId}/extend");
    }

    @Then("^client has registered extension for given loan$")
    public void client_has_extension() {
        when()
            .get("/loans")
        .then()
            .body("[0].id", CoreMatchers.equalTo(Integer.valueOf(loanId)));
    }

    @And("^loan's interest is increased by a factor of (.+)$")
    public void loan_factor_increased(BigDecimal factor) {
        final BigDecimal expectedInterest = this.interest.multiply(factor);

        when()
            .get("/loans")
        .then()
            .body("[0].interest", CoreMatchers.equalTo(expectedInterest.floatValue()));
    }

    @And("^loan's end date is increased by (.+) week$")
    public void loan_factor_increased(Integer weeks) {
        final DateTime expectedEndDate = this.endDate.plusWeeks(weeks);

        when()
            .get("/loans")
        .then()
            .body("[0].endDatePrepared", CoreMatchers.equalTo(dateFormat.format(expectedEndDate.toDate())));
    }
}
