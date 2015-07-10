package model

import com.wordnik.swagger.annotations.{ ApiModel, ApiModelProperty }
import spray.json.DefaultJsonProtocol

import scala.annotation.meta.field


@ApiModel(description = "A User entity")
case class User(
  @(ApiModelProperty @field)(value = "unique identifier for the user")
  id: Int,

  @(ApiModelProperty @field)(value = "email of the user")
  email: String,

  @(ApiModelProperty @field)(value = "user's name")
  name: Option[String] = None,

  @(ApiModelProperty @field)(value = "user's surname")
  surname: Option[String] = None,

  @(ApiModelProperty @field)(hidden = true)
  passwordId: Option[Int] = None
)
object User extends DefaultJsonProtocol{
  implicit val userFormat = jsonFormat5(User.apply)
}
