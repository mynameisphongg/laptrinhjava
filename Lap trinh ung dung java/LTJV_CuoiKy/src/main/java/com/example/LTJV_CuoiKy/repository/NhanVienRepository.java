package com.example.LTJV_CuoiKy.repository;

import com.example.LTJV_CuoiKy.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, String> {

    Optional<NhanVien> findByMaNV(String maNV);

    boolean existsByMaNV(String maNV);

    void deleteByMaNV(String maNV);
}
