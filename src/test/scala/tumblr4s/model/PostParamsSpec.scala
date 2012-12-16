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

import org.scalatest.FlatSpec
import tumblr4s.model._

abstract class PostParamsSpec[T <: PostParams]() extends FlatSpec {
  def name: String
  def postType: PostType
  def validParams: Seq[T]
  def invalidParams: Seq[T]
  def mapKeys: Seq[String] = List("type", "state", "tags", "tweet", "date", "format", "slug")

  val baseHostname = "staff.tumblr.com"

  (name + "PostParams") should "have the correct type" in {
    assert(validParams.forall(_.`type` == postType))
  }

  it should "validate correctly" in {
    assert(validParams.forall(_.isValid))
  }

  it should "invalidate correctly" in {
    assert(invalidParams.forall(!_.isValid))
  }

  it should "contain all required parameters in toMap" in {
    val map = validParams.head.toMap
    mapKeys.foreach(k => assert(map.contains(k)))
  }
}

class TextPostParamsSpec extends PostParamsSpec[TextPostParams]() {
  val title = "title"
  val body = "body"

  def name = "Text"
  def postType = TextPostType
  def validParams = List(new TextPostParams(baseHostname, title, body))
  def invalidParams =
    List(
      new TextPostParams("", title, body),
      new TextPostParams(baseHostname, title, ""))

  override def mapKeys = super.mapKeys ++ List("title", "body")
}

class PhotoPostParamsSpec extends PostParamsSpec[PhotoPostParams]() {
  val caption = "caption"
  val link = "link"
  val source = "source"
  val data = List("")

  def name = "Photo"
  def postType = PhotoPostType
  def validParams =
    List(
      new PhotoPostParams(baseHostname, caption, link, source, Nil),
      new PhotoPostParams(baseHostname, caption, link, "", data))

  def invalidParams =
    List(
      new PhotoPostParams("", caption, link, source, data),
      new PhotoPostParams(baseHostname, caption, link, "", Nil))

  override def mapKeys = super.mapKeys ++ List("caption", "link", "source")
}

class QuotePostParamsSpec extends PostParamsSpec[QuotePostParams]() {
  val quote = "quote"
  val source = "source"

  def name = "Quote"
  def postType = QuotePostType
  def validParams =
    List(
      new QuotePostParams(baseHostname, quote, source),
      new QuotePostParams(baseHostname, quote, ""))

  def invalidParams =
    List(
      new QuotePostParams("", quote, source),
      new QuotePostParams(baseHostname, "", source))

  override def mapKeys = super.mapKeys ++ List("quote", "source")
}

class LinkPostParamsSpec extends PostParamsSpec[LinkPostParams]() {
  val title = "title"
  val url = "url"
  val description = "description"

  def name = "Link"
  def postType = LinkPostType
  def validParams =
    List(
      new LinkPostParams(baseHostname, title, url, description),
      new LinkPostParams(baseHostname, "", url, ""))

  def invalidParams =
    List(
      new LinkPostParams("", title, url, description),
      new LinkPostParams(baseHostname, title, "", description))

  override def mapKeys = super.mapKeys ++ List("title", "url", "description")
}

class ChatPostParamsSpec extends PostParamsSpec[ChatPostParams]() {
  val title = "title"
  val conversation = "conversation"

  def name = "Chat"
  def postType = ChatPostType
  def validParams =
    List(
      new ChatPostParams(baseHostname, title, conversation),
      new ChatPostParams(baseHostname, "", conversation))

  def invalidParams =
    List(
      new ChatPostParams("", title, conversation),
      new ChatPostParams(baseHostname, title, ""))

  override def mapKeys = super.mapKeys ++ List("title", "conversation")
}

class AudioPostParamsSpec extends PostParamsSpec[AudioPostParams]() {
  val caption = "caption"
  val externalUrl = "externalUrl"
  val data = Some("")

  def name = "Audio"
  def postType = AudioPostType
  def validParams =
    List(
      new AudioPostParams(baseHostname, caption, externalUrl, None),
      new AudioPostParams(baseHostname, caption, "", data))

  def invalidParams =
    List(
      new AudioPostParams("", caption, externalUrl, data),
      new AudioPostParams(baseHostname, caption, "", None))

  override def mapKeys = super.mapKeys ++ List("caption", "external_url")
}

class VideoPostParamsSpec extends PostParamsSpec[VideoPostParams]() {
  val caption = "caption"
  val embed = "embed"
  val data = Some("")

  def name = "Audio"
  def postType = VideoPostType
  def validParams =
    List(
      new VideoPostParams(baseHostname, caption, embed, None),
      new VideoPostParams(baseHostname, caption, "", data))

  def invalidParams =
    List(
      new VideoPostParams("", caption, embed, data),
      new VideoPostParams(baseHostname, caption, "", None))

  override def mapKeys = super.mapKeys ++ List("caption", "embed")
}