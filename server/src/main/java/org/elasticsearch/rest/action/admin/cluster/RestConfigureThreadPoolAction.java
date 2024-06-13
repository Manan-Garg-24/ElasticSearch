/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.rest.action.admin.cluster;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.action.admin.cluster.node.threadpool_config.ConfigureThreadPoolAction;
import org.elasticsearch.action.admin.cluster.node.threadpool_config.ConfigureThreadPoolRequest;
import org.elasticsearch.action.admin.cluster.node.threadpool_config.ConfigureThreadPoolResponse;
import org.elasticsearch.cli.UserException;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.rest.BytesRestResponse;

import static org.elasticsearch.rest.RestRequest.Method.GET;

public class RestConfigureThreadPoolAction extends BaseRestHandler {
    @Override
    public List<Route> routes() {
        return Collections.singletonList(new Route(GET, "/_nodes/reset_threadpools"));
    }

    @Override
    public String getName() {
        return "custom_action";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {

        ConfigureThreadPoolRequest configureThreadPoolRequest = new ConfigureThreadPoolRequest();
        return channel -> client.execute(
            ConfigureThreadPoolAction.INSTANCE,
            configureThreadPoolRequest,
            new ActionListener<ConfigureThreadPoolResponse>() {
                @Override
                public void onResponse(ConfigureThreadPoolResponse myCustomResponse) {
                    try (XContentBuilder builder = channel.newBuilder()) {
                        builder.startObject();
                        builder.field("success: ", myCustomResponse.getResult());
                        builder.endObject();
                        channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                    } catch (IOException e) {
                        channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    /** User Exception thrown by {@link EnvironmentAwareCommand#getElasticsearchConfig()}*/
                    if (e instanceof UserException) {
                        channel.sendResponse(new BytesRestResponse(RestStatus.NO_CONTENT, e.getMessage()));
                    }
                    channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
                }
            }
        );
    }
}
