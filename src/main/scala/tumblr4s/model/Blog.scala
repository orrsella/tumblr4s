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
 * A tumblr blog
 *
 * @param title       The display title of the blog
 * @param posts       The total number of posts to this blog
 * @param name        The short blog name that appears before tumblr.com in a standard blog hostname (and before the domain in a custom blog hostname)
 * @param url         The blog's url
 * @param updated     The time of the most recent post, in seconds since the epoch
 * @param description You guessed it! The blog's description
 * @param ask         Indicates whether the blog allows questions
 * @param askAnon     Indicates whether the blog allows anonymous questions
 * @param likes       Number of likes for this user
 */
class Blog(
    val title: String,
    val posts: Int,
    val name: String,
    val url: String,
    val updated: Int,
    val description: String,
    val ask: Boolean,
    val askAnon: Option[Boolean],
    val likes: Option[Int]) extends TumblrObject