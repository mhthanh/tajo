/*
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

package org.apache.tajo.engine.planner.logical;

import org.apache.tajo.engine.eval.EvalNode;

/**
 * An interface for logical node which is able to filter tuples.
 */
public interface SelectableNode {

  /**
   * Checking if it has filter condition
   *
   * @return True if it has filter condition. Otherwise, it will return false.
   */
  public boolean hasQual();

  /**
   * Set a filter condition.
   *
   * @param eval EvalNode resulting in a boolean result.
   */
  public void setQual(EvalNode eval);

  /**
   * Get a filter condition
   *
   * @return Filter Condition
   */
  public EvalNode getQual();
}
