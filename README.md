# IN-house-navigation-system
## Functional description
* There are X (X <100) base stations (BS) and Y( Y < 100) mobile stations (MS).
* Base stations can detect the presence of mobile stations in a certain radius
(detectionRadiusInMeters).
* When detected, MS id and timestamp are reported by BS to RestEndpoint1 (see below)
* One MS can be reported multiple times by multiple BSs.
* Data should be saved to relational database (in-memory db is fine for this assignment)
* MS position can be queried from RestEndpoint2 (see below).
* RestEndpoint2 should be mapped to /location/{uuid}, where uuid is MS id.
* RestEndpoint2 should correctly handle errors and situations where the information is not
available.

## Technology stack

* Java 8
* SpringBoot 2.5.2
* Maven
* JUnit
* H2 database
* MapStruct
* Lombok
* Swagger

## Navigation System Application

Swagger link:
`http://localhost:8080/api/v1/swagger-ui.html`

* When Mobile Station detected, MS id and timestamp are reported by BS to /report endpoint.

```curl
curl --location --request POST 'http://localhost:8080/api/v1/report' \
--header 'Content-Type: application/json' \
--data-raw '{
    "base_station_id": "60701e8e-a9db-4aec-88ec-fd45857ce670",
    "reports": [
        {
            "mobile_station_id": "60701e8e-ccc0-41a1-0000-7677kams5b19",
            "distance": 10,
            "timestamp": "2020-01-25T21:34:51"
        },
        {
            "mobile_station_id": "60701e8e-ccc0-41a1-1111-9099kams5b16",
            "distance": 11,
            "timestamp": "2020-01-25T21:34:51"
        },
        {
            "mobile_station_id": "60701e8e-ccc0-41a1-2222-8811kams5b17",
            "distance": 22,
            "timestamp": "2020-01-25T21:34:51"
        }
    ]
}'
```

* Mobile Station position can be queried from /location endpoint (you should set mobile station id {mobileStationId}).

```curl
curl --location --request GET 'http://localhost:8080/api/v1/location/f59769af-bbf0-47c7-9775-8481cfaf5b17'
```

