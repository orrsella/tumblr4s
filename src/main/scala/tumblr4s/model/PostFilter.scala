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

package tumblr4s.model

/**
 * Specifies the post format to return when querying for blog posts, other than HTML
 */
sealed trait PostFilter
case object HtmlFilter extends PostFilter { override def toString: String = ""}
case object TextFilter extends PostFilter { override def toString: String = "text"}
case object RawFilter  extends PostFilter { override def toString: String = "raw"}