/*
 * Copyright 2018-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.stitch.core.services.mongodb.remote.sync;

import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.BsonValue;

/**
 * The result of an update operation.
 */
public class SyncUpdateResult extends RemoteUpdateResult {
  /**
   * Constructs a result.
   *
   * @param matchedCount the number of documents matched by the query.
   * @param modifiedCount the number of documents modified.
   * @param upsertedId the _id of the inserted document if the replace resulted in an inserted
   *                   document, otherwise null.
   */
  public SyncUpdateResult(
      final long matchedCount,
      final long modifiedCount,
      final BsonValue upsertedId
  ) {
    super(matchedCount, modifiedCount, upsertedId);
  }
}
