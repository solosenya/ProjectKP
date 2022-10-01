package consultantplus.selfemployedapplication.model.selfemployedmodel;

import com.fasterxml.jackson.core.TreeNode;
import org.springframework.http.HttpStatus;

/**
 * Класс имеет три поля: сообщение из налоговой, boolean переменную errorHappened,
 * показывающуб true, если налоговая не дала данных по инн(произошла ошибка) и false в противоположном случае,
 * а также полученный в случае отсутсвия ошибки статус самозанятого (тоже true/false)
 */
public class SelfEmployedModel {
    private boolean selfEmployed;
    private String message;
    private boolean errorHappened;

    /**
     * заполняет поля класса в зависимости от наличия ошибки
     */
    public SelfEmployedModel(TreeNode root, HttpStatus httpStatus) {
        if (httpStatus == HttpStatus.OK) {
            if (root.get("status").toString().equals("true")) {
                this.selfEmployed = true;
            } else {
                this.selfEmployed = false;
            }

            this.message = root
                    .get("message")
                    .toString()
                    .replaceAll("[^А-Яа-я0-9 ]", "");
            this.errorHappened = false;

        } else {
            this.message = root
                    .get("message")
                    .toString()
                    .replaceAll("[^А-Яа-я0-9 ]", "")
                    .split("   ")[1];
            this.errorHappened = true;
        }
    }

    public String getMessage() {
        return message;
    }

    public boolean isSelfEmployed() {
        return selfEmployed;
    }

    public boolean isErrorHappened() {
        return errorHappened;
    }
}
