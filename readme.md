# Batch Payment Processing

## Problem

Consumer submits batch of payments. Payments are processed asynchronously and consumer is notified of the batch completion.

## Solution

- Consumer calls API to create a batch
- Batch is stored in the database and API returns success. 
To improve performance of writes payment list is serialized as JSON and parsed in SQL Server stored procedure and stored in Payments table
- Application Event is published
- Asynchronous event processing runs in the background
- Executor is configured with number of threads that process payments in parallel
- Individual payment status is updated (success or fail)
- Overall batch status is updated and consumer is notified through webhook (TBD)

## Setting up solution

Provide values for following environment variables:

- `BATCH_PAYMENT_DB_URL` - example `jdbc:sqlserver://localhost:1433;databaseName=batch-payments;encrypt=true;trustServerCertificate=true;`
- `BATCH_PAYMENT_DB_USER`
- `BATCH_PAYMENT_DB_PASSWORD`

Note that database tables and stored procedures are created by Flyway and database user needs permissions to create DDL

Run API:
`mvn spring-boot:run`

Post batch:

```
POST /batch HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
	"batchId":"test-00001",
	"payments":[
    {
        "paymentId": "CA14D57F-9962-43FE-9CD5-6BF8AF13375A",
        "paymentType": "ITP",
        "paymentInstruction": {
            "fromAccount": "1",
            "toAccount": "2",
            "amount": 2331.00
        }
    },
    {
        "paymentId": "0221BEE5-0243-465B-B05D-77AF3BB093FA",
        "paymentType": "ITP",
        "paymentInstruction": {
            "fromAccount": "1",
            "toAccount": "2",
            "amount": 2621.00
        }
    },
    {
        "paymentId": "92B1F4EC-8EBE-4A94-B91B-60D5097731C8",
        "paymentType": "ITP",
        "paymentInstruction": {
            "fromAccount": "1",
            "toAccount": "2",
            "amount": 245.00
        }
    },
    {
        "paymentId": "9ECB220F-2654-4035-9E35-2E94145DE1FF",
        "paymentType": "ITP",
        "paymentInstruction": {
            "fromAccount": "1",
            "toAccount": "2",
            "amount": 3175.00
        }
    },
    {
        "paymentId": "34B1A145-63BF-49E8-A29C-850319728604",
        "paymentType": "ITP",
        "paymentInstruction": {
            "fromAccount": "1",
            "toAccount": "2",
            "amount": 490.00
        }
    }
]
}
```







