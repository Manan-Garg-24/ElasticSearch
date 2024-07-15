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
import org.elasticsearch.common.Strings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestActions.NodesResponseRestListener;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.elasticsearch.rest.RestRequest.Method.GET;

public class RestThreadPoolConfigurationAction extends BaseRestHandler {
    @Override
    public List<Route> routes() {
        return unmodifiableList(
            asList(new Route(GET, "/_nodes/reconfigure_thread_pools"), new Route(GET, "/_nodes/reconfigure_thread_pools/{nodeId}"))
        );
    }

    @Override
    public String getName() {
        return "reconfigure_thread_pools";
    }

    @Override
    protected RestChannelConsumer prepareRequest(final RestRequest request, final NodeClient client) throws IOException {
        final ThreadPoolConfigurationRequest threadPoolConfigurationRequest = prepareRequest(request);
        return channel -> client.admin()
            .cluster()
            .updateNodesThreadPools(threadPoolConfigurationRequest, new NodesResponseRestListener<>(channel));
    }

    static ThreadPoolConfigurationRequest prepareRequest(final RestRequest request) {
        final String[] nodeIds = Strings.tokenizeToStringArray(request.param("nodeId", "_all"), ",");
        return new ThreadPoolConfigurationRequest(nodeIds);
    }
}
