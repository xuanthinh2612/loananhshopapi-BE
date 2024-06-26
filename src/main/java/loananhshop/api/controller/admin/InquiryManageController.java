package loananhshop.api.controller.admin;

import loananhshop.api.common.CommonConst;
import loananhshop.api.model.Inquiry;
import loananhshop.api.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/inquiry")
public class InquiryManageController extends AdminBaseController {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping("/list")
    public String listInquiry(@RequestParam("status") int status, Model model) {
        List<Inquiry> inquiryList = inquiryService.findAllByStatus(status);
        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("inquiryStatus", status);
        return "admin/inquiry/index";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable("id") Long id, Model model) {
        Inquiry inquiry = inquiryService.findById(id);
        // flag off = confirm
        inquiry.setStatus(CommonConst.FLAG_OFF);
        inquiryService.save(inquiry);
        return listInquiry(CommonConst.FLAG_OFF, model);
    }
}
