package utils

import com.typesafe.config.ConfigFactory


object Config {
  private val config =  ConfigFactory.load()

  object app {
    val appConf = config.getConfig("app")

    val systemName = appConf.getString("systemName")
    val interface = appConf.getString("interface")
    val port = appConf.getInt("port")
    val userServiceName = appConf.getString("userServiceName")

  }


  object dbConfig {
    val dbConfig = config.getConfig("db")

    val url = dbConfig.getString("url")
    val user = dbConfig.getString("user")
    val password = dbConfig.getString("password")
    val driver = dbConfig.getString("driver")
  }
}
