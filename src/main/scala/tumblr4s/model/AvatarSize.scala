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
 * The size of the avatar in pixels (square, one value for both length and width).
 * Must be one of the values: 16, 24, 30, 40, 48, 64, 96, 128, 512
 */
sealed trait AvatarSize { def size: Int }
case object AvatarSize16  extends AvatarSize { def size = 16}
case object AvatarSize24  extends AvatarSize { def size = 24}
case object AvatarSize30  extends AvatarSize { def size = 30}
case object AvatarSize40  extends AvatarSize { def size = 40}
case object AvatarSize48  extends AvatarSize { def size = 48}
case object AvatarSize64  extends AvatarSize { def size = 64}
case object AvatarSize96  extends AvatarSize { def size = 96}
case object AvatarSize128 extends AvatarSize { def size = 128}
case object AvatarSize512 extends AvatarSize { def size = 512}