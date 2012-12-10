# tumblr4s

A simple and (hopefully) idiomatic Scala library for the [Tumblr API](http://www.tumblr.com/docs/en/api/v2). Provides all API functionality in native Scala code and objects (no json responses). See [this post](http://orrsella.com/post/XXXXXX) for some more background.

## Building

Use [sbt](http://www.scala-sbt.org/) (Simple Build Tool) to build.

## Dependencies

The following libraries are used:

* [Dispatch 0.8.8 ("dispatch-classic")](http://dispatch-classic.databinder.net/Dispatch.html) for making HTTP requests (http, oauth and mime modules)

* [json4s 3.0.0](http://json4s.org/) for json parsing (essentially "lift-json but outside of the lift project")

## Usage

### Authentication

Working with the Tumblr API requires an API key (which is identical to the OAuth Consumer Key). If you don't have one go [register an application](http://www.tumblr.com/oauth/apps). "API key" authenticated methods only require the API key, while "OAuth" authenticated methods also require the Secret Key and a user's access token key and access token secret (see [the Authentication section](http://www.tumblr.com/docs/en/api/v2#auth) for more details.)

The `TumblrApi` class encapsulates all functionality found in the API. Construct it using the companion object:

```scala
import tumblr4s._

val tumblr = TumblrApi(apiKey) // without OAuth capabilities
```

or

```scala
val tumblr = TumblrApi(apiKey, apiSecret, accessTokenKey, accessTokenSecret) // with OAuth capabilities
```

If you'll try to use an OAuth required method without the credentials supplied (first example), you'll get a `TumblrApiOAuthException`.

### Querying

All methods mirror the available [API functionality](http://www.tumblr.com/docs/en/api/v2) and have similar names. Here are some examples, with partial parameters (many more are available):

```scala
import tumblr4s.model._

try {
  val baseHostname = "staff.tumblr.com" // a standard or custom blog hostname, see:
                                        // http://www.tumblr.com/docs/en/api/v2#hostname
  val blog: Blog = tumblr.getBlogInfo(baseHostname)
  val followers: BlogFollowers = tumblr.getBlogFollowers(baseHostname, limit = 20, offset = 60)
  val post: Post = tumblr.getBlogPost(37192020774L, baseHostname)
  val posts: Seq[Post] = tumblr.getDraftPosts(baseHostname)

  val user: User = tumblr.getUserInfo() // user == the authenticated OAuth user (not some public blog)
  val dashboard: Seq[Post] = tumblr.getUserDashboard(postType = QuotePostType, includeNotesInfo = true)
  // ...
} catch {
  case TumblrApiOAuthException() => // tried to invoke an OAuth required method without needed credentials
  case TumblrApiException(400, message, contents, cause) => // Bad Request
  case TumblrApiException(401, message, contents, cause) => // Unauthorized
  case TumblrApiException(404, message, contents, cause) => // Not Found
  // ...
  case _ => // unknown error
}
```

### Posting

```scala
import java.io.File

try {
  val params1 = new TextPostParams(baseHostname, "Some Title", "Some <b>body</b>", DraftPostState, ...)
  val postId: Long = tumblr.post(params1)

  // update the created post and publish:
  val params2 = new TextPostParams(baseHostname, "Other Title", "Diff body", PublishPostState)
  val updatedPostId = tumblr.editPost(postId, params2) // updatedPostId == postId
  val deletedPostId = tumblr.deletePost(postId, baseHostname)

  val photos: Seq[File] = List(new File("/path/to/file1"), new File("/path/to/file2"))
  val photoParams = new PhotoPostParams(baseHostname, data = photos) // see known issues regarding photos
  val photoPostId = tumblr.post(photoParams)

  tumblr.follow("tumblr.mobocracy.net")
  tumblr.like(9666523557L, "ipd0veky")
  // ...
}
```

### HttpClient

The `TumblrApi` class uses the [Scala Cake Pattern](http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth/) to inject HTTP functionality via the `HttpClient` trait. This is mainly so you can easily use a different library, user-agent string, introduce throttling or proxy, etc. in it's implementation. It also helps with testing.

Constructing `TumblrApi` from it's companion object (as shown [above](#authentication)) automatically mixes in `DispatchHttpClient`, which – as you can imagine – uses Dispatch for it's implementation. If you'd like to implement your own version of `HttpClient`, simply do it like this:

```scala
import tumblr4s.http.{HttpClient, HttpMethod}

trait MyHttpClient extends HttpClient {
  def makeRequest(
    method: HttpMethod,
    requestUrl: String,
    params: Map[String, String],
    files: Map[String, File]): String = sys.error("Not Implemented")

  def makeOAuthRequest(
    method: HttpMethod,
    requestUrl: String,
    params: Map[String, String],
    files: Map[String, File],
    consumerKey: String,
    consumerSecret: String,
    accessKey: String,
    accessSecret: String): String = sys.error("Not Implemented")
}

val myTumblr = new TumblrApi(apiKey) with MyHttpClient
```

## Known Issues

* The Tumblr API documentation for [posting photos](http://www.tumblr.com/docs/en/api/v2#posting) states that the `data` parameter in the `/post` request can be "One or more image files (submit multiple times to create a slide show)" and is of type "Array (URL-encoded binary contents)". However, this functionality does not seem to work, no matter which approached was tried for the multi-file upload. It seems that the first file is the only one used. Regardless, the multi-file implementation was still kept (as `Seq[File]`), so future attempts to fix this problem won't change the library's public API. For the time being, know that __only the first image file in a PhotoPostParams request will be used__. If you are able to fix this, or have an idea on how to, please let me know.

## Feedback

Any comments/suggestions? Let me know what you think – I'd love to hear from you. Send pull requests or contact me: [@orrsella](http://twitter.com/orrsella) or [orrsella.com](http://orrsella.com)

## License

This software is licensed under the Apache 2 license, quoted below.

Copyright (c) 2012 Orr Sella

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
