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
 * A blog's avatar
 *
 * @param url The URL of the avatar image. This is also returned in the Location HTTP header field (An HTTP
 *            Location header field is returned — the response points to the avatar image. That means you can
 *            embed this method in an img tag in HTML.).
 */
class Avatar(val url: String) extends TumblrObject