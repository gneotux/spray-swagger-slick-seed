package model

import com.github.t3hnar.bcrypt.{ Password, generateSalt }


case class UserPassword(
  id: Int,
  hashedPassword: Option[String],
  salt: String = generateSalt
){
  def passwordMatches(password: String): Boolean = hashedPassword.contains(password.bcrypt(salt))
}
object UserPassword {
  def newWithPassword(password: String) = {
    val salt = generateSalt
    new UserPassword(0, Some(password.bcrypt(salt)), salt)
  }
}
