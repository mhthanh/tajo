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

import org.apache.tajo.catalog.json.CatalogGsonHelper;
import org.apache.tajo.catalog.proto.CatalogProtos;
import org.apache.tajo.common.ProtoObject;
import org.apache.tajo.json.GsonObject;
import org.apache.tajo.util.TUtil;

import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;

public abstract class Histogram implements ProtoObject<CatalogProtos.HistogramProto>, Cloneable, GsonObject {
  @Expose private String lastAnalyzed = null; // optional
  @Expose private List<HistogramBucket> buckets = null; // repeated

  public Histogram() {
    buckets = TUtil.newList();
  }

  public Histogram(CatalogProtos.HistogramProto proto) {
    if (proto.hasLastAnalyzed()) {
      this.lastAnalyzed = proto.getLastAnalyzed();
    }
    
    this.buckets = TUtil.newList();
    for (CatalogProtos.HistogramBucketProto bucketProto : proto.getBucketsList()) {
      this.buckets.add(new HistogramBucket(bucketProto));
    }
  }

  public String getLastAnalyzed() {
    return this.lastAnalyzed;
  }
  
  public void setLastAnalyzed(String lastAnalyzed) {
    this.lastAnalyzed = lastAnalyzed;
  }
  
  public List<HistogramBucket> getBuckets() {
    return this.buckets;
  }

  public void setBuckets(List<HistogramBucket> buckets) {
    this.buckets = new ArrayList<HistogramBucket>(buckets);
  }

  public void addBucket(HistogramBucket bucket) {
    this.buckets.add(bucket);
  }

  public int getBucketsCount() {
    return this.buckets.size();
  }

  public boolean equals(Object obj) {
    if (obj instanceof Histogram) {
      Histogram other = (Histogram) obj;
      return getLastAnalyzed().equals(other.getLastAnalyzed())
          && TUtil.checkEquals(this.buckets, other.buckets);
    } else {
      return false;
    }
  }

  public int hashCode() {
    return Objects.hashCode(this.lastAnalyzed, this.buckets);
  }

  public Object clone() throws CloneNotSupportedException {
    Histogram hist = (Histogram) super.clone();
    hist.lastAnalyzed = this.lastAnalyzed;
    hist.buckets = new ArrayList<HistogramBucket>(this.buckets);
    return hist;
  }

  public String toString() {
    return CatalogGsonHelper.getPrettyInstance().toJson(this, Histogram.class);
  }

  @Override
  public String toJson() {
    return CatalogGsonHelper.toJson(this, Histogram.class);
  }


  @Override
  public CatalogProtos.HistogramProto getProto() {
    CatalogProtos.HistogramProto.Builder builder = CatalogProtos.HistogramProto.newBuilder();
    if (this.lastAnalyzed != null) {
      builder.setLastAnalyzed(this.lastAnalyzed);
    }
    if (this.buckets != null) {
      for (HistogramBucket bucket : buckets) {
        builder.addBuckets(bucket.getProto());
      }
    }
    return builder.build();
  }
}
