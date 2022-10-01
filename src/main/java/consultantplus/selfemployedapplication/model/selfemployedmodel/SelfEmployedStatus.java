package consultantplus.selfemployedapplication.model.selfemployedmodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class SelfEmployedStatus {

    private URI TaxURI = null;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private JSONObject jsonObject;
    private ObjectMapper objectMapper;
    private HttpEntity<String> request;
    private JsonNode root;
    private HttpStatus statusCode;
    private ResponseEntity<String> responseEntity;
    private boolean errorHappened = false;

    private ObjectNode objectNodeRoot;

    public SelfEmployedStatus() {
    }

    /**
     * установка урла для запроса в налоговую
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus setTaxUri() {
        try {
            this.TaxURI = new URI("https://statusnpd.nalog.ru/api/v1/tracker/taxpayer_status");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * установка таймаутов ожидания ответа
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus createRestTemplate() {
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMinutes(1))
                .setReadTimeout(Duration.ofMinutes(1))
                .build();
        return this;
    }

    /**
     * установка хэдеров для запроса
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus createHttpHeaders() {
        this.httpHeaders = new HttpHeaders();
        return this;
    }

    /**
     * установка типа контента, чтобы отправить Json
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus setContentType() {
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return this;
    }

    /**
     * создание Json для отправи
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus createJSONObject() {
        this.jsonObject = new JSONObject();
        return this;
    }

    /**
     * заполнения Json необходимыми полями для запроса
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus populateJSONObject(String parameterName, String parameterValue) throws JSONException {
        this.jsonObject.put(parameterName, parameterValue);
        return this;
    }

    /**
     * из Json и хэдеров создается Entity для отправки
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus createRequest() {
        this.request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        return this;
    }

    /**
     * получает ответ из налоговой, строит из него полученный Entity,
     * если инн корректный и не произошло ошибок, сохраняется Entity,
     * если что-то пошло не так, то создает Entity из сообщения ошибки и Http статуса ошибки
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus getResponseAsEntity() {
        try {
            this.responseEntity = this.restTemplate.postForEntity(this.TaxURI, request, String.class);
        } catch (RestClientException e) {
            if (e instanceof HttpClientErrorException) {
                this.responseEntity = new ResponseEntity<>(e.toString().replaceAll("[^А-Яа-я0-9 ]", ""), ((HttpClientErrorException) e).getStatusCode());
                this.errorHappened = true;
            }
        }

        return this;
    }

    /**
     * созраняет Http статус
     *
     * @return вызвавший объект
     */
    public HttpStatus getStatusCode() {
        this.statusCode = this.responseEntity.getStatusCode();
        return statusCode;
    }

    /**
     * создает объект ObjectMapper' a для последующего преобразования в TreeNode
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus initializeObjectMapper() {
        this.objectMapper = new ObjectMapper();
        return this;
    }

    /**
     * если ошибок не было, то создает JsonNode, если были, то ObjectNode (они оба имлементируют TreeNode)
     *
     * @return вызвавший объект
     */
    public SelfEmployedStatus injectJsonRoot() throws JsonProcessingException {
        if (errorHappened) {
            this.objectNodeRoot = objectMapper.createObjectNode().put("message", this.responseEntity.getBody());
            return this;
        }
        this.root = objectMapper.readTree(this.responseEntity.getBody());
        return this;
    }

    /**
     * @return тело сообщения из наоговой - объект класса, имлементирующего TreeNode
     * в зависимости от наличия ошибки
     */
    public TreeNode getJsonRoot() {
        if (errorHappened) {
            return this.objectNodeRoot;
        }
        return this.root;
    }

}