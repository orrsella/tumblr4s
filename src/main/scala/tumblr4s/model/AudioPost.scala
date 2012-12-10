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
 * An audio post
 *
 * @param blogName           The short name used to uniquely identify a blog
 * @param id                 The post's unique ID
 * @param postUrl            The location of the post
 * @param slug               The short text summary at the end of the post URL
 * @param type               The type of post
 * @param timestamp          The time of the post, in seconds since the epoch
 * @param date               The GMT date and time of the post, as a string
 * @param format             The post format: html or markdown
 * @param reblogKey          The key used to reblog this post
 * @param tags               Tags applied to the post
 * @param featuredInTag      The tags a post has been featured in
 * @param highlighted        Whether a post has been highlighted or not
 * @param noteCount          The number of notes a post has
 * @param bookmarklet        Indicates whether the post was created via the Tumblr bookmarklet
 * @param mobile             Indicates whether the post was created via mobile/email publishing
 * @param sourceUrl          The URL for the source of the content (for quotes, reblogs, etc.)
 * @param sourceTitle        The title of the source site
 * @param liked              Indicates if a user has already liked a post or not
 * @param state              Indicates the current state of the post
 * @param rebloggedFromId    The id of the post being reblogged
 * @param rebloggedFromUrl   The url of the post being reblogged
 * @param rebloggedFromName  The name of the blog who's post is being reblogged
 * @param rebloggedFromTitle The title of the blog who's post is being reblogged
 * @param rebloggedRootUrl   The url of the original post that was reblogged
 * @param rebloggedRootName  The name of the original blog who's post was reblogged
 * @param rebloggedRootTitle The title of the original blog who's post was reblogged
 * @param notes              The notes attached to this blog post
 * @param caption            The user-supplied caption
 * @param player             HTML for embedding the audio player
 * @param plays              Number of times the audio post has been played
 * @param albumArt           Location of the audio file's ID3 album art image
 * @param artist             The audio file's ID3 artist value
 * @param album              The audio file's ID3 album value
 * @param track              The audio file's ID3 track value
 * @param trackName          The audio file's ID3 title value
 * @param year               The audio file's ID3 year value
 * @param audioUrl           Url for playing the audio
 */
class AudioPost(
    val blogName: Option[String],
    val id: Long,
    val postUrl: String,
    val slug: String,
    val `type`: String,
    val timestamp: Int,
    val date: String,
    val format: String,
    val reblogKey: String,
    val tags: Seq[String],
    val featuredInTag: Option[Seq[String]],
    val highlighted: Option[Highlight],
    val noteCount: Option[Int],
    val bookmarklet: Option[Boolean],
    val mobile: Option[Boolean],
    val sourceUrl: Option[String],
    val sourceTitle: Option[String],
    val liked: Option[Boolean],
    val state: String,
    val rebloggedFromId: Option[Int],
    val rebloggedFromUrl: Option[String],
    val rebloggedFromName: Option[String],
    val rebloggedFromTitle: Option[String],
    val rebloggedRootUrl: Option[String],
    val rebloggedRootName: Option[String],
    val rebloggedRootTitle: Option[String],
    val notes: Option[Seq[Note]],
    val caption: Option[String],
    val player: Option[String],
    val plays: Option[Int],
    val albumArt: Option[String],
    val artist: Option[String],
    val album: Option[String],
    val track: Option[Int], // trackNumber
    val trackName: Option[String],
    val year: Option[Int],
    val audioUrl: Option[String])
  extends Post