/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.custom_action;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.TransportAction;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.node.Node;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.transport.TransportService;

public class TransportNodeCustomAction extends TransportAction<NodeCustomRequest, NodeCustomResponse> {
    private final Node node;

    @Inject
    public TransportNodeCustomAction(TransportService transportService, ActionFilters actionFilters, Node node) {
        super(NodeCustomAction.NAME, actionFilters, transportService.getLocalNodeConnection(), transportService.getTaskManager());
        this.node = node;
    }

    @Override
    protected void doExecute(Task task, NodeCustomRequest request, ActionListener<NodeCustomResponse> listener) {
        String result = node.customAPIEndointResponseString();
        listener.onResponse(new NodeCustomResponse(result));
    }
}
