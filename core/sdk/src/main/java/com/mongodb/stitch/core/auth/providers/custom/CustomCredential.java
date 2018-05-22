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

package com.mongodb.stitch.core.auth.providers.custom;

import static com.mongodb.stitch.core.auth.providers.custom.CustomAuthProvider.DEFAULT_NAME;
import static com.mongodb.stitch.core.auth.providers.custom.CustomAuthProvider.TYPE;

import com.mongodb.stitch.core.auth.ProviderCapabilities;
import com.mongodb.stitch.core.auth.StitchCredential;
import org.bson.Document;

/**
 * The credential used for custom auth log ins.
 */
public final class CustomCredential implements StitchCredential {

  private final String providerName;
  private final String token;

  /**
   * Constructs a custom auth credential for a user.
   *
   * @param token the signed custom auth token.
   * @see <a href="https://docs.mongodb.com/stitch/auth/custom-auth/">Custom Authentication</a>
   */
  public CustomCredential(final String token) {
    this(DEFAULT_NAME, token);
  }

  private CustomCredential(final String providerName, final String token) {
    this.providerName = providerName;
    this.token = token;
  }

  @Override
  public String getProviderName() {
    return providerName;
  }

  @Override
  public String getProviderType() {
    return TYPE;
  }

  @Override
  public Document getMaterial() {
    return new Document(Fields.TOKEN, token);
  }

  @Override
  public ProviderCapabilities getProviderCapabilities() {
    return new ProviderCapabilities(false);
  }

  private static class Fields {
    static final String TOKEN = "token";
  }
}
