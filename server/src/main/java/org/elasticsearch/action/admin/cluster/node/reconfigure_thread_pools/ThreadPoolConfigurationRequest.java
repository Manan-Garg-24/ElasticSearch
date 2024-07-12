package org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools;

import org.elasticsearch.action.support.nodes.BaseNodesRequest;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class ThreadPoolConfigurationRequest extends BaseNodesRequest<ThreadPoolConfigurationRequest> {

    public ThreadPoolConfigurationRequest(StreamInput in) throws IOException {
        super(in);
    }

    /**
     * Reconfigure specified nodes
     */
    public ThreadPoolConfigurationRequest(String... nodesIds) {
        super(nodesIds);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
    }
}
