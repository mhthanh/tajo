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

import java.util.List;

import org.apache.tajo.catalog.proto.CatalogProtos.HistogramProto;

/**
 * A histogram where the entire value range of the data points are divided into buckets of equal sizes.
 */
public class EquiWidthHistogram extends Histogram {

  public EquiWidthHistogram() {
    super();
  }

  public EquiWidthHistogram(HistogramProto proto) {
    super(proto);
  }

  @Override
  public boolean construct(List<Double> samples) {
    Double min = Double.MAX_VALUE;
    Double max = Double.MIN_VALUE;
    for(Double p : samples) {
      if(p < min) min = p;
      if(p > max) max = p;
    }
    
    int numBuckets = samples.size() < DEFAULT_NUM_BUCKETS ? samples.size() : DEFAULT_NUM_BUCKETS;
    Double width = (max - min) / numBuckets;
    for(int i=0; i<numBuckets; i++) {
      
    }
    
    // lastAnalyzed =
    isReady = true;
    return true;
  }
}
