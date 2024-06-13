/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.threadpool_config;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.TransportAction;
import org.elasticsearch.cli.EnvironmentAwareCommand;
import org.elasticsearch.cli.UserException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.SettingsException;
import org.elasticsearch.node.Node;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.transport.TransportService;

public class TransportConfigureThreadPoolAction extends TransportAction<ConfigureThreadPoolRequest, ConfigureThreadPoolResponse> {
    private final Node node;

    @Inject
    public TransportConfigureThreadPoolAction(TransportService transportService, ActionFilters actionFilters, Node node) {
        super(ConfigureThreadPoolAction.NAME, actionFilters, transportService.getLocalNodeConnection(), transportService.getTaskManager());
        this.node = node;
    }

    @Override
    protected void doExecute(Task task, ConfigureThreadPoolRequest request, ActionListener<ConfigureThreadPoolResponse> listener) {
        try {
            final boolean success = node.SetNewThreadPools(EnvironmentAwareCommand.getElasticsearchConfig());
            listener.onResponse(new ConfigureThreadPoolResponse(success));
        } catch (SettingsException | UserException e) {
            listener.onFailure(e);
        }
    }
}
