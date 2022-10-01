package consultantplus.selfemployedapplication.controller;

import consultantplus.selfemployedapplication.model.nodeModel.NodeModel;
import consultantplus.selfemployedapplication.service.FireBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class CRUDController {

    @Autowired
    FireBaseService fireBaseService;

    @PostMapping("/getNextMessage")
    public NodeModel getNode(@RequestParam String clientsAnswer, @RequestParam String previousQuestion)
            throws ExecutionException, InterruptedException {
        return fireBaseService.getNodeModel(clientsAnswer, previousQuestion);
    }
}
