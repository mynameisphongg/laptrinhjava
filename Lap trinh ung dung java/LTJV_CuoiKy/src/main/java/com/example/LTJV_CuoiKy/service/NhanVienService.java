package com.example.LTJV_CuoiKy.service;

import com.example.LTJV_CuoiKy.model.NhanVien;
import com.example.LTJV_CuoiKy.model.PhongBan;
import com.example.LTJV_CuoiKy.repository.NhanVienRepository;
import com.example.LTJV_CuoiKy.repository.PhongBanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NhanVienService {

    private final NhanVienRepository nhanVienRepository;
    private final PhongBanRepository phongBanRepository;

    public List<NhanVien> getAllNhanVien() {
        return nhanVienRepository.findAll();
    }

    public Optional<NhanVien> getNhanVienByMaNV(String maNV) {
        return nhanVienRepository.findByMaNV(maNV);
    }

    @Transactional
    public NhanVien addNhanVien(NhanVien nhanVien) {
        validateAndSetMaNV(nhanVien);
        validatePhongBan(nhanVien);
        return nhanVienRepository.save(nhanVien);
    }

    @Transactional
    public NhanVien updateNhanVienByMaNV(String maNV, NhanVien updatedNhanVien) {
        NhanVien existingNhanVien = nhanVienRepository.findByMaNV(maNV)
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại."));

        updateNhanVienFields(existingNhanVien, updatedNhanVien);
        validatePhongBan(existingNhanVien);

        return nhanVienRepository.save(existingNhanVien);
    }

    @Transactional
    public void deleteNhanVienByMaNV(String maNV) {
        NhanVien nhanVien = nhanVienRepository.findByMaNV(maNV)
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại."));
        nhanVienRepository.delete(nhanVien);
    }

    private void updateNhanVienFields(NhanVien existingNhanVien, NhanVien updatedNhanVien) {
        existingNhanVien.setTenNV(updatedNhanVien.getTenNV());
        existingNhanVien.setPhai(updatedNhanVien.getPhai());
        existingNhanVien.setNoiSinh(updatedNhanVien.getNoiSinh());
        existingNhanVien.setLuong(updatedNhanVien.getLuong());
        existingNhanVien.setPhongBan(updatedNhanVien.getPhongBan());
    }

    private void validateAndSetMaNV(NhanVien nhanVien) {
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống.");
        }
        // Các logic validation khác (nếu có) có thể được thêm vào đây
    }

    private void validatePhongBan(NhanVien nhanVien) {
        PhongBan phongBan = nhanVien.getPhongBan();
        if (phongBan != null && phongBan.getMaPhong() != null) {
            Optional<PhongBan> optionalPhongBan = phongBanRepository.findById(phongBan.getMaPhong());
            PhongBan savedPhongBan = optionalPhongBan.orElseGet(() -> phongBanRepository.save(phongBan));
            nhanVien.setPhongBan(savedPhongBan);
        }
    }
}
