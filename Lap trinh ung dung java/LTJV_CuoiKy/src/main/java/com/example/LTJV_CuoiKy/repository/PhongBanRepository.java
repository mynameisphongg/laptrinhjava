package com.example.LTJV_CuoiKy.repository;

import com.example.LTJV_CuoiKy.model.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, String> {
    // Tùy chọn: Nếu cần thêm các phương thức tùy chỉnh cho PhongBanRepository, bạn có thể khai báo ở đây.
}
