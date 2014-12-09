# MicroLendingApp
Simple micro-lending application

## Requirements
JRE 8

Maven 4

## Compile
Using maven: `mvn clean install`

## Run
As a jar: `java -jar mla-1.0-SNAPSHOT.jar`

## Configuration
You can change configuration in `src/main/resources/application.yml`

Default configuration:

    loan:
      interest: 20.00
      risk:
        maxAmount: EUR 400.00
        maxApplicationsPerDay: 3
        nightEndHour: 6
      extension:
        factor: 1.50
        weeksToIncrease: 1

## Functionality
### Apply for a loan
POST `/loans`

    data:
      amount: 300
      term: 20

### Extend a loan
POST `/loans/{loanId}/extend`

### Get history of loans
GET `/loans`

## Example of json from get history of loans

    [
        {
            "id": 1,
            "interest": 30,
            "riskStatus": "OK",
            "extensions": [
                {
                    "id": 1,
                    "extensionDatePrepared": 1418114073250
                }
            ],
            "currency": "EUR",
            "endDatePrepared": 1420446866325,
            "applicationDatePrepared": 1418114066325,
            "amountPrepared": 120
        }
    ]
