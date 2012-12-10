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

import java.io.File

/**
 * A generic http client
 */
trait HttpClient {

  /**
   * Make a request
   *
   * @param method     The http method to use
   * @param requestUrl The url to use
   * @param params     A map of string name-value parameters for the request
   * @param files      A map of files to be sent as multipart post
   */
  def makeRequest(method: HttpMethod, requestUrl: String, params: Map[String, String], files: Map[String, File] = Map()): String

  /**
   * Make an OAuth request
   *
   * @param method         The http method to use
   * @param requestUrl     The url to use
   * @param params         A map of string name-value parameters for the request
   * @param files          A map of files to be sent as multipart post
   * @param consumerKey    OAuth consumer key
   * @param consumerSecret OAuth consumer secret key
   * @param accessKey      OAuth access token key
   * @param accessSecret   OAuth access token secret
   */
  def makeOAuthRequest(method: HttpMethod, requestUrl: String, params: Map[String, String], files: Map[String, File], consumerKey: String, consumerSecret: String, accessKey: String, accessSecret: String): String
}