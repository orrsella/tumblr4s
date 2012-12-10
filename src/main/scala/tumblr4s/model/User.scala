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
 * A tumblr user
 *
 * @param following         The number of blogs the user is following
 * @param defaultPostFormat The default posting format - html, markdown or raw
 * @param name              The user's tumblr short name
 * @param likes             The total count of the user's likes
 * @param blogs             The blogs the user has permissions to post to
 */
class User(
    val following: Int,
    val defaultPostFormat: String,
    val name: String,
    val likes: Int,
    val blogs: Seq[UserBlog])
  extends TumblrObject