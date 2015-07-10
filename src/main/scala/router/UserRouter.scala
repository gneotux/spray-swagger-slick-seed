package router

import service.UserService
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing.{ HttpService, Route }
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


// this trait defines our service behavior independently from the service actor
trait UserRouter extends HttpService with UserRouterDoc {
  self: Authenticator =>

  val userService: UserService

  val userOperations: Route = postRoute ~ readRoute ~ readAllRoute ~ deleteRoute

  override def readRoute = path("users" / IntNumber) { userId =>
      get {
        authenticate(basicUserAuthenticator) { authInfo =>
          respondWithMediaType(`application/json`) {
            onComplete(userService.get(userId)) {
              case Success(Some(user)) => complete(user)
              case Success(None) => complete(NotFound, "User not found")
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          }
        }
      }
    }

  override def readAllRoute = path("users") {
      get {
        authenticate(basicUserAuthenticator) { authInfo =>
          respondWithMediaType(`application/json`) {
            onComplete(userService.getAll) {
              case Success(users) => complete(users)
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          }
        }
      }
    }

  override def deleteRoute = path("users" / IntNumber) { userId =>
      delete {
        authenticate(basicUserAuthenticator) { authInfo =>
          respondWithMediaType(`application/json`) {
            onComplete(userService.delete(userId)) {
              case Success(ok) => complete(OK)
              case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          }
        }
      }
    }

  override def postRoute: Route = path("users") {
      post {
        authenticate(basicUserAuthenticator) { authInfo =>
          entity(as[UserDto]) { user =>
            respondWithMediaType(`application/json`) {
              onComplete(userService.add(user)) {
                case Success(Some(newUser)) => complete(Created, newUser)
                case Success(None) => complete(NotAcceptable, "Invalid user")
                case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
              }
            }
          }
        }
      }
    }

}
