package consultantplus.selfemployedapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import consultantplus.selfemployedapplication.model.selfemployedmodel.SelfEmployedModel;
import consultantplus.selfemployedapplication.model.selfemployedmodel.SelfEmployedStatus;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Контроллер для проверки статуса ИНН.
 * Имеет POST метод, принимающий в качестве параметра строку inn
 * (можно обращаться как POST http://localhost:8080/app/v1/isSelfEmployed?inn=*
 * Вместо звездочки подставить числом инн, которое ввел пользователь)
 */
@RestController
@RequestMapping("/app/v1")
public class InnCheckController {

    /**
     *
     * @param inn число, введенное пользователем
     * @return ResponseEntity (объект класса SelfEmployedModel с полями message и isSelfEmployed,
     * в первом указывается сообщение из налоговой, во втором статус самозанятого true/false,
     * если из налоговой пришла ошибка, в статусе все равно будет false)
     * @throws JSONException
     * @throws JsonProcessingException
     */
    @PostMapping("/isSelfEmployed")
    public ResponseEntity<SelfEmployedModel> isSelfEmployed(@RequestParam String inn) throws JSONException,
            JsonProcessingException {

        /**
         * Создается объект класса SelfEmployedStatus, в котором он подключается к налоговой,
         * по инн получает статус или возвращает ошибку, расшивровка которой будет указана в message,
         * передает полученные данные в SelfEmployedModel, где они пребразуются в вид для отправки в формате JSON
         */
        SelfEmployedStatus selfEmployedStatus = new SelfEmployedStatus();

        return new ResponseEntity<>(new SelfEmployedModel(selfEmployedStatus
                .setTaxUri()
                .createRestTemplate()
                .createHttpHeaders()
                .setContentType()
                .createJSONObject()
                .populateJSONObject("inn", inn)
                .populateJSONObject("requestDate", LocalDate.now().toString())
                .createRequest()
                .getResponseAsEntity()
                .initializeObjectMapper()
                .injectJsonRoot()
                .getJsonRoot(), selfEmployedStatus.getStatusCode()), HttpStatus.OK);
    }

 }
