package com.cham

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import java.util.Date

import com.cham.cassandrautil.ConfigCassandraCluster
import com.cham.domain.Blog
import com.cham.service.BlogServiceActor
/**
  * Created by cwijayasundara on 08/03/2017.
  */
object ServiceTestMain extends App with ConfigCassandraCluster{

  // load the configs from application.config
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  // create the actor system and dispatcher. Actor system creates a non demon thread that keeps running
  implicit lazy val system = ActorSystem()
  implicit val dispatcher = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val blogServiceActor = system.actorOf(Props(new BlogServiceActor(system,cluster)))

  val log =  Logging(system.eventStream, "blogapp")

  val testBlog:Blog = new Blog("1","auth_1","Chaminda","Scala","Scala is great..","programming","good Scala blog",new Date())

  blogServiceActor ! BlogServiceActor.CreateBlog(testBlog)
  blogServiceActor ! BlogServiceActor.GetAllBlogs
  blogServiceActor ! BlogServiceActor.GetBlogCount
  blogServiceActor ! BlogServiceActor.GetBlog("1")

}
