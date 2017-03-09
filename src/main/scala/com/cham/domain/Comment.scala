package com.cham.domain

import java.util.Date

/**
  * Created by cwijayasundara on 08/03/2017.
  */

class Comment(commentId:CommentId,
              blogId:BlogId,
              authorId:AuthorId,
              comment:Text,
              createdDateTime:Date)
