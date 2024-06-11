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

import org.elasticsearch.action.admin.cluster.node.custom_action.NodeCustomAction;
import org.elasticsearch.action.admin.cluster.node.custom_action.NodeCustomRequest;
import org.elasticsearch.action.admin.cluster.node.custom_action.NodeCustomResponse;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.rest.BytesRestResponse;

import static org.elasticsearch.rest.RestRequest.Method.GET;

public class RestNodeCustomAction extends BaseRestHandler {
    @Override
    public List<Route> routes() {
        return Collections.singletonList(new Route(GET, "/_nodes/custom_endpoint"));
    }

    @Override
    public String getName() {
        return "custom_action";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        NodeCustomRequest nodeCustomRequest = new NodeCustomRequest();

        return channel -> client.execute(NodeCustomAction.INSTANCE, nodeCustomRequest, new ActionListener<NodeCustomResponse>() {
            @Override
            public void onResponse(NodeCustomResponse myCustomResponse) {
                try (XContentBuilder builder = channel.newBuilder()) {
                    builder.startObject();
                    builder.field("result", myCustomResponse.getResult());
                    builder.endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                } catch (IOException e) {
                    channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
                }
            }

            @Override
            public void onFailure(Exception e) {
                channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            }
        });
    }
}
