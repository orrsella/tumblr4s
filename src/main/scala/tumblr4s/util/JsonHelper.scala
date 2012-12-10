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

package tumblr4s.util

import org.json4s._
import org.json4s.Extraction._
import org.json4s.native.JsonMethods._

/**
 * Helper object for formatting and transformint json
 */
object JsonHelper {
  implicit val formats = DefaultFormats
  val OneUnderscorePattern = """(.*)_(.)(.*)""".r
  val TwoUnderscorePattern = """(.*)_(.)(.*)_(.)(.*)""".r

  /**
   * Provide json representation of objects so they can print nicely
   */
  def objectToJsonString(obj: Any): String = {
    pretty(render(decompose(obj)))
  }

  /**
   * Make all fields camelCased, removing all underscores
   */
  def transformToCamelCaseKeys(json: JValue): JValue = {
    json transformField {
      case JField(TwoUnderscorePattern(a, b, c, d, e), value) =>
        JField("%s%s%s%s%s".format(a, b.toUpperCase, c, d.toUpperCase, e), value)
      case JField(OneUnderscorePattern(a, b, c), value) =>
        JField("%s%s%s".format(a, b.toUpperCase, c), value)
    }
  }
}