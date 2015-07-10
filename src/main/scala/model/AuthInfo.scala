package model


case class AuthInfo(user: User) {
  //Here you should put the logic for permissions associated to users
  def hasPermissions(permission: String): Boolean = true
}
