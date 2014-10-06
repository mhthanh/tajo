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

public abstract class ColumnHistogram implements ProtoObject<CatalogProtos.ColumnHistogramProto>, Cloneable, GsonObject {
  @Expose private List<HistogramBucket> buckets = null; // repeated

  public ColumnHistogram() {
    buckets = TUtil.newList();
  }

  public ColumnHistogram(CatalogProtos.ColumnHistogramProto proto) {
    this.buckets = TUtil.newList();
    for (CatalogProtos.HistogramBucketProto bucketProto : proto.getBucketsList()) {
      this.buckets.add(new HistogramBucket(bucketProto));
    }
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
    if (obj instanceof ColumnHistogram) {
      ColumnHistogram other = (ColumnHistogram) obj;
      return TUtil.checkEquals(this.buckets, other.buckets);
    } else {
      return false;
    }
  }

  public int hashCode() {
    return Objects.hashCode(this.buckets);
  }

  public Object clone() throws CloneNotSupportedException {
    ColumnHistogram hist = (ColumnHistogram) super.clone();
    hist.buckets = new ArrayList<HistogramBucket>(this.buckets);
    return hist;
  }

  public String toString() {
    return CatalogGsonHelper.getPrettyInstance().toJson(this, ColumnHistogram.class);
  }

  @Override
  public String toJson() {
    return CatalogGsonHelper.toJson(this, ColumnHistogram.class);
  }


  @Override
  public CatalogProtos.ColumnHistogramProto getProto() {
    CatalogProtos.ColumnHistogramProto.Builder builder = CatalogProtos.ColumnHistogramProto.newBuilder();
    if (this.buckets != null) {
      for (HistogramBucket bucket : buckets) {
        builder.addBuckets(bucket.getProto());
      }
    }
    return builder.build();
  }
}
