/*
 * Copyright 2002-2017 the original author or authors.
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpHeaders;

/**
 * Base implementation of {@link ClientHttpRequest} that buffers output
 * in a byte array before sending it over the wire.
 *
 * @author Arjen Poutsma
 * @since 3.0.6
 */
abstract class AbstractBufferingClientHttpRequest extends AbstractClientHttpRequest {

	private ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream(1024);


	@Override
	protected OutputStream getBodyInternal(HttpHeaders headers) throws IOException {
		return this.bufferedOutput;
	}

	@Override
	protected ClientHttpResponse executeInternal(HttpHeaders headers) throws IOException {
		byte[] bytes = this.bufferedOutput.toByteArray();
		if (headers.getContentLength() < 0) {
			headers.setContentLength(bytes.length);
		}
		/**
		 * 如果this为InterceptingClientHttpRequest对象，就执行它的executeInternal方法
		 * 执行InterceptingRequestExecution的execute方法，然后执行下一个AbstractBufferingClientHttpRequest对象，
		 * 即SimpleBufferingClientHttpRequest，执行executeInternal方法，最后通过HttpURLConnection发起http请求
		 *
		 * 1.默认情况下：RestTemplate底层通过SimpleClientHttpRequestFactory对象创建HttpURLConnection发起远程调用，
		 * 2.spring提供了多种对HTTP客户端库的支持：
		 *   HttpComponentsClientHttpRequestFactory使用Apache Http Client
		 *   比如：
		 *     @Bean
		 *     public RestTemplate restTemplate() {
		 *         return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		 *     }
		 *   OkHttp3ClientHttpRequestFactory使用okhttp3 OkHttpClient
		 */
		ClientHttpResponse result = executeInternal(headers, bytes);
		this.bufferedOutput = new ByteArrayOutputStream(0);
		return result;
	}

	/**
	 * Abstract template method that writes the given headers and content to the HTTP request.
	 * @param headers the HTTP headers
	 * @param bufferedOutput the body content
	 * @return the response object for the executed request
	 */
	protected abstract ClientHttpResponse executeInternal(HttpHeaders headers, byte[] bufferedOutput)
			throws IOException;


}
