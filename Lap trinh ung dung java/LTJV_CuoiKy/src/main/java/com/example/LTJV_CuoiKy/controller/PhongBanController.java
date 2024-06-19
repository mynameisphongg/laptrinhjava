package com.example.LTJV_CuoiKy.controller;

import com.example.LTJV_CuoiKy.model.PhongBan;
import com.example.LTJV_CuoiKy.repository.PhongBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/phongban")
public class PhongBanController {

    private final PhongBanRepository phongBanRepository;

    @Autowired
    public PhongBanController(PhongBanRepository phongBanRepository) {
        this.phongBanRepository = phongBanRepository;
    }

    // Hiển thị danh sách phòng ban
    @GetMapping("/list")
    public String listPhongBan(Model model) {
        List<PhongBan> listPhongBan = phongBanRepository.findAll();
        model.addAttribute("listPhongBan", listPhongBan);
        return "list-phongban";
    }

    // Form thêm phòng ban
    @GetMapping("/add")
    public String addPhongBanForm(Model model) {
        model.addAttribute("phongBan", new PhongBan());
        return "add-phongban";
    }

    // Xử lý thêm phòng ban
    @PostMapping("/add")
    public String addPhongBanSubmit(@Valid @ModelAttribute("phongBan") PhongBan phongBan, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-phongban";
        }
        // Gán giá trị cho maPhong trước khi lưu
        // Ví dụ, có thể sử dụng logic để sinh tự động maPhong hoặc validate unique maPhong
        // Ở đây tạm thời gán cứng là "PB"
        phongBan.setMaPhong("PB");

        phongBanRepository.save(phongBan);
        return "redirect:/phongban/list";
    }

    // Form sửa phòng ban
    @GetMapping("/edit/{maPhong}")
    public String editPhongBanForm(@PathVariable String maPhong, Model model) {
        Optional<PhongBan> optionalPhongBan = phongBanRepository.findById(maPhong);
        if (optionalPhongBan.isPresent()) {
            model.addAttribute("phongBan", optionalPhongBan.get());
            return "edit-phongban";
        } else {
            return "redirect:/phongban/list";
        }
    }

    // Xử lý sửa phòng ban
    @PostMapping("/edit/{maPhong}")
    public String editPhongBanSubmit(@Valid @ModelAttribute("phongBan") PhongBan phongBan, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-phongban";
        }
        // Gán giá trị cho maPhong trước khi lưu
        // Ví dụ, có thể sử dụng logic để validate unique maPhong
        // Nếu không cần gán giá trị, bỏ qua dòng này
        // phongBan.setMaPhong("PB");

        phongBanRepository.save(phongBan);
        return "redirect:/phongban/list";
    }

    // Xử lý xóa phòng ban
    @GetMapping("/delete/{maPhong}")
    public String deletePhongBan(@PathVariable String maPhong) {
        phongBanRepository.deleteById(maPhong);
        return "redirect:/phongban/list";
    }
}
