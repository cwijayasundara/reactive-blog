package com.cham.domain

/**
  * Created by cwijayasundara on 08/03/2017.
  */

import java.util.Date

case class Blog(blogid: String,
                authorId: String,
                authorName: String,
                blogTitle: String,
                blogContent : String,
                subjectArea:String,
                comments:String,
                createdDateTime: Date)
