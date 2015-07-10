package utils

import utils.Config.dbConfig._
import slick.driver.{ H2Driver, JdbcProfile, PostgresDriver }
import slick.jdbc.JdbcBackend._

//Based on play-slick driver loader
object DatabaseConfig {
  lazy val db: Database = Database.forURL(url, user, password, driver = driver)
  lazy val profile: JdbcProfile = driver match {
    case "org.postgresql.Driver" => PostgresDriver
    case "org.h2.Driver" => H2Driver
  }
}
