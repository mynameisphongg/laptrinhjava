package com.example.LTJV_CuoiKy.controller;

import com.example.LTJV_CuoiKy.model.NhanVien;
import com.example.LTJV_CuoiKy.service.NhanVienService;
import com.example.LTJV_CuoiKy.service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    private final NhanVienService nhanVienService;
    private final PhongBanService phongBanService;

    @Autowired
    public NhanVienController(NhanVienService nhanVienService, PhongBanService phongBanService) {
        this.nhanVienService = nhanVienService;
        this.phongBanService = phongBanService;
    }

    @GetMapping("/list")
    public String listNhanVien(Model model) {
        model.addAttribute("listNhanVien", nhanVienService.getAllNhanVien());
        return "list-nhanvien"; // Trả về view template list-nhanvien.html
    }

    @GetMapping("/add")
    public String addNhanVienForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("listPhongBan", phongBanService.getAllPhongBan());
        return "add-nhanvien";
    }

    @PostMapping("/add")
    public String addNhanVienSubmit(@Valid @ModelAttribute("nhanVien") NhanVien nhanVien,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listPhongBan", phongBanService.getAllPhongBan());
            return "add-nhanvien";
        }

        try {
            nhanVienService.addNhanVien(nhanVien);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listPhongBan", phongBanService.getAllPhongBan());
            return "add-nhanvien";
        }

        return "redirect:/nhanvien/list";
    }

    @GetMapping("/delete/{maNV}")
    public String deleteNhanVien(@PathVariable String maNV) {
        nhanVienService.deleteNhanVienByMaNV(maNV);
        return "redirect:/nhanvien/list";
    }

    @GetMapping("/edit/{maNV}")
    public String editNhanVienForm(@PathVariable String maNV, Model model) {
        NhanVien nhanVien = nhanVienService.getNhanVienByMaNV(maNV)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID:" + maNV));
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("listPhongBan", phongBanService.getAllPhongBan());
        return "edit-nhanvien";
    }

    @PostMapping("/edit/{maNV}")
    public String editNhanVienSubmit(@PathVariable String maNV, @Valid @ModelAttribute("nhanVien") NhanVien nhanVien,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listPhongBan", phongBanService.getAllPhongBan());
            return "edit-nhanvien";
        }

        try {
            nhanVienService.updateNhanVienByMaNV(maNV, nhanVien);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listPhongBan", phongBanService.getAllPhongBan());
            return "edit-nhanvien";
        }

        return "redirect:/nhanvien/list";
    }
}
