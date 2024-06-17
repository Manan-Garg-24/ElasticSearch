package org.elasticsearch.action.admin.cluster.node.reconfigure_thread_pools;
import org.elasticsearch.action.ActionType;

public class ThreadPoolConfigurationAction extends ActionType<ThreadPoolConfigurationResponse> {

    public static final ThreadPoolConfigurationAction INSTANCE = new ThreadPoolConfigurationAction();
    public static final String NAME = "cluster:admin/nodes/custominfo";

    private ThreadPoolConfigurationAction() {
        super(NAME, ThreadPoolConfigurationResponse::new);
    }
}
