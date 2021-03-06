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

package org.apache.tajo.util;

import java.util.Collection;

import static org.apache.tajo.rpc.protocolrecords.PrimitiveProtos.*;

public class ProtoUtil {
  public static final BoolProto TRUE = BoolProto.newBuilder().setValue(true).build();
  public static final BoolProto FALSE = BoolProto.newBuilder().setValue(false).build();

  public static final NullProto NULL_PROTO = NullProto.newBuilder().build();

  public static StringProto convertString(String value) {
    return StringProto.newBuilder().setValue(value).build();
  }

  public static StringListProto convertStrings(Collection<String> strings) {
    return StringListProto.newBuilder().addAllValues(strings).build();
  }

  public static Collection<String> convertStrings(StringListProto strings) {
    return strings.getValuesList();
  }
}
