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
 * Post state
 */
sealed trait PostState
case object PublishPostState   extends PostState { override def toString: String = "published"}
case object DraftPostState     extends PostState { override def toString: String = "draft"}
case object QueuePostState     extends PostState { override def toString: String = "queue"}
case object PrivatePostState   extends PostState { override def toString: String = "private"}