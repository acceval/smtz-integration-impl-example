package com.acceval.core.microservice;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.cache.MSUtilCache;
import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.BaseBeanUtil;

@Service
public class MicroServiceUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceUtil.class);

	@Value("${microservice.url}")
	private String url;

	public Object getForObject(MicroServiceRequest microServiceRequest, Class<?> type) throws MicroServiceUtilException {
		return getForObject(microServiceRequest, null, type);
	}

	public Object getForObject(MicroServiceRequest microServiceRequest, MultiValueMap<String, String> mvmValue, Class<?> type)
			throws MicroServiceUtilException {

		/** null checking */
		microServiceRequest.assertNull();
		Optional.ofNullable(type).orElseThrow(() -> new MicroServiceUtilException(MicroServiceUtil.class, "REST Type is null."));
		//		DiscoveryClient discoveryClient = microServiceRequest.getDiscoveryClient();

		RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);

		/** bypass cert checking??*/
		try {
			TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			};
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			restTemplate.setRequestFactory(requestFactory);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e2) {
			e2.printStackTrace();
		}

		//		String zuulService = "ZUUL-SERVER";
		String msService = microServiceRequest.getMsService();
		String msFunction = microServiceRequest.getMsFunction();
		String param = microServiceRequest.getParam() == null ? "" : microServiceRequest.getParam();
		String token = microServiceRequest.getToken();
		String companyID = microServiceRequest.getCompanyID();

		//		List<ServiceInstance> instances = discoveryClient.getInstances(zuulService);
		//		if (instances.isEmpty())
		//			throw new MicroServiceUtilException(MicroServiceUtil.class, zuulService + " Service is Not Available!");
		//		ServiceInstance instance = instances.get(0);
		//		String host = instance.getHost();
		//		String url = "http://" + msService.replaceFirst(Pattern.quote("/"), "") + "/" + msFunction + "/" + param;

		String url2 = url + "/" + msService.replaceFirst(Pattern.quote("/"), "") + "/" + msFunction + "/" + param;

		if (mvmValue != null && !mvmValue.keySet().isEmpty()) {
			UriComponentsBuilder uriCompBuilder = UriComponentsBuilder.fromHttpUrl(url2);
			for (String key : mvmValue.keySet()) {
				uriCompBuilder.queryParam(key, mvmValue.get(key).toArray());
			}
			url2 = uriCompBuilder.toUriString();
		}

		try {
			Object object = null;

			/** caching */
			MSUtilCache hazelcastCache = null;
			try {
				hazelcastCache = (MSUtilCache) BaseBeanUtil.getBean(MSUtilCache.class);
			} catch (NoSuchBeanDefinitionException e) {
				// not implementing cache
			}

			if (hazelcastCache == null) {
				LOGGER.debug("Firing URL: " + url2);
				object = fireRequest(restTemplate, token, url2, type);
			} else {
				object = hazelcastCache.restTemplateExchange(this, restTemplate, token, url, url2, companyID, type);
			}

			return object;
		} catch (Throwable e) {
			LOGGER.error("Error occur when fire [" + url2 + "] \r\n" + e.getMessage(), e);
		}

		return null;
	}

	public Object fireRequest(RestTemplate restTemplate, String token, String url, Class<?> type) throws Throwable {
		Object object = null;

        HttpHeaders headers = buildHeaders(token);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity respEntity = restTemplate.exchange(url, HttpMethod.GET, entity, type);
        object = respEntity.getBody();

//		object = restTemplate.getForObject(url, type);

		return object;
	}

	private HttpHeaders buildHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();

        if (token != null) {

            headers.set("Authorization", token);

            CurrentUser user = PrincipalUtil.getCurrentUser();

            if (user != null) {
                headers.set(PrincipalUtil.HDRKEY_COMPANYID, String.valueOf(user.getCompanyId()));
                headers.set(PrincipalUtil.HDRKEY_COMPANYCODE, user.getCompanyCode());
                headers.set(PrincipalUtil.HDRKEY_SCHEMANAME, user.getSchemaName());
                headers.set(PrincipalUtil.HDRKEY_TIMEZONEID, user.getTimeZone());
                if (user.getServicePackage() != null) {
                    headers.set(PrincipalUtil.HDRKEY_SERVICEPACKAGE, user.getServicePackage().toString());
                }
            }

        } else {
            CurrentUser user = PrincipalUtil.getSystemUser();

            if (user != null) {
                headers.set(PrincipalUtil.HDRKEY_COMPANYID, String.valueOf(user.getCompanyId()));
                headers.set(PrincipalUtil.HDRKEY_COMPANYCODE, user.getCompanyCode());
                headers.set(PrincipalUtil.HDRKEY_SCHEMANAME, user.getSchemaName());
                headers.set(PrincipalUtil.HDRKEY_TIMEZONEID, user.getTimeZone());
                if (user.getServicePackage() != null) {
                    headers.set(PrincipalUtil.HDRKEY_SERVICEPACKAGE, user.getServicePackage().toString());
                }
            } else {
                headers.set(PrincipalUtil.HDRKEY_COMPANYID, "");
                headers.set(PrincipalUtil.HDRKEY_COMPANYCODE, "");
                headers.set(PrincipalUtil.HDRKEY_SCHEMANAME, "");
                headers.set(PrincipalUtil.HDRKEY_TIMEZONEID, "");
            }
        }

        return headers;
    }
}
