package router

import com.wordnik.swagger.annotations._
import spray.routing._
import model.User

// Trying to not pollute the code with annotations
@Api(value = "/users", description = "Operations for users.", consumes= "application/json",  produces = "application/json")
trait UserRouterDoc {

    @ApiOperation(value = "Get a user by id", httpMethod = "GET", response = classOf[User])
    @ApiImplicitParams(Array(
      new ApiImplicitParam(name = "userId", value="ID of the user that needs to retrieved", required = true, dataType = "integer", paramType = "path" )
    ))
    @ApiResponses(Array(
      new ApiResponse(code = 200, message = "Ok"),
      new ApiResponse(code = 404, message = "User not found")
    ))
    def readRoute: Route

    @ApiOperation(value = "Get all the users", httpMethod = "GET", response = classOf[List[User]])
    def readAllRoute: Route

    @ApiOperation(value = "Delete a user by id", httpMethod = "DELETE", response = classOf[Int])
    @ApiImplicitParams(Array(
      new ApiImplicitParam(name = "userId", value="ID of the user that needs to be deleted", required = true, dataType = "integer", paramType = "path" )
    ))
    @ApiResponses(Array(
      new ApiResponse(code = 404, message = "User not found"),
      new ApiResponse(code = 400, message = "Invalid ID supplied")
    ))
    def deleteRoute: Route


    @ApiOperation(value = "Add a new user to the system", httpMethod = "POST", consumes="application/json")
    @ApiImplicitParams(Array(
      new ApiImplicitParam(name = "body", value="User object to be added", required = true, dataType = "router.UserDto", paramType = "body" )
    ))
    @ApiResponses(Array(
      new ApiResponse(code = 405, message = "Invalid user"),
      new ApiResponse(code = 201, message = "Entity Created")
    ))
    def postRoute: Route

}
