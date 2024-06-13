/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.threadpool_config;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class ConfigureThreadPoolResponse extends ActionResponse {
    private boolean result;

    public ConfigureThreadPoolResponse(StreamInput in) throws IOException{
        super(in);
    } // Required for serialization

    public ConfigureThreadPoolResponse(boolean result) {
        System.out.println("The response is : " + result);
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }


    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeBoolean(result);
    }
}
