package service

import dao.{ PasswordDao, UserDao }
import model.{ User, UserPassword }
import router.UserDto
import utils.DatabaseConfig._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait UserService {

  def userDao: UserDao

  def passwordDao: PasswordDao

  def add(user: UserDto): Future[Option[User]]

  def getAll(): Future[Seq[User]]

  def get(id: Int): Future[Option[User]]

  def get(email: String): Future[Option[(User, UserPassword)]]

  def delete(id: Int):Future[Int]

  def populateUser: UserDto => User = (userDto: UserDto) => User(0, userDto.email, userDto.name, userDto.surname)
}

object UserService extends UserService {

  override val userDao = UserDao

  override val passwordDao = PasswordDao

  override def add(user: UserDto): Future[Option[User]] = db.run {
    for {
      passwordId <- passwordDao.add(UserPassword newWithPassword user.password)
      userId <- userDao.add(populateUser(user).copy(passwordId = Some(passwordId)))
      // "This DBMS allows only a single AutoInc"
      // H2 doesn't allow return the whole user once created so we have to do this instead of returning the object from
      // the dao on inserting
      user <- UserDao.get(userId)
    } yield user
  }

  override def getAll(): Future[Seq[User]] = db.run {
    userDao.getAll
  }

  override def get(id: Int): Future[Option[User]] = db.run {
    userDao.get(id)
  }

  override def get(email: String): Future[Option[(User, UserPassword)]] = db.run {
    userDao.get(email)
  }

  override def delete(id: Int):Future[Int] = db.run {
    userDao.delete(id)
  }
}
