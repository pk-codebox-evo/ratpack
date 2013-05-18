/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ratpackframework.groovy.handling.internal;

import groovy.lang.Closure;
import org.ratpackframework.groovy.ClosureHandlers;
import org.ratpackframework.groovy.handling.ChainBuilder;
import org.ratpackframework.handling.Handler;
import org.ratpackframework.handling.Handlers;

import java.util.List;

import static org.ratpackframework.groovy.Closures.action;

public class DefaultChainBuilder implements ChainBuilder {

  private final List<Handler> handlers;

  DefaultChainBuilder(List<Handler> handlers) {
    this.handlers = handlers;
  }

  public void handler(Closure<?> handler) {
    add(ClosureHandlers.handler(handler));
  }

  public void chain(Closure<?> handlers) {
    add(chainBuildingHandler(handlers));
  }

  public void path(String path, Closure<?> handlers) {
    add(Handlers.path(path, chainBuildingHandler(handlers)));
  }

  public void all(String path, Closure<?> handler) {
    add(ClosureHandlers.handler(path, handler));
  }

  public void handler(String path, List<String> methods, Closure<?> handler) {
    add(ClosureHandlers.handler(path, methods, handler));
  }

  public void get(String path, Closure<?> handler) {
    add(ClosureHandlers.get(path, handler));
  }

  public void get(Closure<?> handler) {
    get("", handler);
  }

  public void post(String path, Closure<?> handler) {
    add(ClosureHandlers.post(path, handler));
  }

  public void post(Closure<?> handler) {
    post("", handler);
  }

  public void assets(String path, String... indexFiles) {
    add(Handlers.assets(path, indexFiles));
  }

  public void context(Object object, Closure<?> handlers) {
    add(Handlers.context(object, chainBuildingHandler(handlers)));
  }

  public void fsContext(String path, Closure<?> handlers) {
    add(Handlers.fsContext(path, chainBuildingHandler(handlers)));
  }

  private Handler chainBuildingHandler(Closure<?> handlers) {
    return new ChainBuildingHandler(action(handlers));
  }

  public void add(Handler handler) {
    handlers.add(handler);
  }


}
