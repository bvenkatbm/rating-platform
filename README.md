# Rating Platform
Platform to add, edit and display ratings for available products

## Getting Started
Please follow the guide to understand how to set up and run the service

### Build

1. Clone the repository to a local directory
```
cd rating-platform
```

2. Execute the following command from the parent directory:
```
./gradlew build
```

### To run the service in IDE provide
1. VM options
 ```
 -Dspring.profiles.active=local 
 ```

2. The main entry point is
```
com.daytodayhealth.rating.RatingPlatformApplication 
```
class

### To run the service using Executable jar
Navigate to 
```
cd build\libs
```
and then from cmd execute the following command
```
java -jar -Dspring.profiles.active=local rating-platform-0.0.1-SNAPSHOT.jar
```

### To access H2 Database console
[H2 Console](http://localhost:8001/h2-console)

## APIs
### 1. Fetch Prodcuts:
#### HTTP Method: GET
#### Request URL: ```/products/list```
#### Example Response Body:
```[
{
"id": "f1ed3338-d3ad-4475-8ef5-50ae4dcdae5f",
"name": "Lab Test Order",
"averageRating": 2.3,
"ratingCount": 3
},
...
]
```
### 2. Fetch Ratings:
#### HTTP Method: GET
#### Request URL: ```/product/{product-id}/rating```
#### Query Parameters:
Filter using ```rating={rating}```

Order using ```sort=postedAt&order=asc```

*Note: Query Parameters are not mandatory*
#### Example Response Body:
```
{
    "averageRating": 2.0,
    "ratingCount": 1,
    "ratings": {
        "content": [
            {
                "message": "Nice App",
                "postedAt": "2021-06-24T13:13:19.636+00:00",
                "rating": 2,
                "postedByName": "User1"
            }
        ],
        "pageable": {
            "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
            },
            "offset": 0,
            "pageSize": 10,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "totalPages": 1,
        "last": true,
        "totalElements": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "numberOfElements": 1,
        "first": true,
        "empty": false
    }
}
```
### 3. Add Rating:
#### HTTP Method: POST
#### Request URL: ```/product/{product-id}/add-rating```
#### Request Body:
```
{
    "rating": 3,
    "message": "App Experience is not so great"
}
```
*Note: ```message``` is optional but rating is mandatory*
#### Example Response Body:
Rating is added successfully

### 4. Edit Rating:
#### HTTP Method: PUT
#### Request URL: ```/product/{product-id}/edit-rating```
#### Request Body:
```
{
    "rating": 3,
    "message": "App Experience is not so great"
}
```
*Note: ```message``` is optional but rating is mandatory*
#### Example Response Body:
Rating is edited successfully