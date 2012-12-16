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

import org.scalatest.FlatSpec
import tumblr4s.http._
import tumblr4s.model._

class TumblrApiSpec extends FlatSpec {
  val baseHostname = "staff.tumblr.com"
  val apiKey = "key"
  val apiSecret = "secret"

  trait DummyHttpClient extends HttpClient {
    def makeRequest(
      method: HttpMethod,
      requestUrl: String,
      params: Map[String, String],
      files: Map[String, String]): String = ""

    def makeOAuthRequest(
      method: HttpMethod,
      requestUrl: String,
      params: Map[String, String],
      files: Map[String, String],
      consumerKey: String,
      consumerSecret: String,
      accessKey: String,
      accessSecret: String): String = ""
  }

  val tumblrNoOAuth = new TumblrApi(apiKey, None, None, None) with DummyHttpClient

  "TumblrApi without OAuth credentials" should "throw OAuth exception for required methods" in {
    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getBlogFollowers(baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getQueuedPosts(baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getDraftPosts(baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getSubmissionPosts(baseHostname)
    }

    val params = new TextPostParams(baseHostname, "title", "body")

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.post(params)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.editPost(1L, params)
    }

    val reblogParams = new ReblogPostParams(baseHostname)

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.reblog(1L, "reblogkey", "comment", reblogParams)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.deletePost(1L, baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getUserInfo()
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getUserDashboard()
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getUserLikes()
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.getUserFollowing()
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.follow(baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.unfollow(baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.like(1L, baseHostname)
    }

    intercept[TumblrApiOAuthException] {
      tumblrNoOAuth.unlike(1L, baseHostname)
    }
  }
}