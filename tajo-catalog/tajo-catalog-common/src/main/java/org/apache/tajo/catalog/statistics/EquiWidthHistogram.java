/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tajo.catalog.statistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.tajo.catalog.proto.CatalogProtos.HistogramProto;
import org.apache.tajo.util.TUtil;

/**
 * A custom and improved version of the famous EquiWidth histogram
 */
public class EquiWidthHistogram extends Histogram {

  public EquiWidthHistogram() {
    super();
  }

  public EquiWidthHistogram(HistogramProto proto) {
    super(proto);
  }

  /**
   * Originally, an EquiWidth histogram is constructed by dividing the entire value range of the data points into
   * buckets of equal sizes. Here, we construct it similarly, then improve it by removing empty buckets, to save disk
   * space and processing time, and by compacting the buckets' boundaries, to increase selectivity estimation accuracy.
   */
  @Override
  public boolean construct(List<Double> samples) {
    isReady = false;
    buckets = TUtil.newList();
    Double globalMin = Double.MAX_VALUE;
    Double globalMax = Double.MIN_VALUE;
    int numBuckets = samples.size() > DEFAULT_MAX_BUCKETS ? DEFAULT_MAX_BUCKETS : samples.size();
    List<Double> bMinValues = new ArrayList<Double>(numBuckets);
    List<Double> bMaxValues = new ArrayList<Double>(numBuckets);
    List<Long> bFrequencies = new ArrayList<Long>(numBuckets);

    for (Double p : samples) {
      if (p < globalMin) globalMin = p;
      if (p > globalMax) globalMax = p;
    }
    double bWidth = (globalMax - globalMin) / numBuckets;

    for (int i = 0; i < numBuckets; i++) {
      bMinValues.add(Double.MAX_VALUE);
      bMaxValues.add(Double.MIN_VALUE);
      bFrequencies.add(0l);
    }

    for (Double p : samples) {
      int bIndex = (int) Math.round(Math.floor((p - globalMin) / bWidth));
      bFrequencies.set(bIndex, bFrequencies.get(bIndex) + 1);
      if (p < bMinValues.get(bIndex)) bMinValues.set(bIndex, p);
      if (p > bMaxValues.get(bIndex)) bMaxValues.set(bIndex, p);
    }

    for (int i = 0; i < numBuckets; i++) {
      if (bFrequencies.get(i) > 0) {
	buckets.add(new HistogramBucket(bMinValues.get(i), bMaxValues.get(i), bFrequencies.get(i)));
      }
    }

    isReady = true;
    lastAnalyzed = System.currentTimeMillis();

    return true;
  }
}
