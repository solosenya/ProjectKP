package consultantplus.selfemployedapplication.model.nodeModel;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class NodeModel {

    public String previousQuestion;

    public String clientsAnswer;

    public String answer;

    public List<String> variants;
}
