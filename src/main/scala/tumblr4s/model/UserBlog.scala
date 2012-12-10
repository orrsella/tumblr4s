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
 * A blog a user has permissions to post to
 *
 * @param name      The short blog name
 * @param url       The blog's url
 * @param title     The display title of the blog
 * @param primary   Indicates if this is the user's primary blog
 * @param followers Total count of followers for this blog
 * @param tweet     Indicate if posts are tweeted auto, Y, N
 * @param facebook  Indicate if posts are sent to facebook Y, N
 * @param type      Indicates whether a blog is public or private
 */
class UserBlog(
    val name: String,
    val url: String,
    val title: String,
    val primary: Boolean,
    val followers: Int,
    val tweet: String,
    val facebook: String,
    val `type`: String)
  extends TumblrObject