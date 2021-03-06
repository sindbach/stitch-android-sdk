/*
 * Copyright 2018-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.stitch.core.internal.net;

import com.mongodb.stitch.core.StitchRequestErrorCode;
import com.mongodb.stitch.core.StitchRequestException;
import com.mongodb.stitch.core.StitchServiceException;
import com.mongodb.stitch.core.internal.common.StitchError;

public abstract class BaseStitchRequestClient implements StitchRequestClient {
  final String baseUrl;
  final Transport transport;

  private final Long defaultRequestTimeout;

  /**
   * Constructs a BaseStitchRequestClient with the provided parameters.
   * @param baseUrl the base URL of the Stitch server to which this client will make requests.
   * @param transport the underlying {@link Transport} that this client will use to make requests.
   * @param defaultRequestTimeout the number of milliseconds the client should wait for a response
   *                              by default from the server before failing with an error.
   */
  BaseStitchRequestClient(final String baseUrl,
                          final Transport transport,
                          final Long defaultRequestTimeout) {
    this.baseUrl = baseUrl;
    this.transport = transport;
    this.defaultRequestTimeout = defaultRequestTimeout;
  }

  public void close() {
    transport.close();
  }

  private static void inspectResponse(final Response response) {
    if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
      return;
    }

    StitchError.handleRequestError(response);
  }

  Response doRequestUrl(final StitchRequest stitchReq, final String url) {
    final Response response;
    try {
      response = transport.roundTrip(buildRequest(stitchReq, url));
    } catch (final Exception e) {
      throw new StitchRequestException(e, StitchRequestErrorCode.TRANSPORT_ERROR);
    }

    inspectResponse(response);

    return response;
  }

  EventStream doStreamRequestUrl(final StitchRequest stitchReq, final String url) {
    try {
      return transport.stream(buildRequest(stitchReq, url));
    } catch (final StitchServiceException e) {
      throw e;
    } catch (final Exception e) {
      throw new StitchRequestException(e, StitchRequestErrorCode.TRANSPORT_ERROR);
    }
  }

  private Request buildRequest(final StitchRequest stitchReq, final String url) {
    return new Request.Builder()
        .withMethod(stitchReq.getMethod())
        .withUrl(String.format("%s%s", url, stitchReq.getPath()))
        .withTimeout(
            stitchReq.getTimeout() == null ? defaultRequestTimeout : stitchReq.getTimeout())
        .withHeaders(stitchReq.getHeaders())
        .withBody(stitchReq.getBody())
        .build();
  }
}
