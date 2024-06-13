/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */
package org.elasticsearch.xpack.sql.plugin;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestStatusToXContentListener;
import org.elasticsearch.xpack.core.async.GetAsyncStatusRequest;

import java.util.Collections;
import java.util.List;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.xpack.sql.proto.Protocol.ID_NAME;
import static org.elasticsearch.xpack.sql.proto.Protocol.SQL_ASYNC_STATUS_REST_ENDPOINT;

public class RestSqlAsyncGetStatusAction extends BaseRestHandler {
    @Override
    public List<Route> routes() {
        return Collections.singletonList(new Route(GET, SQL_ASYNC_STATUS_REST_ENDPOINT + "{" + ID_NAME + "}"));
    }

    @Override
    public String getName() {
        return "sql_get_async_status";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        GetAsyncStatusRequest statusRequest = new GetAsyncStatusRequest(request.param(ID_NAME));
        return channel -> client.execute(SqlAsyncGetStatusAction.INSTANCE, statusRequest, new RestStatusToXContentListener<>(channel));
    }
}
