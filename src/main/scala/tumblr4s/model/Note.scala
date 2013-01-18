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
 * A note object on a specific blog post
 *
 * @param timestamp When the note was created
 * @param blogName  The name of the blog creating the note
 * @param blogUrl   The url of the blog creating the note
 * @param type      The type of the note (like, reblog, posted, etc.)
 */
class Note(val timestamp: Int, val blogName: String, val blogUrl: String, val `type`: String) extends TumblrObject