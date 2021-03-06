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

package com.mongodb.stitch.core.internal.net;

import static com.mongodb.stitch.core.internal.net.StitchAppAuthRoutes.RouteParts.AUTH_PROVIDER_LINK_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppAuthRoutes.RouteParts.AUTH_PROVIDER_LOGIN_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppAuthRoutes.RouteParts.AUTH_PROVIDER_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppAuthRoutes.RouteParts.BASE_AUTH_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppAuthRoutes.RouteParts.PROFILE_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppAuthRoutes.RouteParts.SESSION_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppRoutes.RouteParts.APP_ROUTE;
import static com.mongodb.stitch.core.internal.net.StitchAppRoutes.RouteParts.BASE_ROUTE;

import com.mongodb.stitch.core.auth.internal.StitchAuthRoutes;

public final class StitchAppAuthRoutes implements StitchAuthRoutes {
  private final String clientAppId;

  StitchAppAuthRoutes(final String clientAppId) {
    this.clientAppId = clientAppId;
  }

  @Override
  public String getBaseAuthRoute() {
    return BASE_AUTH_ROUTE;
  }

  @Override
  public String getSessionRoute() {
    return SESSION_ROUTE;
  }

  @Override
  public String getProfileRoute() {
    return PROFILE_ROUTE;
  }

  @Override
  public String getAuthProviderRoute(final String providerName) {
    return String.format(AUTH_PROVIDER_ROUTE, clientAppId, providerName);
  }

  @Override
  public String getAuthProviderLoginRoute(final String providerName) {
    return String.format(AUTH_PROVIDER_LOGIN_ROUTE, clientAppId, providerName);
  }

  @Override
  public String getAuthProviderLinkRoute(final String providerName) {
    return String.format(AUTH_PROVIDER_LINK_ROUTE, clientAppId, providerName);
  }

  @Override
  public String getAuthProviderExtensionRoute(final String providerName, final String path) {
    return String.format("%s/%s", getAuthProviderRoute(providerName), path);
  }

  public static class RouteParts {
    static final String BASE_AUTH_ROUTE = BASE_ROUTE + "/auth";
    static final String SESSION_ROUTE = BASE_AUTH_ROUTE + "/session";
    static final String PROFILE_ROUTE = BASE_AUTH_ROUTE + "/profile";
    static final String BASE_APP_AUTH_ROUTE = APP_ROUTE + "/auth";
    static final String AUTH_PROVIDER_ROUTE = BASE_APP_AUTH_ROUTE + "/providers/%s";
    static final String AUTH_PROVIDER_LOGIN_ROUTE = AUTH_PROVIDER_ROUTE + "/login";
    static final String AUTH_PROVIDER_LINK_ROUTE = AUTH_PROVIDER_LOGIN_ROUTE + "?link=true";
  }
}
