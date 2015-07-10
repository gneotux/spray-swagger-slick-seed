## Spray-swagger-slick-seed

Seed for activator

Key features:

* Basic Http Authentication
* Slick3
* Swagger using webjars
* Db drivers loaded with configuration file H2/Postgres
* Integrations tests
* Typesafe config


Follow these steps to get started:

1. Git-clone this repository.

        $ git clone https://github.com/Gneotux/pfc-spray.git my-project

2. Change directory into your clone:

        $ cd my-project

3. Create the database in postgres using the script in {DIRECTORY}/src/main/resources/schema.sql


4. Run the application

        > sbt run

5. Browse to [http://localhost:8080](http://localhost:8080/)


6. Learn more at
http://www.spray.io/
https://github.com/gettyimages/spray-swagger
http://slick.typesafe.com/news/2015/02/20/slick-3.0.0-RC1-released.html
http://etorreborre.github.io/specs2/guide/org.specs2.guide.QuickStart.html#Quick+Start
http://www.tecnoguru.com/blog/2014/07/07/implementing-http-basic-authentication-with-spray/

