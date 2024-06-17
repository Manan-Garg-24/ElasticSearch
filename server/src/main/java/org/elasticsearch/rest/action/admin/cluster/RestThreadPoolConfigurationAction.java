/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.rest.action.admin.cluster;

import org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools.ThreadPoolConfigurationRequest;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestActions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RestThreadPoolConfigurationAction extends BaseRestHandler {
    @Override
    public List<Route> routes() {
        return Collections.singletonList(new Route(RestRequest.Method.GET, "/_nodes/reconfigure_thread_pools"));
    }

    @Override
    public String getName() {
        return "reconfigure_thread_pools";
    }

    @Override
    protected RestChannelConsumer prepareRequest(final RestRequest request, final NodeClient client) throws IOException {
        final ThreadPoolConfigurationRequest threadPoolConfigurationRequest = new ThreadPoolConfigurationRequest();
        return channel -> client.admin()
            .cluster()
            .updateNodesThreadPools(threadPoolConfigurationRequest, new RestActions.NodesResponseRestListener<>(channel));
    }
}
