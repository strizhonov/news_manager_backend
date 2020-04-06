**News Manager REST application.**<br/>
=====================================

Application intended to perform basic operations with news - saving, retrieving,
updating, removing and searching and is based on Spring and Hibernate frameworks.<br/>

To interact with client the app uses JSON format.<br/>

News item consists of:<br/>
- title;
- short text;
- full text;
- creation date;
- modification date;
- author (only one);
- tag(s) (zero, one or more).

**API description:**<br/>
------------------------------  
**Authentication**<br/>
----------
`POST` */auth/signin* - authenticate by passed credentials. Requires username and password in json format:<br/>
`{ "username" : "super_man", "password" : "super_password" } `<br/>

Return signed JSON Web Token in the response body.<br/>

***

`POST` */auth/signup* - register user in database. Requires username data in json format:<br/>
`{ "name" : "Joe", "surname" : "Dou", "username" : "super_man", "password" : "super_password" } `<br/>

***

**OAuth2**<br/>
----------
`POST` */clients* - create client for OAuth2 interacting. Requires client data in json format:<br/>
`{ "clientId" : "value", "clientSecret" : "secretHere", "grantType" : "password" } `<br/>

There's two types of grant type allowed:<br/>
*implicit* - for implicit grant type flow,<br/>
*password* - for resource owner grant type flow.<br/>

***

**News**<br/>
----------
  `POST` */news* - save news item in the database. Requires news item passed as json:<br/>
  `{ "title" : "news_item_five_title", "shortText" : "news_item_five_short_text", 
  "fullText" : "news_item_five_full_text", "author" : { "name" : "author_five_name" ,"surname" : 
  "author_five_surname" }, "tags" : [{ "name" : "tag_three" }, { "name" : "tag_four" }]}` <br/>
  If there are no mentioned author and tags in the database, they are to be created either. 
  News item creation and modification date will be set as current time.<br/>

***
 
   `GET` */news/:id* - get news item with dates by its id<br/>
 
***
   
  `PUT` */news* - updates news item, requires news item passed as json:<br/> 
  `{ "id" : 5, "title" : "updated_news_item_five_title", "shortText" : "news_item_five_short_text", "fullText" : 
  "news_item_five_full_text", "author" : { "name" : "author_five_name" ,"surname" : "author_five_surname" }, 
   "tags" : [{ "name" : "tag_three" }, { "name" : "tag_four" }]}` <br/><br/>
   If there are no mentioned author and tags in the database, they are to be created. 
   Modification date will be updated to the current time.<br/>
  
***
 
  `DELETE` */news/:id* - delete news item by its id<br/>
  
***

   `GET` */news* - get all news items with dates from database.<br/>
   
***

   `POST` */news/search* - search news items in the database. News can be filtered by author, or tags and 
   author simultaneously. <br /><br />
   Json object in the request body is used for search implementing:<br/>
   `{ "sortParams" : ["sort_param_one", "sort_param_two"], "tagNames" : ["tag_name_one", "tag_name_two"], 
   "author" : { "name" : "author_name", "surname" : "author_surname" } }`<br/>
  
   Search logic: (tag1_name OR tag2_name OR ... OR tagN_name) AND author_name AND author_surname. Author can be 
   null and tags can be empty, then every present news item will be retrieved.<br /><br/>

   Filtered news can also be sorted by mentioning sortParams. Possible param values:<br/>
     
   - date - result List will be sorted by the News' creationDate ascending;<br/>
 
   - tags - result List will be sorted by the News' number of tags ascending;<br/>

   - author - result List will be sorted by the News' Author surname ascending.<br/>
 
   Sort params will be considered in the mentioned order. If there's no sort param passed, 
   there's no sorting performed.<br />
   
   Example of searching news of "John Wonka" with tag "Finance" sorted by creation date and then tags number:<br/>
   
   `POST` */news/search*<br />
   `{ "sortParams" : ["date", "tags"], "tagNames" : ["Finance"], "author" : { "name" : "John", "surname" : "Wonka" } }`
   
***

   `GET` */news/count* - count all news in the database.<br/>
   
***

**Tags**<br/>
----------
`POST` */tags* - save tag in the database. Requires news item passed as json:<br/>
`{ "name" : "tag_name" } ` <br/>

***

 `GET` */tags/:id* - get tag by its id<br/>

***
 
`PUT` */tags* - updates tag, requires news item passed as json:<br/>
`{ "id" : 5, "name" : "tag_name" } ` <br/>

***

`DELETE` */tags/:id* - delete tag by its id<br/>

***

 `GET` */tags* - get all tags from database.<br/>
 
***

**Authors**<br/>
----------
`POST` */authors* - save author in the database. Requires author item passed as json:<br/>
`{ "name" : "author_name", "surname" : "author_surname" } `<br/>

***

 `GET` */authors/:id* - get author by its id<br/>

***
 
`PUT` */authors* - updates author, requires author item passed as json:<br/>
`{ "id" : 3, "name" : "author_name", "surname" : "author_surname" } `<br/>

***

`DELETE` */authors/:id* - delete author by its id<br/>

***

 `GET` */authors* - get all authors from database.<br/>
 
***
