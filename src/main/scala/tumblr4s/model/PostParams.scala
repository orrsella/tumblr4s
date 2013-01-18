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
 * Parameters for creating/editing a new generic post
 */
sealed abstract class PostParams extends TumblrObject {
  def baseHostname: String
  def `type`: PostType
  def state: PostState
  def tags: String
  def tweet: String
  def date: String
  def format: PostFormat
  def slug: String
  def isValid: Boolean = !baseHostname.isEmpty
  def toMap: Map[String, String] =
    Map(
      "type" -> `type`.toString,
      "state" -> state.toString,
      "tags" -> tags,
      "tweet" -> tweet,
      "date" -> date,
      "format" -> format.toString,
      "slug" -> slug)
  def toFilesMap: Map[String, String] = Map()
}

/**
 * Parameters for a text post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param title        The optional title of the post, HTML entities must be escaped
 * @param body         The full post body, HTML allowed
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class TextPostParams(
    val baseHostname: String,
    val title: String = "",
    val body: String,
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = TextPostType
  override def isValid = super.isValid && !body.isEmpty
  override def toMap = super.toMap ++ Map("title" -> title, "body" -> body)
}

/**
 * Parameters for a photo post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param caption      The user-supplied caption, HTML allowed
 * @param link         The "click-through URL" for the photo
 * @param source       The photo source URL, either source or data required
 * @param data         Sequence of paths to image files, each limited to 10MB (add more than one images to create a slide
 *                     show), either source or data required
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class PhotoPostParams(
    val baseHostname: String,
    val caption: String = "",
    val link: String = "",
    val source: String = "",
    val data: Seq[String] = Nil,
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = PhotoPostType
  override def isValid = super.isValid && (!source.isEmpty || !data.isEmpty)
  override def toMap = super.toMap ++ Map("caption" -> caption, "link" -> link, "source" -> source)
  override def toFilesMap = data.zipWithIndex.map(t => ("data[" + t._2 + "]", t._1)).toMap
}

/**
 * Parameters for a quote post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param quote        The full text of the quote
 * @param source       Cited source, HTML allowed
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
  *                    the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class QuotePostParams(
    val baseHostname: String,
    val quote: String,
    val source: String = "",
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = QuotePostType
  override def isValid = super.isValid && !quote.isEmpty
  override def toMap = super.toMap ++ Map("quote" -> quote, "source" -> source)
}

/**
 * Parameters for a link post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param title        The title of the page the link points to
 * @param url          The link
 * @param description  A user-supplied description, HTML allowed
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class LinkPostParams(
    val baseHostname: String,
    val title: String = "",
    val url: String,
    val description: String = "",
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = LinkPostType
  override def isValid = super.isValid && !url.isEmpty
  override def toMap = super.toMap ++ Map("title" -> title, "url" -> url, "description" -> description)
}

/**
 * Parameters for a chat post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param title        The title of the chat
 * @param conversation The text of the conversation/chat, with dialogue labels (no HTML), parts separated with '\r\n'
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class ChatPostParams(
    val baseHostname: String,
    val title: String = "",
    val conversation: String,
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = ChatPostType
  override def isValid = super.isValid && !conversation.isEmpty
  override def toMap = super.toMap ++ Map("title" -> title, "conversation" -> conversation)
}

/**
 * Parameters for an audio post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param caption      The user-supplied caption
 * @param externalUrl  The URL of the site that hosts the audio file (not tumblr), either externalUrl or data required
 * @param data         Path to MP3 audio file limited to 10MB, either externalUrl or data required
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class AudioPostParams(
    val baseHostname: String,
    val caption: String = "",
    val externalUrl: String = "",
    val data: Option[String] = None,
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = AudioPostType
  override def isValid = super.isValid && (!externalUrl.isEmpty || data.isDefined)
  override def toMap = super.toMap ++ Map("caption" -> caption, "external_url" -> externalUrl)
  override def toFilesMap = data match {
    case Some(file) => Map("data" -> file)
    case _ => Map()
  }
}

/**
 * Parameters for a video post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param caption      The user-supplied caption
 * @param embed        HTML embed code for the video, either embed or data required
 * @param data         Path to video file limited to 100MB, either embed or data required
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class VideoPostParams(
    val baseHostname: String,
    val caption: String = "",
    val embed: String = "",
    val data: Option[String] = None,
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = VideoPostType
  override def isValid = super.isValid && (!embed.isEmpty || data.isDefined)
  override def toMap = super.toMap ++ Map("caption" -> caption, "embed" -> embed)
  override def toFilesMap = data match {
    case Some(file) => Map("data" -> file)
    case _ => Map()
  }
}

/**
 * Parameters for reblogging a post
 *
 * @param baseHostname The standard or custom blog hostname
 * @param state        The state of the post. Specify one of the following:  published, draft, queue, private
 * @param tags         Comma-separated tags for this post
 * @param tweet        Manages the autotweet (if enabled) for this post: set to off for no tweet, or enter text to override
 *                     the default tweet
 * @param date         The GMT date and time of the post, as a string
 * @param format       Sets the format type of post. Supported formats are: html & markdown
 * @param slug         Add a short text summary to the end of the post URL
 */
class ReblogPostParams(
    val baseHostname: String,
    val state: PostState = PublishPostState,
    val tags: String = "",
    val tweet: String = "off",
    val date: String = "",
    val format: PostFormat = HtmlPostFormat,
    val slug: String = "")
  extends PostParams {
  def `type`: PostType = AnyPostType
  override def isValid = super.isValid
}