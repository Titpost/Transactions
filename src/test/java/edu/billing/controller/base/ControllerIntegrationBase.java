package edu.billing.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class ControllerIntegrationBase {

    protected static final String BASE_URI = "http://localhost:8080/api/account";
    public static final String NEW_ID = "1234 5678 0000 0000";
    public static final String UNKNOWN_ID = "Unknown";
    public static final String KNOWN_ID = "7777 7777 7777 7777";

    @Autowired
    protected RestTemplate template;

    /**
     * CORS Headers
     *
     * @param headers - HttpHeader entries unity
     */
    protected void validateCORSHttpHeaders(HttpHeaders headers){
        assertThat(headers.getAccessControlAllowOrigin(), is("*"));
        assertThat(headers.getAccessControlAllowHeaders(), hasItem("*"));
        assertThat(headers.getAccessControlMaxAge(), is(3600L));
        assertThat(headers.getAccessControlAllowMethods(), hasItems(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.DELETE));
    }
}
