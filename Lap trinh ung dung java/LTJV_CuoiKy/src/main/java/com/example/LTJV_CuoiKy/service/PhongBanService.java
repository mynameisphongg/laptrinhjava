package com.example.LTJV_CuoiKy.service;

import com.example.LTJV_CuoiKy.model.PhongBan;
import com.example.LTJV_CuoiKy.repository.PhongBanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhongBanService {

    private final PhongBanRepository phongBanRepository;

    public List<PhongBan> getAllPhongBan() {
        return phongBanRepository.findAll();
    }

    public Optional<PhongBan> findById(String maPhong) {
        return phongBanRepository.findById(maPhong);
    }

    @Transactional
    public PhongBan addPhongBan(PhongBan phongBan) {
        return phongBanRepository.save(phongBan);
    }

    @Transactional
    public PhongBan updatePhongBan(String maPhong, PhongBan updatedPhongBan) {
        PhongBan existingPhongBan = phongBanRepository.findById(maPhong)
                .orElseThrow(() -> new IllegalStateException("Phòng ban không tồn tại."));

        existingPhongBan.setTenPhong(updatedPhongBan.getTenPhong());

        return phongBanRepository.save(existingPhongBan);
    }

    @Transactional
    public void deletePhongBanById(String maPhong) {
        if (!phongBanRepository.existsById(maPhong)) {
            throw new IllegalStateException("Phòng ban không tồn tại.");
        }
        phongBanRepository.deleteById(maPhong);
    }
}
