/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools;

import org.elasticsearch.action.support.nodes.BaseNodeResponse;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class NodeThreadPoolConfigurationResponse extends BaseNodeResponse {
    private String nodeThreadPoolUpdateStatus;

    public NodeThreadPoolConfigurationResponse(StreamInput in) throws IOException {
        super(in);
        nodeThreadPoolUpdateStatus = in.readString();
    }

    public NodeThreadPoolConfigurationResponse(DiscoveryNode node, String msg) {
        super(node);
        this.nodeThreadPoolUpdateStatus = msg;
    }

    public String isNodeThreadPoolUpdatedSuccessfully() {
        return nodeThreadPoolUpdateStatus;
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeString(nodeThreadPoolUpdateStatus);
    }
}
