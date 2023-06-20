# Recipe Backend API System
This repository implemented a Recipe Backend Management System

### Requirements to run the application
• Windows/Linux<br/>
• Java/Open JDK 11<br/>
• Docker<br/>

### Steps to follow
•`git clone https://github.com/ezechima123/recipe-backend-api.git`<br/>
•`cd recipe-backend-api`<br/>
•`docker-compose up -d`<br/>

## List of Precreated Registered Users

| **#** | **Email**           | **FullName**            | **Password** |
|-------|---------------------|-------------------------|--------------|
| 1     | userTest1@gmail.com | Chima Emmanuel Ezeamama | pwd123#$     |
| 2     | userTest2@gmail.com | Chima Emmanuel Ezeamama | pwd123#$     |

# Actions to be Taken

| **Action**  	| **Login User**                       	|
|-------------	|--------------------------------------	|
| Request Url 	| http://localhost:8654/api/auth/login 	|
| Method      	| POST                                 	|

```xml
{
    "email": "userTest1@gmail.com",
    "password": "pwd123#$"
}
```
<br/>
<img title="a title" alt="Alt text" src="docs/images/loginuser.jpg">
<br/>

| **Action**  	| **Create a Recipe**                  	|
|-------------	|--------------------------------------	|
| Request Url 	| http://localhost:8654/api/v1/recipes 	|
| Method      	| POST                                 	|

```xml
{
    "referenceId": "1572-8228-0000",
    "title": "Salad",
    "servings": 2,
    "maintainedBy": "userTest1@gmail.com",
    "ingredients": [
        "Egg",
        "Carrot",
        "Cocumber"
    ],
    "instructions": [
        "Watch",
        "Cut",
        "use Mayo"
    ],
    "comment": "Recipe Created",
    "vegetarian": true
}
```
<img title="a title" alt="Alt text" src="docs/images/createrecipe.jpg">
<br/>

| **Action**  	| **Fetch a Recipe**                        	|
|-------------	|-------------------------------------------	|
| Request Url 	| http://localhost:8654/api/v1/recipes/{id} 	|
| Method      	| GET                                       	|

<br/>
http://localhost:8654/api/v1/recipes/6490efed511e704670c79a51

<br/>
<img title="a title" alt="Alt text" src="docs/images/getrecipe.jpg">
<br/>

| **Action**  	| **Update a Recipe**                        	|
|-------------	|-------------------------------------------	|
| Request Url 	| http://localhost:8654/api/v1/recipes/{id} 	|
| Method      	| PUT                                       	|

```xml
{
    "referenceId": "1572-8228-0000",
    "title": "Salad",
    "servings": 2,
    "maintainedBy": "userTest1@gmail.com",
    "ingredients": [
        "Egg",
        "Carrot",
        "Spinash
    ],
    "instructions": [
        "Watch",
        "Cut",
        "use Mayo2"
    ],
    "comment": "Recipe updated for Money",
    "vegetarian": true
}
```


<br/>
<img title="a title" alt="Alt text" src="docs/images/updaterecipe.jpg">
<br/>

| **Action**  	| **Delete a Recipe**                        	        |
|-------------	|-------------------------------------------	        |
| Request Url 	| http://localhost:8654/api/v1/recipes/{id}/{comment}	|
| Method      	| DELETE                                      	        |


<br/>
http://localhost:8654/api/v1/recipes/6490efed511e704670c79a51/Delete Recipe by Me

<br/>
<img title="a title" alt="Alt text" src="docs/images/deleterecipe.jpg">
<br/>

| **Action**  	| **Filter and Search Recipe**                        	|
|-------------	|-------------------------------------------	        |
| Request Url 	| http://localhost:8654/api/v1/recipes/search?{filter}	|
| Method      	| GET    


### Query where vegetarian = true
```xml
http://localhost:8654/api/v1/recipes/search?filter=vegetarian:eq:true
```
<br/>
<img title="a title" alt="Alt text" src="docs/images/search1.jpg">
<br/>


### Query where servings = 1 and has 'Pepper' as Ingredients
```xml
http://localhost:8654/api/v1/recipes/search?filter=servings:eq:1&filter=ingredients:in:Pepper
```
<br/>
<img title="a title" alt="Alt text" src="docs/images/search2.jpg">
<br/>


### Query  without 'potatoes' as ingrdients and has 'Cut' as Instructions
```xml
http://localhost:8654/api/v1/recipes/search?filter=ingredients:nin:potatoes&filter=instructions:in:Cut
```
<br/>
<img title="a title" alt="Alt text" src="docs/images/search3.jpg">
<br/>



# The Software Documentation is located on the docs folder


