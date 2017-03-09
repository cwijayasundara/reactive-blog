package com.cham.dao

import java.text.SimpleDateFormat

import akka.actor.Actor
import com.cham.core.Keyspaces
import com.cham.dao.BlogReaderActor.{CountAllBlogs, FindAllBlogs, findBlog}
import com.cham.domain.Blog
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.{BoundStatement, Cluster, Row}

/**
  * Created by cwijayasundara on 08/03/2017.
  */

class BlogReaderActor(cluster:Cluster) extends Actor {

  val session = cluster.connect(Keyspaces.blog)

  val countAll  = new BoundStatement(session.prepare("select count(*) from blog_by_author;"))

  val findBlogsById = new BoundStatement(session.prepare("select * from blog_by_author where blogid ='1'")) // remove the hard coding

  import scala.collection.JavaConversions._
  import com.cham.cassandrautil.cassandra.resultset._
  import context.dispatcher
  import akka.pattern.pipe
  import java.util.Date

  def buildBlogObj(r:Row): Blog ={
    val blogId = r.getString("blogid")
    val authorId = r.getString("authorid")
    val authorName = r.getString("authorname")
    val blogTitle = r.getString("blogtitle")
    val blogContent = r.getString("blogcontent")
    val subjectArea = r.getString("subjectarea")
    val comment=r.getString("comments")
    val createdAt = r.getDate("createddatetime")
    val date : Date = new SimpleDateFormat("yyyy-MM-dd").parse(createdAt.toString())
    Blog(blogId,authorId,authorName,blogTitle,blogContent,subjectArea,comment,date)
  }

  def receive: Receive = {

    case FindAllBlogs(maximum:Int)  => {
      println("Inside the FindAllBlogs() of the BlogReaderActor..")
      val query = QueryBuilder.select().all().from(Keyspaces.blog, "blog_by_author").limit(maximum)
      session.executeAsync(query) map (_.all().map(buildBlogObj).toVector) pipeTo sender
    }

    case CountAllBlogs => {
      println("Inside the CountAllBlogs() of the BlogReaderActor..")
      session.executeAsync(countAll) map (_.one.getLong(0)) pipeTo sender
    }

    case findBlog(blogId:String) => {
      println("Inside findBlog() of the BlogReaderActor..")
      session.executeAsync(findBlogsById) map (_.one.getLong(0)) pipeTo sender
    }
  }
}

object BlogReaderActor{
  case class FindAllBlogs(maximum: Int = 100)
  case class findBlog(blogId:String)
  case object CountAllBlogs
}
