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

package com.orrsella.tumblr4s

// import com.twitter.logging._
import model._
// import http._
// import util.JsonHelper
import java.io.File

object Main {
  private val apiKey = "ogl2MZgUxcjXF6sC1YePtBjnsb55L4PLYVrzPxrUDvEfSU7MaY"
  private val oauthConsumerKey = "ogl2MZgUxcjXF6sC1YePtBjnsb55L4PLYVrzPxrUDvEfSU7MaY"
  private val apiSecret = "Dt7gK2efttMStVCYoIZDuHWBHwsO6h46HjX04rJWtNqTxuAG9Y"
  private val accessTokenKey = "39KoO81BxdTeIum0PSh7B1UmhE0nDmoBduhFmdxofOMKd5fH2Z"
  private val accessTokenSecret = "d3anp5NhZiBRW0NWNuUYZWu5P1IdHuFShfTjdu757X0t8oeWLG"

  private val accessTokenKey2 = "UoH1jLR4dNsYNCaoAbuMT6Facc7S81FRx1Y529eCgjDnNJWUHB"
  private val accessTokenSecret2 = "uFAPXGglUvTHh6YlcSuGmFIsfYWhrOFfbTn7c7zNmgIVKHMwiI"

  // private val baseHostname = "staff.mobocracy.net"

  // implicit val formats = net.liftweb.json.DefaultFormats

  def main(args: Array[String]) {

    // val log = new LoggerFactory(
    //  node = "com.orrsella",
    //  level = Some(Level.DEBUG),
    //  handlers = ConsoleHandler() :: Nil
    // ).apply()

    val tumblr = TumblrApi(apiKey, apiSecret, accessTokenKey, accessTokenSecret) // with OAuth capabilities
    println(tumblr.getBlogInfo("orrsella.com"))
    // val params = new PhotoPostParams("orrsella.com", data = List("/Users/orr/Pictures/Wallpapers/test2.jpg"), state = DraftPostState, tweet = "off")
    // println(tumblr.post(params))


//     // println(tumblr.getBlogInfo("parislemon2.com"))
//     println(tumblr.getBlogPost(37599553345L, "parislemon.com"))


// import com.orrsella.tumblr4s.http.{HttpClient, HttpMethod}

// trait MyHttpClient extends HttpClient {
//   def makeRequest(
//     method: HttpMethod,
//     requestUrl: String,
//     params: Map[String, String],
//     files: Map[String, String]): String = sys.error("Not implemented")

//   def makeOAuthRequest(
//     method: HttpMethod,
//     requestUrl: String,
//     params: Map[String, String],
//     files: Map[String, String],
//     consumerKey: String,
//     consumerSecret: String,
//     accessKey: String,
//     accessSecret: String): String = sys.error("Not implemented")
// }

// val myTumblr = new TumblrApi(apiKey) with MyHttpClient
//   }
  }
}
