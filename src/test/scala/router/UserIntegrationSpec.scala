package router

import model.User
import org.specs2.mock._
import org.specs2.mutable.Specification
import service.UserService
import spray.http.{ StatusCodes, BasicHttpCredentials }
import spray.httpx.SprayJsonSupport._
import spray.testkit.Specs2RouteTest
import utils.{ DatabaseSupportSpec, SpecSupport }


class UserIntegrationSpec extends Specification with Specs2RouteTest with UserRouter with SpecSupport with Authenticator with Mockito {

  // connects the DSL to the test ActorSystem
  implicit def actorRefFactory = system

  override val userService = UserService

  val user = BasicHttpCredentials("test1@test.com", "password1")

  "Users Endpoint" should {
    "leave GET requests to other paths unhandled" in this {
      Get("/kermit") ~> addCredentials(user) ~> userOperations ~>  check  {
        handled must beFalse
      }
    }
  }

  "Users Endpoint#users" should {
    "return a list of users for GET requests to users path" in this {
      Get("/users") ~> addCredentials(user) ~> userOperations ~> check {
        responseAs[Seq[User]] === DatabaseSupportSpec.users
      }
    }


    "return a single user for GET requests to users path" in this {
      Get("/users/1") ~> addCredentials(user) ~> userOperations ~> check {
        responseAs[User] === DatabaseSupportSpec.users.head
      }
    }

    "return the id for DELETE requests to users path" in this {
      Delete("/users/1") ~> addCredentials(user) ~> userOperations ~> check {
        status mustEqual StatusCodes.OK
      }
    }

    "return the correct user for POST requests to users path" in this {
      Post("/users", UserDto("test4@gmail.com", Some("name4"), Some("surname4"), "password1")) ~> addCredentials(user) ~> userOperations ~> check {
        status mustEqual StatusCodes.Created
        responseAs[User] === User(4, "test4@gmail.com", Some("name4"), Some("surname4"), Some(4))
      }
    }
  }


}
