/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.http.client;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

/**
 * Wrapper for a {@link ClientHttpRequest} that has support for {@link ClientHttpRequestInterceptor ClientHttpRequest} that has support for {@link ClientHttpRequestInterceptors}.
 *
 * @author Arjen Poutsma
 * @since 3.1
 */
class InterceptingClientHttpRequest extends AbstractBufferingClientHttpRequest {

	private final ClientHttpRequestFactory requestFactory;

	private final List<ClientHttpRequestInterceptor> interceptors;

	private HttpMethod method;

	private URI uri;


	protected InterceptingClientHttpRequest(ClientHttpRequestFactory requestFactory,
			List<ClientHttpRequestInterceptor> interceptors, URI uri, HttpMethod method) {
		//在RestTemplate的doExecute方法中会调用createRequest方法，然后调用其父类HttpAccessor的createRequest方法，
		//该方法中getRequestFactory().createRequest()方法中，会创建InterceptingClientHttpRequest对象，并把InterceptingClientHttpRequestFactory的
		//父类AbstractClientHttpRequestFactoryWrapper的属性requestFactory（为SimpleClientHttpRequestFactory）传过来
		this.requestFactory = requestFactory;
		this.interceptors = interceptors;
		this.method = method;
		this.uri = uri;
	}


	@Override
	public HttpMethod getMethod() {
		return this.method;
	}

	@Override
	public String getMethodValue() {
		return this.method.name();
	}

	@Override
	public URI getURI() {
		return this.uri;
	}

	@Override
	protected final ClientHttpResponse executeInternal(HttpHeaders headers, byte[] bufferedOutput) throws IOException {
		//无参构造方法中会把拦截器集合赋值给内部类InterceptingRequestExecution的属性iterator
		InterceptingRequestExecution requestExecution = new InterceptingRequestExecution();
		return requestExecution.execute(this, bufferedOutput);
	}


	private class InterceptingRequestExecution implements ClientHttpRequestExecution {

		private final Iterator<ClientHttpRequestInterceptor> iterator;

		public InterceptingRequestExecution() {
			this.iterator = interceptors.iterator();
		}

		@Override
		public ClientHttpResponse execute(HttpRequest request, byte[] body) throws IOException {
			/**
			 * 遍历执行拦截器
			 */
			if (this.iterator.hasNext()) {
				ClientHttpRequestInterceptor nextInterceptor = this.iterator.next();
				return nextInterceptor.intercept(request, body, this);
			}
			else {
				HttpMethod method = request.getMethod();
				Assert.state(method != null, "No standard HTTP method");
				/**
				 * 在LoadBalancerInterceptor的intercept方法中，在创建LoaderBalancerRequest的过程中，request为ServiceRequestWrapper
				 * 这里的requestFactory为SimpleClientHttpRequestFactory对象
				 * 返回的delegate为SimpleBufferingClientHttpRequest
				 */
				ClientHttpRequest delegate = requestFactory.createRequest(request.getURI(), method);
				request.getHeaders().forEach((key, value) -> delegate.getHeaders().addAll(key, value));
				if (body.length > 0) {
					if (delegate instanceof StreamingHttpOutputMessage) {
						StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) delegate;
						streamingOutputMessage.setBody(outputStream -> StreamUtils.copy(body, outputStream));
					}
					else {
						StreamUtils.copy(body, delegate.getBody());
					}
				}
				/**
				 * delegate为SimpleBufferingClientHttpRequest对象
				 * 执行父类AbstractClientHttpRequest的execute方法
				 * 最后通过HttpURLConnection发起http请求的
				 */
				return delegate.execute();
			}
		}
	}

}
