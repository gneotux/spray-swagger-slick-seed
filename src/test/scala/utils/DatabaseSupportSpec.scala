package utils

import dao.{ UserDao, PasswordDao }
import model.{ User, UserPassword }
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterEach
import utils.DatabaseConfig._
import utils.DatabaseConfig.profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DatabaseSupportSpec {

  lazy val passwords = Seq(
    UserPassword(1, Some("$2a$10$U3gBQ50FY5qiQ5XeQKgWwO6AADKjaGqh/6l3RzWitAWelWCQxffUC"), "$2a$10$U3gBQ50FY5qiQ5XeQKgWwO"),
    UserPassword(2, Some("$2a$10$U3gBQ50FY5qiQ5XeQKgWwO6AADKjaGqh/6l3RzWitAWelWCQxffUC"), "$2a$10$U3gBQ50FY5qiQ5XeQKgWwO"),
    UserPassword(3, Some("$2a$10$U3gBQ50FY5qiQ5XeQKgWwO6AADKjaGqh/6l3RzWitAWelWCQxffUC"), "$2a$10$U3gBQ50FY5qiQ5XeQKgWwO")
  )
  lazy val users = Seq(
    User(1, "test1@test.com", Some("name1"), Some("surname1"), Some(1)),
    User(2, "test2@test.com", Some("name2"), Some("surname2"), Some(2)),
    User(3, "test3@test.com", Some("name3"), Some("surname3"), Some(3))
  )
}

trait SpecSupport extends Specification with BeforeAfterEach {

  def createSchema = {
    val results = db.run(
      DBIO.seq(
        (PasswordDao.passwords.schema ++ UserDao.users.schema).create,
        PasswordDao.passwords ++= DatabaseSupportSpec.passwords,
        UserDao.users ++= DatabaseSupportSpec.users
      ))

    Await.result(results, Duration.Inf)
  }

  def dropSchema = {
    val results = db.run((PasswordDao.passwords.schema ++ UserDao.users.schema).drop)
    Await.result(results, Duration.Inf)
  }
  override def before: Unit={
    createSchema
  }

  override def after: Unit = {
    dropSchema
  }
}

