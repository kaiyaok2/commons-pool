/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.pool2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.TestGenericKeyedObjectPool;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

/**
 * Unit tests for {@link PoolUtils}.
 *
 * TODO Replace our own mocking with a mocking library like Mockito.
 */
public class TestPoolUtils {

    /** Period between checks for minIdle tests. Increase this if you happen to get too many false failures. */
    private static final int CHECK_PERIOD = 300;

    /** Times to let the minIdle check run. */
    private static final int CHECK_COUNT = 4;

    /** Sleep time to let the minIdle tests run CHECK_COUNT times. */
    private static final int CHECK_SLEEP_PERIOD = CHECK_PERIOD * (CHECK_COUNT - 1) + CHECK_PERIOD / 2;

    @Test
    @Test
    @Test
    public void testCheckRethrow() {
        try {
            PoolUtils.checkRethrow(new Exception());
        } catch (final Throwable t) {
            fail("PoolUtils.checkRethrow(Throwable) must rethrow only ThreadDeath and VirtualMachineError.");
        }
        try {
            PoolUtils.checkRethrow(new ThreadDeath());
            fail("PoolUtils.checkRethrow(Throwable) must rethrow ThreadDeath.");
        } catch (final ThreadDeath td) {
            // expected
        } catch (final Throwable t) {
            fail("PoolUtils.checkRethrow(Throwable) must rethrow only ThreadDeath and VirtualMachineError.");
        }
        try {
            PoolUtils.checkRethrow(new InternalError()); // InternalError extends VirtualMachineError
            fail("PoolUtils.checkRethrow(Throwable) must rethrow VirtualMachineError.");
        } catch (final VirtualMachineError td) {
            // expected
        } catch (final Throwable t) {
            fail("PoolUtils.checkRethrow(Throwable) must rethrow only ThreadDeath and VirtualMachineError.");
        }
    }

    @Test
    @Test
    @Test
    public void testJavaBeanInstantiation() {
        assertNotNull(new PoolUtils());
    }

