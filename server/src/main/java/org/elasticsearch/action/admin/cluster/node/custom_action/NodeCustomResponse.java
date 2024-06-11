/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.custom_action;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class NodeCustomResponse extends ActionResponse {
    private String result;

    public NodeCustomResponse(StreamInput in) throws IOException{
        super(in);
    } // Required for serialization

    public NodeCustomResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }


    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeString(result);
    }
}
