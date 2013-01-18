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

package com.orrsella.tumblr4s.model

/**
 * A blog's posts
 *
 * @param blog       General information about the blog
 * @param posts      The posts
 * @param totalPosts The total number of post available for this request, useful for paginating through results
 */
class BlogPosts(val blog: Blog, val posts: Seq[Post], val totalPosts: Int) extends TumblrObject