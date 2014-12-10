# MicroLendingApp
Simple micro-lending application

## Requirements
JRE 8u20+ (date.time parse bug JDK-8041360 fixed)

Maven 4

## Compile
Note: because of accepteance tests, tests need started application

Using maven: `mvn clean install -DskipTests`

## Run
As a jar: `java -jar mla-1.0-SNAPSHOT.jar`

## Test
Note: because of accepteance tests, tests need started application

Using maven: `mvn test`

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
                        "extensionDate": "10.12.2014 23:49:41"
                    }
                ],
                "currency": "EUR",
                "amountPrepared": 120,
                "applicationDate": "10.12.2014 23:49:39",
                "endDate": "06.01.2015 23:49:39"
            }
        ]
