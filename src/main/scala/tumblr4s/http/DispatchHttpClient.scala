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

package tumblr4s.http

import dispatch._
import dispatch.mime.Mime._
import dispatch.oauth.OAuth._
import dispatch.oauth.{Consumer, Token}
import java.io.File
import tumblr4s.TumblrApiException

/**
 * An implementation of HttpClient using databinder's Dispatch
 */
trait DispatchHttpClient extends HttpClient {

  // to disable apache commons logging, run:
  // System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

  /**
   * Make a request
   *
   * @param method     The http method to use
   * @param requestUrl The url to use
   * @param params     A map of string name-value parameters for the request
   * @param files      A map of files to be sent as multipart post (using dispatch mime:
   *                   https://github.com/dispatch/dispatch/blob/master/mime/src/main/scala/Mime.scala)
   */
  def makeRequest(method: HttpMethod, requestUrl: String, params: Map[String, String], files: Map[String, File]): String = {
    val http = new Http with NoLogging
    try {
      val request = url(requestUrl)

      method match {
        case GET => http(addFiles(request <<? params, files) as_str)
        case POST => http(addFiles(request << params, files) as_str)
      }
    } catch {
      case e @ StatusCode(code, contents) => throw TumblrApiException.fromJson(code, contents, e)
      case e: Exception => throw new TumblrApiException(-1, "Unknown error", "", e)
    } finally {
      http.shutdown()
    }
  }

  /**
   * Make an OAuth request
   *
   * @param method         The http method to use
   * @param requestUrl     The url to use
   * @param params         A map of string name-value parameters for the request
   * @param files          A map of files to be sent as multipart post (using dispatch mime:
   *                       https://github.com/dispatch/dispatch/blob/master/mime/src/main/scala/Mime.scala)
   * @param consumerKey    OAuth consumer key
   * @param consumerSecret OAuth consumer secret key
   * @param accessKey      OAuth access token key
   * @param accessSecret   OAuth access token secret
   */
  def makeOAuthRequest(
    method: HttpMethod,
    requestUrl: String,
    params: Map[String, String],
    files: Map[String, File],
    consumerKey: String,
    consumerSecret: String,
    accessKey: String,
    accessSecret: String): String = {

    val http = new Http with NoLogging
    try {
      val consumer = new Consumer(consumerKey, consumerSecret)
      val token = new Token(accessKey, accessSecret)
      val request = url(requestUrl)

      method match {
        case GET => http(addFiles(request <<? params <@ (consumer, token), files) as_str)
        case POST => http(addFiles(request << params <@ (consumer, token), files) as_str)
      }
    } catch {
      case e @ StatusCode(code, contents) => throw TumblrApiException.fromJson(code, contents, e)
      case e: Exception => throw new TumblrApiException(-1, "Unknown error", "", e)
    } finally {
      http.shutdown()
    }
  }

  /**
   * Add files to request
   */
  private def addFiles(request: Request, files: Map[String, File]): Request = {
    if (files.isEmpty) request
    else addFiles(request, files.tail) <<* (files.head._1, files.head._2)
  }
}