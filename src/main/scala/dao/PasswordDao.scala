package dao

import model.UserPassword
import utils.DatabaseConfig.profile.api._

trait PasswordDao {

  def create: DBIO[Unit]

  def get(id: Int): DBIO[Option[UserPassword]]

  def add(password: UserPassword): DBIO[Int]
}

trait PasswordDaoSlickImpl extends PasswordDao {

  class Passwords(tag: Tag) extends Table[UserPassword](tag, "passwords") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def hashedPassword: Rep[Option[String]] = column[Option[String]]("hashed_password")

    def salt: Rep[String] = column[String]("salt")

    def * = (id, hashedPassword, salt) <>((UserPassword.apply _).tupled, UserPassword.unapply)
  }

  val passwords = TableQuery[Passwords]

  override def create: DBIO[Unit] = passwords.schema.create

  override def get(id: Int): DBIO[Option[UserPassword]] = passwords.filter(_.id === id).result.headOption

  override def add(password: UserPassword): DBIO[Int] = (passwords returning passwords.map(_.id)) += password

}

object PasswordDao extends PasswordDaoSlickImpl

