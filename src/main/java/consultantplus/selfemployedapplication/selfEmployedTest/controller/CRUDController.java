package consultantplus.selfemployedapplication.selfEmployedTest.controller;

import consultantplus.selfemployedapplication.reg_dered.model.Reg_DeRegModel;
import consultantplus.selfemployedapplication.reg_dered.service.Reg_DeRegService;
import consultantplus.selfemployedapplication.selfEmployedTest.service.FireBaseService;
import consultantplus.selfemployedapplication.selfEmployedTest.model.NodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class CRUDController {

    @Autowired
    FireBaseService fireBaseService;

    @Autowired
    Reg_DeRegService reg_deRegService;

    @PostMapping("/getNextMessage")
    public NodeModel getNode(@RequestParam String clientsAnswer, @RequestParam String previousQuestion)
            throws ExecutionException, InterruptedException {
        return fireBaseService.getNodeModel(clientsAnswer, previousQuestion);
    }

    @PostMapping("/getMessageById")
    public Reg_DeRegModel getReg_deReg(@RequestParam Long Id) throws ExecutionException, InterruptedException {
        return  reg_deRegService.getInfo(Id);
    }
}
