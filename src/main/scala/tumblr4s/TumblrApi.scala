/**
 * Copyright (c) 2012 Orr Sella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tumblr4s

import java.io.File
import org.json4s._
import org.json4s.native.JsonMethods._
import tumblr4s.http._
import tumblr4s.model._
import tumblr4s.util._

/**
 * The Tumblr API: http://www.tumblr.com/docs/en/api/v2
 *
 * @param apiKey       Application's API key (the same as the OAuth consumer key)
 * @param apiSecret    Application's OAuth consumer secret
 * @param accessKey    User OAuth access token key
 * @param accessSecret User OAuth access token secret
 */
class TumblrApi(
    apiKey: String,
    apiSecret: Option[String] = None,
    accessKey: Option[String] = None,
    accessSecret: Option[String] = None) {

  this: HttpClient =>
  private val BaseUrl = "http://api.tumblr.com/v2"
  private implicit val formats = DefaultFormats

  /**
   * Retrieve blog info
   *
   * @param baseHostname The standard or custom blog hostname
   */
  def getBlogInfo(baseHostname: String): Blog = {
    val url = BaseUrl + "/blog/" + baseHostname + "/info"
    getJson(ApiAuth, GET, url) \ "response" \ "blog"
  }

  /**
   * Returns a blog's avatar
   *
   * @param baseHostname The standard or custom blog hostname
   * @param size         The size of the avatar (square, one value for both length and width)
   */
  def getBlogAvatar(baseHostname: String, size: AvatarSize = AvatarSize64): Avatar =
    new Avatar(BaseUrl + "/blog/" + baseHostname + "/avatar/" + size.size)

  /**
   * Retrieve a blog's likes
   *
   * @param baseHostname The standard or custom blog hostname
   */
  def getBlogLikes(baseHostname: String, limit: Int = 20, offset: Int = 0): BlogLikes = {
    val url = BaseUrl + "/blog/" + baseHostname + "/likes"
    val params: Map[String, String] = Map("limit" -> limit, "offset" -> offset)
    val json = getJson(ApiAuth, GET, url, params) \ "response"
    new BlogLikes(json \ "likedPosts", json \ "likedCount")
  }

  /**
   * Retrieve a blog's followers
   *
   * @param baseHostname The standard or custom blog hostname
   * @param limit        The number of results to return: 1–20, inclusive
   * @param offset       Result to start at (0 = first follower)
   */
  def getBlogFollowers(baseHostname: String, limit: Int = 20, offset: Int = 0): BlogFollowers = {
    val url = BaseUrl + "/blog/" + baseHostname + "/followers"
    val params: Map[String, String] = Map("limit" -> limit, "offset" -> offset)
    getJson(OAuth, GET, url, params) \ "response" transformField {
      case JField("totalUsers", JString(value)) => JField("totalUsers", JInt(value.toInt)) // cast string to int
    }
  }

  /**
   * Retreive a specific post
   *
   * @param id                The id of the requested post
   * @param baseHostname      The standard or custom blog hostname
   * @param includeReblogInfo Indicates whether to return reblog information. Returns the various reblogged_ fields.
   * @param includeNotesInfo  Indicates whether to return notes information. Returns note count and note metadata.
   */
  def getBlogPost(
    id: Long,
    baseHostname: String,
    includeReblogInfo: Boolean = false,
    includeNotesInfo: Boolean = false): Post = {

    val url = BaseUrl + "/blog/" + baseHostname + "/posts";
    val params: Map[String, String] = Map("id" -> id, "reblog_info" -> includeReblogInfo, "notes_info" -> includeNotesInfo)
    val posts: Seq[Post] = getJson(OAuthOrApiAuth, GET, url, params) \ "response" \ "posts"

    posts match {
      case post :: _ => post
      case _ => throw new TumblrApiException(404, "Not Found", "", new Exception())
    }
  }

  /**
   * Retreive a blog's published posts
   *
   * @param baseHostname      The standard or custom blog hostname
   * @param postType          The type of post to return
   * @param tag               Limits the response to posts with the specified tag
   * @param limit             The number of posts to return: 1–20, inclusive
   * @param offset            Post number to start at (0 = first post)
   * @param includeReblogInfo Indicates whether to return reblog information. Returns the various reblogged_ fields.
   * @param includeNotesInfo  Indicates whether to return notes information. Returns note count and note metadata.
   * @param filter            Specifies the post format to return, other than HTML
   */
  def getBlogPosts(
    baseHostname: String,
    postType: PostType = AnyPostType,
    tag: String = "",
    limit: Int = 20,
    offset: Int = 0,
    includeReblogInfo: Boolean = false,
    includeNotesInfo: Boolean = false,
    filter: PostFilter = HtmlFilter): BlogPosts = {

    val url = BaseUrl + "/blog/" + baseHostname + "/posts/" + postType
    val params: Map[String, String] =
      Map(
        "tag" -> tag,
        "limit" -> limit,
        "offset" -> offset,
        "reblog_info" -> includeReblogInfo,
        "notes_info" -> includeNotesInfo,
        "filter" -> filter)

    val json = getJson(OAuthOrApiAuth, GET, url, params) \ "response"
    new BlogPosts(json \ "blog", json \ "posts", json \ "totalPosts")
  }

  /**
   * Retreive queued posts
   *
   * @param baseHostname The standard or custom blog hostname
   * @param limit        The number of posts to return: 1–20, inclusive
   * @param offset       Post number to start at (0 = first post)
   * @param filter       Specifies the post format to return, other than HTML
   */
  def getQueuedPosts(
    baseHostname: String,
    limit: Int = 20,
    offset: Int = 0,
    filter: PostFilter = HtmlFilter): Seq[Post] = {

    val url = BaseUrl + "/blog/" + baseHostname + "/posts/queue"
    val params: Map[String, String] = Map("limit" -> limit, "offset" -> offset, "filter" -> filter)
    getJson(OAuth, GET, url, params) \ "response" \ "posts"
  }

  /**
   * Retreive draft posts
   *
   * @param baseHostname The standard or custom blog hostname
   * @param filter       Specifies the post format to return, other than HTML
   */
  def getDraftPosts(baseHostname: String, filter: PostFilter = HtmlFilter): Seq[Post] = {
    val url = BaseUrl + "/blog/" + baseHostname + "/posts/draft"
    val params: Map[String, String] = Map("filter" -> filter)
    getJson(OAuth, GET, url, params) \ "response" \ "posts"
  }

  /**
   * Retreive submission posts
   *
   * @param baseHostname The standard or custom blog hostname
   * @param limit        The number of posts to return: 1–20, inclusive
   * @param offset       Post number to start at (0 = first post)
   * @param filter       Specifies the post format to return, other than HTML
   */
  def getSubmissionPosts(
    baseHostname: String,
    limit: Int = 20,
    offset: Int = 0,
    filter: PostFilter = HtmlFilter): Seq[Post] = {

    val url = BaseUrl + "/blog/" + baseHostname + "/posts/submission?offset=" + offset + "&filter=" + filter
    val params: Map[String, String] = Map("limit" -> limit, "offset" -> offset, "filter" -> filter)
    getJson(OAuth, GET, url, params) \ "response" \ "posts"
  }

  /**
   * Create a new blog post
   *
   * @param postParams The parameters object for creating the new post
   * @return           The newly created post id
   */
  def post(postParams: PostParams): Long = {
    require(postParams.isValid, "Invalid post parameters, make sure all required field are supplied")
    val url = BaseUrl + "/blog/" + postParams.baseHostname + "/post"
    val params = postParams.toMap
    val files = postParams.toFilesMap
    getJson(OAuth, POST, url, params, files) \ "response" \ "id"
  }

  /**
   * Edit an existing blog post
   *
   * @param id         The ID of the post to edit
   * @param postParams The parameters object for editing the post
   * @return           The edited post id
   */
  def editPost(id: Long, postParams: PostParams): Long = {
    require(postParams.isValid, "Invalid post parameters, make sure all required field are supplied")
    val url = BaseUrl + "/blog/" + postParams.baseHostname + "/post/edit"
    val params = postParams.toMap ++ Map("id" -> id.toString)
    val files = postParams.toFilesMap
    getJson(OAuth, POST, url, params, files) \ "response" \ "id"
  }

  /**
   * Reblog a post
   *
   * @param id         The ID of the reblogged post on tumblelog
   * @param id         The reblog key for the reblogged post
   * @param comment    A comment added to the reblogged post
   * @param postParams The parameters object for editing the post
   * @return           The edited post id
   */
  def reblog(id: Long, reblogKey: String, comment: String, postParams: ReblogPostParams): Long = {
    require(postParams.isValid, "Invalid post parameters, make sure all required field are supplied")
    val url = BaseUrl + "/blog/" + postParams.baseHostname + "/post/reblog"
    val params = postParams.toMap ++ Map("id" -> id.toString, "reblog_key" -> reblogKey, "comment" -> comment)
    getJson(OAuth, POST, url, params) \ "response" \ "id"
  }

  /**
   * Delete a post
   *
   * @param id           The ID of the post to delete
   * @param baseHostname The standard or custom blog hostname
   * @return             The deleted post id
   */
  def deletePost(id: Long, baseHostname: String): Long = {
    val url = BaseUrl + "/blog/" + baseHostname + "/post/delete"
    val params = Map("id" -> id.toString)
    getJson(OAuth, POST, url, params) \ "response" \ "id"
  }

  /**
   * Get a uer's information
   *
   * Use this method to retrieve the user's account information that matches the OAuth credentials submitted
   * with the request.
   */
  def getUserInfo(): User = {
    val url = BaseUrl + "/user/info"
    getJson(OAuth, GET, url) \ "response" \ "user"
  }

  /**
   * Retrieve a user's dashboard
   *
   * Use this method to retrieve the dashboard that matches the OAuth credentials submitted with the request.
   *
   * @param postType          The type of post to return
   * @param limit             The number of posts to return: 1–20, inclusive
   * @param offset            Post number to start at (0 = first post)
   * @param sinceId           Retun posts that have appeared after this id
   * @param includeReblogInfo Indicates whether to return reblog information. Returns the various reblogged_ fields.
   * @param includeNotesInfo  Indicates whether to return notes information. Returns note count and note metadata.
   */
  def getUserDashboard(
    postType: PostType = AnyPostType,
    limit: Int = 20,
    offset: Int = 0,
    sinceId: Int = 0,
    includeReblogInfo: Boolean = false,
    includeNotesInfo: Boolean  = false): Seq[Post] = {

    val url = BaseUrl + "/user/dashboard"
    val params: Map[String, String] =
      Map(
        "limit" -> limit,
        "offset" -> offset,
        "type" -> postType,
        "since_id" -> sinceId,
        "reblog_info" -> includeReblogInfo,
        "notes_info" -> includeNotesInfo)

    getJson(OAuth, GET, url, params) \ "response" \ "posts"
  }

  /**
   * Retrieve a user's likes
   *
   * Use this method to retrieve the liked posts that match the OAuth credentials submitted with the request.
   *
   * @param limit  The number of results to return: 1–20, inclusive
   * @param offset Result to start at (0 = first post)
   */
  def getUserLikes(limit: Int = 20, offset: Int = 0): UserLikes = {
    val url = BaseUrl + "/user/likes"
    val params: Map[String, String] = Map("limit" -> limit, "offset" -> offset)
    val json = getJson(OAuth, GET, url, params) \ "response"
    new UserLikes(json \ "likedPosts", json \ "likedCount")
  }

  /**
   * Retrieve the blogs a user is following
   *
   * Use this method to retrieve the blogs followed by the user whose OAuth credentials are submitted with the request.
   *
   * @param limit  The number of results to return: 1–20, inclusive
   * @param offset Result to start at (0 = first followee)
   */
  def getUserFollowing(limit: Int = 20, offset: Int = 0): UserFollowing = {
    val url = BaseUrl + "/user/following"
    val params: Map[String, String] = Map("limit" -> limit, "offset" -> offset)
    getJson(OAuth, GET, url, params) \ "response"
  }

  /**
   * Follow a blog
   *
   * @param blogUrl The URL of the blog to follow
   */
  def follow(blogUrl: String): Unit = {
    val url = BaseUrl + "/user/follow"
    val params: Map[String, String] = Map("url" -> blogUrl)
    getJson(OAuth, POST, url, params)
  }

  /**
   * Unfollow a blog
   *
   * @param blogUrl The URL of the blog to follow
   */
  def unfollow(blogUrl: String): Unit = {
    val url = BaseUrl + "/user/unfollow"
    val params: Map[String, String] = Map("url" -> blogUrl)
    getJson(OAuth, POST, url, params)
  }

  /**
   * Like a post
   *
   * @param postId    The ID of the post to like
   * @param reblogKey The reblog key for the post id
   */
  def like(postId: Long, reblogKey: String): Unit = {
    val url = BaseUrl + "/user/like"
    val params: Map[String, String] = Map("id" -> postId, "reblog_key" -> reblogKey)
    getJson(OAuth, POST, url, params)
  }

  /**
   * Unlike a post
   *
   * @param postId    The ID of the post to unlike
   * @param reblogKey The reblog key for the post id
   */
  def unlike(postId: Long, reblogKey: String): Unit = {
    val url = BaseUrl + "/user/unlike"
    val params: Map[String, String] = Map("id" -> postId, "reblog_key" -> reblogKey)
    getJson(OAuth, POST, url, params)
  }

  /**
   * Get posts with tag
   *
   * @param tag    The tag on the post syou'd like to retrieve
   * @param before The timestamp of when you'd like to see posts before
   * @param limit  The number of results to return: 1–20, inclusive
   * @param filter Specifies the post format to return, other than HTML
   */
  def getTaggedPosts(
    tag: String,
    before: Option[Int] = None,
    limit: Int = 20,
    filter: PostFilter = HtmlFilter): Seq[Post] = {

    val url = BaseUrl + "/tagged"
    val params: Map[String, String] =
      Map("tag" -> tag, "before" -> before.getOrElse(""), "limit" -> limit, "filter" -> filter)

    getJson(OAuthOrApiAuth, GET, url, params) \ "response"
  }

  ///////////////////////
  //                   //
  // Private Functions //
  //                   //
  ///////////////////////

  implicit private def anyToString(value: Any): String = value.toString
  implicit private def jValueToInt(value: JValue): Int = value.extract[Int]
  implicit private def jValueToLong(value: JValue): Long = value.extract[Long]
  implicit private def jValueToPost(value: JValue): Post = value.extract[Post]
  implicit private def jValueToUser(value: JValue): User = value.extract[User]
  implicit private def jValueToBlog(value: JValue): Blog = value.extract[Blog]
  implicit private def jValueToUserLikes(value: JValue): UserLikes = value.extract[UserLikes]
  implicit private def jValueToBlogFollowers(value: JValue): BlogFollowers = value.extract[BlogFollowers]
  implicit private def jValueToUserFollowing(value: JValue): UserFollowing = value.extract[UserFollowing]
  implicit private def jValueToPostSeq(value: JValue): Seq[Post] = value.children map extractPostByType

  private def extractPostByType(post: JValue): Post = {
    val postType = (post \ "type").extract[String]
    postType match {
      case "text" => post.extract[TextPost]
      case "photo" => post.extract[PhotoPost]
      case "quote" => post.extract[QuotePost]
      case "link" => post.extract[LinkPost]
      case "chat" => post.extract[ChatPost]
      case "audio" => post.extract[AudioPost]
      case "video" => post.extract[VideoPost]
      case "answer" => post.extract[AnswerPost]
      case _ => throw new Exception("Attempting to parse an unknown post type: '" + postType + "'")
    }
  }

  private sealed trait AuthMethod
  private case object ApiAuth extends AuthMethod
  private case object OAuth extends AuthMethod
  private case object OAuthOrApiAuth extends AuthMethod

  private def getJson(
    auth: AuthMethod,
    method: HttpMethod,
    url: String,
    params: Map[String, String] = Map(),
    files: Map[String, String] = Map()): JValue = {

    val paramsWithApiKey = params ++ Map("api_key" -> apiKey)
    val response = auth match {
      case ApiAuth => makeRequest(method, url, paramsWithApiKey, files)
      case OAuth => (apiSecret, accessKey, accessSecret) match {
        case (Some(s), Some(key), Some(secret)) =>
          makeOAuthRequest(method, url, paramsWithApiKey, files, apiKey, s, key, secret)
        case _ => throw new TumblrApiOAuthException()
      }
      case OAuthOrApiAuth => (apiSecret, accessKey, accessSecret) match {
        case (Some(s), Some(key), Some(secret)) =>
        makeOAuthRequest(method, url, paramsWithApiKey, files, apiKey, s, key, secret)
        case _ => makeRequest(method, url, paramsWithApiKey, files)
      }
    }
    JsonHelper.transformToCamelCaseKeys(parse(response))
  }
}

/**
 * Companion object for TumblrApi providing factory methods with a default implementation of HttpClient
 */
object TumblrApi {
  def apply(apiKey: String) =
    new TumblrApi(apiKey, None, None, None) with DispatchHttpClient

  def apply(apiKey: String, apiSecret: String, accessTokenKey: String, accessTokenSecret: String) =
    new TumblrApi(apiKey, Some(apiSecret), Some(accessTokenKey), Some(accessTokenSecret)) with DispatchHttpClient
}