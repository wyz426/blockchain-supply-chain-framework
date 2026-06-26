package com.blc.scm.pass.FabricClient;

import com.blc.scm.pass.exception.CustomError;
import com.blc.scm.pass.exception.LocalRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Elaine Huang
 * @date 2024/4/26 1:02 AM
 * @signature Do it while you can!
 */
@Slf4j
@RestController
@RequestMapping("/fabricclient")
public class FabricClientController {
    @Autowired
    private FabricClientService fabricClientService;

    @PostMapping("/submit")
    public void submit(@RequestBody FabricRequest request, @RequestParam("blockchainName") String network,@RequestParam("orgId") Integer orgId) {
        try {
            fabricClientService.submitTransaction(network, orgId, request);
        } catch (Exception e) {
            throw new LocalRuntimeException(CustomError.FABRIC_SUBMIT_ERROR);
        }
    }

    @GetMapping("/evaluate")
    public String evaluate(@RequestBody FabricRequest request, @RequestParam("blockchainName") String network,@RequestParam("orgId") Integer orgId) {
        byte[] res;
        try {
            res = fabricClientService.evaluateTransaction(network, orgId, request);
        } catch (Exception e) {
            throw new LocalRuntimeException(CustomError.FABRIC_EVALUATE_ERROR);
        }
        return new String(res);
    }
}
