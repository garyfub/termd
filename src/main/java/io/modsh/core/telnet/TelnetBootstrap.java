/*
 * Copyright 2014 Julien Viet
 *
 * Julien Viet licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */
package io.modsh.core.telnet;

import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.net.NetServer;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TelnetBootstrap {

  public static void main(String[] args) throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    new TelnetBootstrap("localhost", 4000).start();
    latch.await();
  }

  private final String host;
  private final int port;
  private final Vertx vertx;
  private NetServer server;

  public TelnetBootstrap(String host, int port) {
    this(VertxFactory.newVertx(), host, port);
  }

  public TelnetBootstrap(Vertx vertx, String host, int port) {
    this.vertx = vertx;
    this.host = host;
    this.port = port;
  }

  public void start() {
    NetServer server = vertx.createNetServer();
    server.connectHandler(new TelnetHandler());
    server.listen(port, host);
  }
}
