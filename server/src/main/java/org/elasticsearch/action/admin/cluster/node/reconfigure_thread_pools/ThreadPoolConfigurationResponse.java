package org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools;

import org.elasticsearch.action.FailedNodeException;
import org.elasticsearch.action.support.nodes.BaseNodesResponse;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.xcontent.ToXContentFragment;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;

import java.io.IOException;
import java.util.List;

public class ThreadPoolConfigurationResponse extends BaseNodesResponse<NodeThreadPoolConfigurationResponse> implements ToXContentFragment {

    public ThreadPoolConfigurationResponse(StreamInput in) throws IOException {
        super(in);
    }

    public ThreadPoolConfigurationResponse(
        ClusterName clusterName,
        List<NodeThreadPoolConfigurationResponse> nodes,
        List<FailedNodeException> failures
    ) {
        super(clusterName, nodes, failures);
    }

    @Override
    protected List<NodeThreadPoolConfigurationResponse> readNodesFrom(StreamInput in) throws IOException {
        return in.readList(NodeThreadPoolConfigurationResponse::new);
    }

    @Override
    protected void writeNodesTo(StreamOutput out, List<NodeThreadPoolConfigurationResponse> nodes) throws IOException {
        out.writeList(nodes);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject("nodes");
        for (NodeThreadPoolConfigurationResponse nodeThreadPoolConfigurationResponse : getNodes()) {
            builder.startObject(nodeThreadPoolConfigurationResponse.getNode().getId());
            builder.field("status", nodeThreadPoolConfigurationResponse.isNodeThreadPoolUpdatedSuccessfully());
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }

    @Override
    public String toString() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().prettyPrint();
            builder.startObject();
            toXContent(builder, EMPTY_PARAMS);
            builder.endObject();
            return Strings.toString(builder);
        } catch (IOException e) {
            return "{ \"error\" : \"" + e.getMessage() + "\"}";
        }
    }
}
