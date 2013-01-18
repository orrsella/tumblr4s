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

import org.scalatest.FlatSpec
import com.orrsella.tumblr4s.http._
import com.orrsella.tumblr4s.model._

class TumblrApiExceptionSpec extends FlatSpec {
  val contents =
"""{
   "meta": {
      "status": 401,
      "msg": "Unauthorized"
   },
   "response": { }
}"""

  "TumblrApiException" should "parse valid json correctly" in {
    val e = TumblrApiException.fromJson(401, contents, new Exception())
    assert(e.status === 401)
    assert(e.message === "Unauthorized")
    assert(e.contents === contents)
  }

  it should "parse invalid json correctly" in {
    val e = TumblrApiException.fromJson(402, "", new Exception())
    assert(e.status === 402)
    assert(e.message === "Unknown error")
    assert(e.contents === "")
  }
}