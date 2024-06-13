/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.threadpool_config;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class ConfigureThreadPoolRequest extends ActionRequest {
    public ConfigureThreadPoolRequest() throws IOException{
        super();
        System.out.println("New request created");
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
    }

    public ActionRequestValidationException validate(){
        return null;
    }

}
