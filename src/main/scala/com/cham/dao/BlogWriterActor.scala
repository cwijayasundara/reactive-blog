package com.cham.dao

import akka.actor.Actor
import com.cham.core.Keyspaces
import com.cham.dao.BlogWriterActor.{CreateBlog, CreateBlogs}
import com.cham.domain.Blog
import com.datastax.driver.core.Cluster

/**
  * Created by cwijayasundara on 08/03/2017.
  */
class BlogWriterActor(cluster:Cluster) extends Actor {

  val session = cluster.connect(Keyspaces.blog)

  val insertSql = "INSERT INTO blog_by_author(blogid, authorid,authorname,blogtitle,blogcontent,subjectarea," +
                   "comments,createddatetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"

  val preparedStatement = session.prepare(insertSql)

  def createBlog(blog: Blog): Unit = {
    printf("Inside createBlog() of BlogWriterActor")
    session.executeAsync(preparedStatement.bind
     (blog.blogid,
      blog.authorId,
      blog.authorName,
      blog.blogTitle,
      blog.blogContent,
      blog.subjectArea,
      blog.comments,
      blog.createdDateTime))
  }

  // Actors receive method.
  def receive: Receive = {
    case CreateBlog(blog:Blog) => createBlog(blog)
    case CreateBlogs(blogs:Vector[Blog]) => {
          blogs.foreach(createBlog)}
  }
}

object BlogWriterActor{
  case class CreateBlog(blog:Blog)
  case class CreateBlogs(blogs:Vector[Blog])
}

