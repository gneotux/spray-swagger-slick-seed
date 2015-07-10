package dao

import model.{ User, UserPassword }
import utils.DatabaseConfig._
import utils.DatabaseConfig.profile.api._


trait UserDao {

  def create

  def getAll: DBIO[Seq[User]]

  def get(id: Int): DBIO[Option[User]]

  def get(email: String): DBIO[Option[(User, UserPassword)]]

  def add(user: User): DBIO[Int]

  def delete(id: Int): DBIO[Int]
}

trait UserDaoSlickImpl extends UserDao {

  class Users(tag: Tag) extends Table[User](tag, "users") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def email: Rep[String] = column[String]("email")

    def name: Rep[String] = column[String]("name")

    def surname: Rep[String] = column[String]("surname")

    def passwordId: Rep[Option[Int]] = column[Option[Int]]("password_id")

    def * = (id, email, name.?, surname.?, passwordId) <>((User.apply _).tupled, User.unapply)
  }

  val users = TableQuery[Users]

  override def create = users.schema.create

  override def getAll: DBIO[Seq[User]] = users.result

  override def get(id: Int): DBIO[Option[User]] = users.filter(_.id === id).result.headOption

  override def get(email: String): DBIO[Option[(User, UserPassword)]] =
    (for {
      user <- users.filter(_.email === email)
      password <- PasswordDao.passwords.filter(_.id === user.id)
    } yield (user, password)).result.headOption

  override def add(user: User): DBIO[Int] = {
    (users returning users.map(_.id)) += user
  }

  override def delete(id: Int): DBIO[Int] = users.filter(_.id === id).delete
}

object UserDao extends UserDaoSlickImpl
