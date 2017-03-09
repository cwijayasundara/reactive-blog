DROP TABLE IF EXISTS blog_by_author;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS comment_by_blog;

CREATE TABLE blog_by_author (
  blogid text PRIMARY KEY,
  authorid text,
  authorname text,
  blogtitle text,
  blogcontent text,
  subjectarea text,
  comments text,
  createddatetime timestamp
) WITH comment='technical blogs'
  AND COMPACT STORAGE
  AND read_repair_chance = 1.0;

CREATE TABLE author (
authorid text PRIMARY KEY,
authorname text,
emailaddress text,
blogs text,
comments text
) WITH comment='blog author'
       AND COMPACT STORAGE
AND read_repair_chance = 1.0;

CREATE TABLE comment_by_blog (
commentid text PRIMARY KEY,
blogid text,
authorid text,
comment text,
createddatetime timestamp
) WITH comment='blog comment'
       AND COMPACT STORAGE
AND read_repair_chance = 1.0;