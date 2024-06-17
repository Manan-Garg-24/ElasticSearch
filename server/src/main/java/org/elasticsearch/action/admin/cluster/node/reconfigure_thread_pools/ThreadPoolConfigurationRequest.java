package org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools;

import org.elasticsearch.action.support.nodes.BaseNodesRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class ThreadPoolConfigurationRequest extends BaseNodesRequest<ThreadPoolConfigurationRequest> {

    public ThreadPoolConfigurationRequest(StreamInput in) throws IOException {
        super(in);
    }

    /**
     * Default constructor, to signal reconfiguration of thread pools of all nodes
     */
    public ThreadPoolConfigurationRequest() {
        super(Strings.tokenizeToStringArray("_all", ","));
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
    }
}
