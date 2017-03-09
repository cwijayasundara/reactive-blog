package com.cham.domain

/**
  * Created by cwijayasundara on 08/03/2017.
  */

import java.util.Date

case class Blog(blogid: BlogId,
                authorId: AuthorId,
                authorName: Text,
                blogTitle: Text,
                blogContent : Text,
                subjectArea:Text,
                comments:Text,
                createdDateTime: Date)
