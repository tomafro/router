package uk.gov.gds.router.configuration

object RouterConfig {

  def databaseHosts: List[String] = Config.setting("mongo.database.hosts").split(",").toList
}