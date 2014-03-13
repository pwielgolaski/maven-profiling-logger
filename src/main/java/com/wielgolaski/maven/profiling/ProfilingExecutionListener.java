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

import org.apache.maven.execution.AbstractExecutionListener;
import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.plugin.MojoExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Profiles the maven execution by timing the different steps.
 */
public class ProfilingExecutionListener extends AbstractExecutionListener {

    private Logger logger = LoggerFactory.getLogger(ProfilingExecutionListener.class);

    private long start;
    private boolean projectStarted = false;

    /**
     * Struct for keeping a group of times
     */
    private class Times implements Comparable<Times> {
        long time;
        Map<String, Long> entries = new LinkedHashMap<String, Long>();

        public int compareTo(Times o) {
            return (int) (this.time - o.time);
        }
    }

    private Map<String, Times> times = new LinkedHashMap<String, Times>();

    // ------------------------------------------< ExecutionListener >--

    @Override
    public void projectDiscoveryStarted(ExecutionEvent event) {
        start = System.currentTimeMillis();
    }

    @Override
    public void sessionStarted(ExecutionEvent event) {
        if (!times.containsKey("scanning for projects")) {
            addTime("scanning for projects", System.currentTimeMillis() - start);
        }

    }

    @Override
    public void projectStarted(ExecutionEvent event) {
        start = System.currentTimeMillis();
        projectStarted = true;
    }

    @Override
    public void mojoStarted(ExecutionEvent event) {
        if (projectStarted) {
            addTime("project setup", System.currentTimeMillis() - start);
            projectStarted = false;
        }
        start = System.currentTimeMillis();
    }

    @Override
    public void mojoFailed(ExecutionEvent event) {
        mojoSucceeded(event);
    }

    @Override
    public void mojoSucceeded(ExecutionEvent event) {
        final long time = System.currentTimeMillis() - start;

        MojoExecution me = event.getMojoExecution();
        addTime(me.getArtifactId(), me.getGoal(), time);
    }

    @Override
    public void sessionEnded(ExecutionEvent event) {
        final long total = System.currentTimeMillis() - event.getSession().getRequest().getStartTime().getTime();

        logger.info("MOJO EXECUTION TIMES");
        logger.info("------------------------------------------------------------------------");

        final long sum = sum(times.values());
        addTime("other", total - sum);

        // sort by time, descending
        List<Map.Entry<String, Times>> entries = sortByValue(times.entrySet());
        Collections.reverse(entries);

        for (Map.Entry<String, Times> e : entries) {
            Times t = e.getValue();
            logger.info(fmtPercentAligned(t.time, total) + " " + e.getKey() + " " + fmtTime(t.time));

            // sort by time, descending
            List<Map.Entry<String, Long>> nested = sortByValue(t.entries.entrySet());
            Collections.reverse(nested);

            for (Map.Entry<String, Long> n : nested) {
                String msg = "     * " + n.getKey();
                // omit repeating data when there is only one entry that is identical to its parent
                if (nested.size() > 1) {
                    msg += " " + fmtPercent(n.getValue(), total) + " " + fmtTime(n.getValue());
                }

                logger.info(msg);
            }
        }
        logger.info("------------------------------------------------------------------------");
    }

    // -----------------------------------------------------< helper >--

    private void addTime(String name, long time) {
        addTime(name, null, time);
    }

    private void addTime(String name, String nestedName, long time) {
        Times t = times.get(name);
        if (t == null) {
            // first occurrence
            t = new Times();
            t.time = time;
            times.put(name, t);
        } else {
            // sum up execution times
            t.time += time;
        }

        if (nestedName != null) {
            if (t.entries.containsKey(nestedName)) {
                // sum up execution times
                t.entries.put(nestedName, t.entries.get(nestedName) + time);
            } else {
                // first occurrence
                t.entries.put(nestedName, time);
            }
        }
    }

    private long sum(Collection<Times> list) {
        long sum = 0;

        for (Times t : list) {
            sum += t.time;
        }

        return sum;
    }

    private String fmtTime(long time) {
        return String.format(Locale.ENGLISH, "[%1$.2fs]", time / 1000.0);
    }

    private String fmtPercent(long time, long total) {
        return String.format(Locale.ENGLISH, "%1$d%%", (int) (100.0 / total * time));
    }

    private String fmtPercentAligned(long time, long total) {
        return String.format(Locale.ENGLISH, "%1$3d%%", (int) (100.0 / total * time));
    }

    /**
     * Sorts a collection of map entries by their value.
     */
    private <K, V extends Comparable<V>> List<Map.Entry<K, V>> sortByValue(Collection<Map.Entry<K, V>> entries) {
        List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(entries);
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        return list;
    }

}
