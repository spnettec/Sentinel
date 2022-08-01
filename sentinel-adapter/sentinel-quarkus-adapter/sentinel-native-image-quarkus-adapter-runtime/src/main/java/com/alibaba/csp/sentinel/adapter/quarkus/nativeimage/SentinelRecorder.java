/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.adapter.quarkus.nativeimage;

import com.alibaba.csp.sentinel.command.vo.NodeVo;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.reader.ObjectReaderCreator;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.alibaba.fastjson2.writer.ObjectWriterAdapter;
import com.alibaba.fastjson2.writer.ObjectWriterCreator;
import io.quarkus.runtime.annotations.Recorder;

/**
 * @author sea
 */
@Recorder
public class SentinelRecorder {

    /**
     * register fastjson serializer deserializer class info
     */
    public void init() {
        JSONFactory.getDefaultObjectWriterProvider().registerIfAbsent(NodeVo.class, ObjectWriterCreator.INSTANCE.createObjectWriter(NodeVo.class));
        JSONFactory.getDefaultObjectWriterProvider().registerIfAbsent(FlowRule.class, ObjectWriterCreator.INSTANCE.createObjectWriter(FlowRule.class));
        JSONFactory.getDefaultObjectWriterProvider().registerIfAbsent(SystemRule.class, ObjectWriterCreator.INSTANCE.createObjectWriter(SystemRule.class));
        JSONFactory.getDefaultObjectWriterProvider().registerIfAbsent(DegradeRule.class, ObjectWriterCreator.INSTANCE.createObjectWriter(DegradeRule.class));
        JSONFactory.getDefaultObjectWriterProvider().registerIfAbsent(AuthorityRule.class, ObjectWriterCreator.INSTANCE.createObjectWriter(AuthorityRule.class));
        JSONFactory.getDefaultObjectWriterProvider().registerIfAbsent(ParamFlowRule.class, ObjectWriterCreator.INSTANCE.createObjectWriter(ParamFlowRule.class));

        JSONFactory.getDefaultObjectReaderProvider().registerIfAbsent(NodeVo.class, ObjectReaderCreator.INSTANCE.createObjectReader(NodeVo.class));
        JSONFactory.getDefaultObjectReaderProvider().registerIfAbsent(FlowRule.class, ObjectReaderCreator.INSTANCE.createObjectReader(FlowRule.class));
        JSONFactory.getDefaultObjectReaderProvider().registerIfAbsent(SystemRule.class, ObjectReaderCreator.INSTANCE.createObjectReader(SystemRule.class));
        JSONFactory.getDefaultObjectReaderProvider().registerIfAbsent(DegradeRule.class, ObjectReaderCreator.INSTANCE.createObjectReader(DegradeRule.class));
        JSONFactory.getDefaultObjectReaderProvider().registerIfAbsent(AuthorityRule.class, ObjectReaderCreator.INSTANCE.createObjectReader(AuthorityRule.class));
        JSONFactory.getDefaultObjectReaderProvider().registerIfAbsent(ParamFlowRule.class, ObjectReaderCreator.INSTANCE.createObjectReader(ParamFlowRule.class));
    }
}
