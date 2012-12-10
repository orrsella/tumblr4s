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

import org.json4s._
import org.json4s.native.JsonMethods._

/**
 * A tumblr API exception
 *
 * @param status   The HTTP/1.1 status code
 * @param message  The HTTP/1.1 status message, is available
 * @param contents The contents of the errornous response
 */
case class TumblrApiException(status: Int, message: String, contents: String, cause: Exception)
  extends Exception(
    "Tumblr API Exception: status='" + status +
    "', message='" + message +
    "', contents='" + contents +
    "', cause='" + cause.getClass + "'")

/**
 * Companion object for constructing a TumblrApiException from json response
 */
object TumblrApiException {
  private implicit val formats = DefaultFormats

  def fromJson(status: Int, contents: String, cause: Exception) = {
    try {
      val json = parse(contents)
      val status = (json \ "meta" \ "status").extract[Int]
      val message = (json \ "meta" \ "msg").extract[String]
      new TumblrApiException(status, message, contents, cause)
    } catch {
      case e => println(e); new TumblrApiException(status, "Unknown error", contents, cause)
    }
  }
}