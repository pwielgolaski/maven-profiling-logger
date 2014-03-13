package com.wielgolaski.maven.profiling;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.execution.ExecutionListener;

/**
 * ExecutionListener that passes the events on to multiple listeners.
 */
public class ExecutionListenerChain implements ExecutionListener {

    private List<ExecutionListener> listeners = new ArrayList<ExecutionListener>();

    public ExecutionListenerChain addListener(ExecutionListener listener) {
        listeners.add(listener);
        return this;
    }

    public ExecutionListenerChain removeListener(ExecutionListener listener) {
        listeners.add(listener);
        return this;
    }

    public void projectDiscoveryStarted(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.projectDiscoveryStarted(event);
        }
    }

    public void sessionStarted(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.sessionStarted(event);
        }
    }

    public void sessionEnded(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.sessionEnded(event);
        }
    }

    public void projectSkipped(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.projectSkipped(event);
        }
    }

    public void projectStarted(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.projectStarted(event);
        }
    }

    public void projectSucceeded(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.projectSucceeded(event);
        }
    }

    public void projectFailed(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.projectFailed(event);
        }
    }

    public void mojoSkipped(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.mojoSkipped(event);
        }
    }

    public void mojoStarted(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.mojoStarted(event);
        }
    }

    public void mojoSucceeded(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.mojoSucceeded(event);
        }
    }

    public void mojoFailed(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.mojoFailed(event);
        }
    }

    public void forkStarted(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.forkStarted(event);
        }
    }

    public void forkSucceeded(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.forkSucceeded(event);
        }
    }

    public void forkFailed(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.forkFailed(event);
        }
    }

    public void forkedProjectStarted(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.forkedProjectStarted(event);
        }
    }

    public void forkedProjectSucceeded(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.forkedProjectSucceeded(event);
        }
    }

    public void forkedProjectFailed(ExecutionEvent event) {
        for (ExecutionListener listener : listeners) {
            listener.forkedProjectFailed(event);
        }
    }
}
