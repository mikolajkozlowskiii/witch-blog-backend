# Witch Blog Backend
The project Witch Blog is realised in cooperation with COMARCH company as a part of the Team Projects Confference 2023 at Wroclaw University of Science and Technology.
## Description of Witch Blog
A website with several sections dedicated to magic and divination. The user should be able to learn about ancient rituals, famous witches and hags, read their horoscope, and receive online divination from Tarot cards. A unique feature will be a mobile add-on that allows the user to recognize Tarot cards in a photo and provide the general divination based on them.

#### Main objectives:
1. web application 
2. mobile application (Android)
3. image recognition by neural networks - tarot cards
4. algorithm that generates a divination based on the cards


Video presentation of project:
* [demo on YouTube](https://www.youtube.com/watch?v=K1szw2-itfw&ab_channel=KonferencjaProjekt%C3%B3wZespo%C5%82owychPWr)

Other project repositories:

* [web app frontend](https://github.com/mieszkoP13/witch-blog-web-frontend)
* [mobile app frontend](https://github.com/mieszkoP13/witch-blog-mobile-frontend)
* [neural networks](https://github.com/MaciejKiec/witch-blog-mobile-restAPI)

## Description of backend part of project
The backend part includes a database and REST API. Both the database and REST API have been deployed for production on the azure platform.
### Technology Stack
*  Java
*  Spring Boot
*  Azure SQL Database
*  Azure App Service
*  Git
  
### Resources
The REST API contains resources for authorization, users, divinations, tarot cards, photos or articles. 

### Authentication and Authorization
Authenticated users can have full rights (related to their roles) to use the backend. For now, there are 3 different user roles: user, moderator and admin. Depending on the role, you have more or less number of access rights to the endpoints. There are 2 ways to create an account. You can create an account locally or log in using your Google account. If you decide to create an account locally, you need to register and then confirm your email within 15 minutes. In the case of logging in via google OAuth 2.0, you need to authenticate with the authorization service. Rest API is stateless. Therefore, the authentication process when accessing resources involves sending a JWT token in the header, on the basis of which the user is authenticated and then authorized. The JWT token is passed to the frontend at login and then, if necessary, the frontend can ask to refresh the token. 


https://github.com/mikolajkozlowskiii/witch-blog-backend/assets/68119685/d18c097a-eb98-4461-870d-805448efdc13


### Divination alghorithm
The most complex resource is the divinations resource. It has an algorithm that generates personalized divinations based on tarot cards. Tarot cards as input to the algorithm can be given by the recognized cards on the taken picture by neural networks in the mobile application or randomly drawn in the web application. Each tarot card of the 78 in the deck of cards has about 8 unique positive meanings and 8 unique negative meanings. The negative meaning is read if the exposed card is turned 180 degrees. On the basis of the meanings of these cards a personalized sentence is composed, which is more or less positive in nature. 

[See video of divination algorithm in use](https://www.youtube.com/watch?v=SXeWW9YQ0ck)


## Explore Rest APIs
* deployed version: https://witchblog.azurewebsites.net
* local version: http://localhost:8080
### Authorization
| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/v1/auth/signup  | Sign up | [JSON](#signup) |
| POST   | /api/v1/auth/signin  | Log in | [JSON](#signin) |
| POST   | /api/v1/auth/confirmation-email | Send again confirmation email | |
| POST   | /api/v1/auth/confirm-email | Confirm email by sending in required parameter token | |
### Users

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/v1/users/me | Get current logged in user info | |
| GET    | /api/v1/users/{email} | Get user info by email | | 
| PUT    | /api/v1/users/{email} | Update user info (if email belongs to logged in use) |  [JSON](#editprofile) | 
| PUT    | /api/users/{email}/mod | Give moderator role to user (only for admins) | |
| DELETE    | /api/users/{email}/mod | Take moderator role from user (only for admins) | |
| GET    | /api/users/user-roles | Get all users with user role |  |
| GET    | /api/users/mod-roles | Get all users with moderator role | |


### Divinations
| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /api/v1/divinations | Generate personalized divination from virtual cards on web app |
| POST    | /api/v1/divinations/mobile | Generate personalized divination by sending physical cards recognized by neural networks in a mobile app|[JSON](#divinations) |
| GET    | /api/v1/divinations/history | Get history of all divinations (only for admin)|
| GET    | /api/v1/divinations/history/{userId} | Get user's history of all divinations|


### Tarot Cards
| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /api/v1/tarot | Create tarot card (only for mod or admin or mod) |
| GET    | /api/v1/tarot/id/{cardId} | Get tarot card by id |
| GET    | /api/v1/tarot/name/{name} | Get tarot card by name |
| GET    | /api/v1/tarot/random | Get random tarot card |


### Images
| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /api/v1/images/tarot | Send image of tarot card (only for admins or mods)|
| GET    | /api/v1/images/tarot/{name} | Get image of tarot card by name |
| GET    | /api/v1/images/base64/tarot/{name} | Get image encoded in base64 of tarot card |
| DELETE    | /api/v1/images/tarot/{name} | Delete image of tarot card by name (only for admins or mods)|

### Articles
| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/v1/articles | Get all articles |
| GET    | /api/v1/articles/{id} | Get article by id |
| POST    | /api/v1/articles | Create article (only for admins or mods) | [JSON](#articles)|
| PUT    | /api/v1/articles | Update article (only for admins or mods) |
| DELETE    | /api/v1/articles/{id} | Delete article (only for admins or mods) |



## Sample Valid JSON Request Body

##### <a id="signup">Sign up -> /api/v1/auth/signup</a>
```json
{
	"firstName": "Mikolaj",
	"lastName": "Kozlowski",
	"password": "password",
	"email": "mikolajkozlowskiii@gmail.com"
}
```

##### <a id="login">Sign in -> /api/v1/auth/singin</a>
```json
{
	"password": "password",
	"email": "mikolajkozlowskiii@gmail.com"
}
```

##### <a id="editprofile">Edit profile info -> /api/v1/users/{email}</a>
```json
{
	"firstName": "Mikolaj",
	"lastName": "Kozlowski",
	"password": "password",
	"birthDate": "01-01-2001 16:30:00"
}
```

##### <a id="divinations">Get divination for recognised cards -> /api/v1/divinations/mobile</a>
```json
[
    {
        "nameOfCard": "The Lovers",
        "isReversed": "false"
    },
    {
        "nameOfCard": "The Devil",
        "isReversed": "true"
    },
    {
        "nameOfCard": "The Moon",
        "isReversed": "false"
    }
]
```

##### <a id="articles">Create article (only for admins or mods). Max length of: title - 50 chars, content - 5000 chars.-> /api/v1/divinations/mobile</a>
```json
{
	"title": "Title of curious article",
	"content": "Content of curious article",
}
```



## Sample JSON Response Body
##### <a id="tarotcard">Info about tarot card a -> /api/v1/tarot/id/130</a>
```json
{
    "name": "King of Swords",
    "number": "14",
    "arcana": "Minor Arcana",
    "suit": "Swords",
    "img": "s14.jpg",
    "fortune_telling": [
        "This card represents an older man with an insightful, deliberate spirit, likely born between May 11th and June 10th, who is known for his integrity and sharp decision-making ability"
    ],
    "keywords": [
        "verdict",
        "expertise",
        "decision",
        "genius"
    ],
    "meanings": {
        "light": [
            "Expressing yourself with firmness and authority",
            "Consulting an expert",
            "Rendering a final decision",
            "Coming to a final conclusion",
            "Calling in advisors and consultants",
            "Reaching a beneficial agreement based on sound information"
        ],
        "shadow": [
            "Talking \"over the heads\" of others",
            "Waffling on an important decision",
            "Wishing in vain you could take back what's been said",
            "Insisting on having the last word",
            "Flaunting your intellectual capability",
            "Constantly changing your mind",
            "Refusing to make choices that are in your own best interest"
        ]
    },
    "Elemental": "Fire of Air.",
    "Affirmation": "\"My word is my bond.\"",
    "Questions to Ask": [
        "If you were to ask others, \"What's my area of expertise?\" what would they say?",
        "How comfortable are you saying exactly what you mean? How often do you temper what you have to say for fear of offending others?",
        "What would your decision be if you had to render a binding verdict right now?"
    ]
}
```


##### <a id="prediction">Divination based on generated cards in web app -> /api/v1/divinations</a>
```json
{
    "cardsResponse": [
        {
            "card": {
                "id": 93,
                "name": "The Hanged Man",
                "number": "12",
                "arcana": "Major Arcana",
                "suit": "Trump",
                "img": "m12.jpg"
            },
            "meaning": "Profiting at the expense of others",
            "reversed": true
        },
        {
            "card": {
                "id": 99,
                "name": "The Moon",
                "number": "18",
                "arcana": "Major Arcana",
                "suit": "Trump",
                "img": "m18.jpg"
            },
            "meaning": "Practicing magic or celebrating the magic of everyday life",
            "reversed": false
        },
        {
            "card": {
                "id": 113,
                "name": "Page of Cups",
                "number": "11",
                "arcana": "Minor Arcana",
                "suit": "Cups",
                "img": "c11.jpg"
            },
            "meaning": "Indulging in overly-sweet sentimentality",
            "reversed": true
        }
    ],
    "prediction": "Hi Mikolaj! Happy 2023-07-02. Prepare yourself for today's divination. By an unlucky twist of fate, you are about to be profiting at the expense of others (The Hanged Man), indulging in overly-sweet sentimentality (Page of Cups). On the other hand, to your advantage, you are about to be practicing magic or celebrating the magic of everyday life (The Moon). It's a forecast that stirs a range of emotions, keeping you intrigued."
}
```


##### <a id="getarticle">Get 2 recent articles -> /api/v1/articles?pageNo=0&pageSize=2&sortBy=createdDate</a>
```json
{
    "content": [
        {
            "id": 552,
            "title": "Modern tarot cards",
            "content": "Lorem ipsum...",
            "createdDate": "2023-06-14T07:46:52.861697",
            "createdBy": {
                "id": 71,
                "email": "mieszkopietrzak@gmail.com",
                "firstName": "Mieszko",
                "lastName": "Gawerda",
                "birthDate": "2023-03-01T11:43:00.000+00:00"
            },
            "modifiedDate": "2023-06-15T14:15:45.952755",
            "modifiedBy": {
                "id": 71,
                "email": "mieszkopietrzak@gmail.com",
                "firstName": "Mieszko2",
                "lastName": "Gawerda",
                "birthDate": "2023-03-01T11:43:00.000+00:00"
            }
        },
        {
            "id": 454,
            "title": "The Mysteries of Tarot Cards",
            "content": "Lorem ipsum...",
            "createdDate": "2023-06-12T21:28:09.636372",
            "createdBy": {
                "id": 71,
                "email": "mieszkopietrzak@gmail.com",
                "firstName": "Mieszko",
                "lastName": "Gawerda",
                "birthDate": "2023-03-01T11:43:00.000+00:00"
            }
        }
    ],
    "pageable": "INSTANCE",
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "size": 2,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}
```


