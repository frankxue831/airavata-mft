/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 */

package org.apache.airavata.mft.transport.tcp.server;

import org.apache.airavata.mft.core.api.ConnectorChannel;
import org.apache.airavata.mft.core.api.SourceConnector;
import org.apache.airavata.mft.core.bufferedImpl.channel.AbstractConnector;
import org.apache.airavata.mft.core.bufferedImpl.channel.InChannel;
import org.apache.airavata.mft.transport.tcp.Constants;

import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a connection between remote client and  FileServer
 */
public class FileServerConnector extends AbstractConnector implements SourceConnector {

    private LinkedBlockingQueue<SocketChannel> acceptedChannelCache = new
            LinkedBlockingQueue<>(Constants.MAX_CACHING_CONNECTIONS);


    @Override
    public ConnectorChannel openChannel() throws Exception {
        SocketChannel socketChannel = this.acceptedChannelCache.take();
        InChannel inChannel = new InChannel(socketChannel, this);
        return inChannel;

    }


    public void addChannel(SocketChannel channel) throws InterruptedException {
        this.acceptedChannelCache.put(channel);
    }


}
