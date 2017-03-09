package com.cham.service

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.cham.dao.{BlogReaderActor, BlogWriterActor}
import com.cham.domain.Blog
import com.datastax.driver.core.Cluster
import com.cham.service.BlogServiceActor._
/**
  * Created by cwijayasundara on 08/03/2017.
  */

object BlogServiceActor{
  case class CreateBlog(blog:Blog)
  case class CreateBlogs(blogs: Vector[Blog])
  case class GetAllBlogs(limit:Int)
  case class GetBlog(blogId:String)
  case class GetBlogCount()
  case class BlogResponse(blog:Blog)
  case class Blogs(blogs: Vector[Blog])
  case class CreateBlogResponse(blogs: Vector[Blog])
}

class BlogServiceActor(system:ActorSystem,cluster:Cluster) extends Actor{

  val blogWriter = system.actorOf(Props(new BlogWriterActor(cluster)))
  val blogReader = system.actorOf(Props(new BlogReaderActor(cluster)))

  def receive: Receive = {

    case CreateBlog(blog:Blog) => blogWriter ! BlogWriterActor.CreateBlog(blog)

    case CreateBlogs(blogs:Vector[Blog]) => blogWriter ! BlogWriterActor.CreateBlogs(blogs)

    case GetAllBlogs(limit:Int) =>
    {
      def notFound() = sender() ! None
      def getAllBlogs(child:ActorRef) = child forward BlogReaderActor.FindAllBlogs(limit)
      context.child("blogs").fold(notFound())(getAllBlogs)
    }

    case GetBlogCount =>
    {
      def notFound() = sender() ! None
      def getBlogCount(child: ActorRef) = child forward BlogReaderActor.CountAllBlogs
      context.child("count").fold(notFound())(getBlogCount)
    }

    case GetBlog(blogId) => {
      def notFound() = sender() ! None
      def getBlogByBlogId(child: ActorRef) = child forward BlogReaderActor.findBlog("1") // to do remove the hard coding
      context.child(blogId).fold(notFound())(getBlogByBlogId)
    }
  }
}
