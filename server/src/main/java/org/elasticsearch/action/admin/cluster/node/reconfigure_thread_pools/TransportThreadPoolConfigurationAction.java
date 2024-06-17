// TransportMyCustomAction.java
package org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools;

import org.elasticsearch.action.FailedNodeException;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.nodes.BaseNodeRequest;
import org.elasticsearch.action.support.nodes.TransportNodesAction;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.cluster.service.ClusterService;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.node.Node;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

import java.io.IOException;
import java.util.List;

public class TransportThreadPoolConfigurationAction extends TransportNodesAction<
    ThreadPoolConfigurationRequest,
    ThreadPoolConfigurationResponse,
    TransportThreadPoolConfigurationAction.NodeThreadPoolUpdateRequest, NodeThreadPoolConfigurationResponse> {

    private final Node node;

    @Inject
    public TransportThreadPoolConfigurationAction(
        ThreadPool threadPool,
        ClusterService clusterService,
        TransportService transportService,
        Node node,
        ActionFilters actionFilters
    ) {
        super(
            ThreadPoolConfigurationAction.NAME,
            threadPool,
            clusterService,
            transportService,
            actionFilters,
            ThreadPoolConfigurationRequest::new,
            NodeThreadPoolUpdateRequest::new,
            ThreadPool.Names.MANAGEMENT,
            NodeThreadPoolConfigurationResponse.class
        );
        super.executeSynchronously();
        this.node = node;
    }

    @Override
    protected ThreadPoolConfigurationResponse newResponse(ThreadPoolConfigurationRequest request, List<NodeThreadPoolConfigurationResponse> responses, List<FailedNodeException> failures) {
//        List<String> results = responses.stream().map(NodeResponse::getResult).collect(Collectors.toList());
//        return new MyCustomResponse(clusterService.getClusterName(), responses, failures);
        return new ThreadPoolConfigurationResponse(clusterService.getClusterName(), responses, failures);
    }

    @Override
    protected NodeThreadPoolUpdateRequest newNodeRequest(ThreadPoolConfigurationRequest request) {
        return new NodeThreadPoolUpdateRequest(request);
    }

    @Override
    protected NodeThreadPoolConfigurationResponse newNodeResponse(StreamInput in, DiscoveryNode node) throws IOException {
        return new NodeThreadPoolConfigurationResponse(in);
    }

    @Override
    protected NodeThreadPoolConfigurationResponse nodeOperation(NodeThreadPoolUpdateRequest nodeThreadPoolUpdateRequest) {
//        String result = clusterService.localNode().getId(); // Get the local node ID
//        return new MyCustomResponse.NodeResponse(clusterService.localNode(), result);
        return node.SetNewThreadPools();
    }

    public static class NodeThreadPoolUpdateRequest extends BaseNodeRequest {

        ThreadPoolConfigurationRequest request;

        public NodeThreadPoolUpdateRequest(StreamInput in) throws IOException {
            super(in);
            request = new ThreadPoolConfigurationRequest(in);
        }

        public NodeThreadPoolUpdateRequest(ThreadPoolConfigurationRequest request) {
            this.request = request;
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            super.writeTo(out);
            request.writeTo(out);
        }
    }


//    public static class NodeResponse extends BaseNodeResponse {
//        private String result;
//
//        public NodeResponse(StreamInput in) throws IOException {
//            super(in);
//            result = in.readString();
//        }
//
//        public NodeResponse(DiscoveryNode node, String result) {
//            super(node);
//            this.result = result;
//        }
//
//        public String getResult() {
//            return result;
//        }
//
//        @Override
//        public void writeTo(StreamOutput out) throws IOException {
//            super.writeTo(out);
//            out.writeString(result);
//        }
//    }
}
