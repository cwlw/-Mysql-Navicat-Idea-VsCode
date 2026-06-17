package com.library.controller;

import com.library.service.CardOperateService;
import com.library.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@RestController
@RequestMapping("/api/cardOperate")
public class CardOperateController {

    @Autowired
    private CardOperateService cardOperateService;

    // 新办卡
    @PostMapping("/apply")
    public Result applyCard(@RequestBody Map<String, String> data) {
        String sno = data.get("sno");
        String cardNo = data.get("cardNo");
        return cardOperateService.applyCard(sno, cardNo);
    }

    // 挂失
    @PostMapping("/loss")
    public Result lossCard(@RequestBody Map<String, String> data) {
        String sno = data.get("sno");
        String cardNo = data.get("cardNo");
        return cardOperateService.lossCard(sno, cardNo);
    }

    // 补办
    @PostMapping("/reissue")
    public Result reissueCard(@RequestBody Map<String, String> data) {
        String sno = data.get("sno");
        String originCardNo = data.get("originCardNo");
        String newCardNo = data.get("newCardNo");
        return cardOperateService.reissueCard(sno, originCardNo, newCardNo);
    }

    // 注销
    @PostMapping("/cancel")
    public Result cancelCard(@RequestBody Map<String, String> data) {
        String sno = data.get("sno");
        String cardNo = data.get("cardNo");
        return cardOperateService.cancelCard(sno, cardNo);
    }
}