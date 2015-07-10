package router

import akka.actor.{ Actor, ActorLogging }
import com.gettyimages.spray.swagger.SwaggerHttpService
import com.wordnik.swagger.model.ApiInfo
import service.UserService

import scala.reflect.runtime.universe._


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ApiRouterActor(service: UserService) extends Actor with UserRouter with ActorLogging with Authenticator {

  override val userService = service

  val swaggerService = new SwaggerHttpService {
    override def apiTypes = Seq(typeOf[UserRouterDoc])
    override def apiVersion = "0.1"
    override def baseUrl = "/" // let swagger-ui determine the host and port
    override def docsPath = "api-docs"
    override def actorRefFactory = context
    override def apiInfo = Some(new ApiInfo("Api users", "", "", "", "", ""))
  }

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(
    userOperations ~
    swaggerService.routes ~
    get {
      pathPrefix("") { pathEndOrSingleSlash {
          getFromResource("swagger-ui/index.html")
        }
      } ~
      pathPrefix("webjars") {
        getFromResourceDirectory("META-INF/resources/webjars")
      }
    }
  )

}