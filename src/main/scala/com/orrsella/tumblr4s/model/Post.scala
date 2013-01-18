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
 * A generic post
 */
abstract class Post extends TumblrObject {
  def blogName: Option[String]
  def id: Long
  def postUrl: String
  def slug: String
  def `type`: String
  def timestamp: Int
  def date: String
  def format: String
  def reblogKey: String
  def tags: Seq[String]
  def featuredInTag: Option[Seq[String]]
  def highlighted: Option[Highlight]
  def noteCount: Option[Int]
  def bookmarklet: Option[Boolean]
  def mobile: Option[Boolean]
  def sourceUrl: Option[String]
  def sourceTitle: Option[String]
  def liked: Option[Boolean]
  def state: String
  def rebloggedFromId: Option[Int]
  def rebloggedFromUrl: Option[String]
  def rebloggedFromName: Option[String]
  def rebloggedFromTitle: Option[String]
  def rebloggedRootUrl: Option[String]
  def rebloggedRootName: Option[String]
  def rebloggedRootTitle: Option[String]
  def notes: Option[Seq[Note]]
}