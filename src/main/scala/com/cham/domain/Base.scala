package com.cham.domain

/**
  * Created by cwijayasundara on 08/03/2017.
  */

case class Text(text: String) extends AnyVal

object Text {
  implicit def toText(text: String): Text = Text(text)
}

case class CommentId(id: String) extends AnyVal

object CommentId {
  implicit def toCommentId(id: String): CommentId = CommentId(id)
}

case class BlogId(id: String) extends AnyVal

object BlogId {
  implicit def toBlogId(id: String): BlogId = BlogId(id)
}

case class AuthorId(id: String) extends AnyVal

object AuthorId {
  implicit def toAuthorId(id: String): AuthorId = AuthorId(id)
}

case class Base()