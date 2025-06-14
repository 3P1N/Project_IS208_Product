CREATE TABLE KhoHang (
    ID_KhoHang       NUMBER PRIMARY KEY,
    TenKhoHang       VARCHAR2(100) NOT NULL,
    SoLuongToiDa     NUMBER,
    SoLuongTonKho    NUMBER DEFAULT 0
);

CREATE TABLE LoaiSanPham (
    ID_LoaiSanPham   NUMBER PRIMARY KEY,
    TenLoaiSanPham   VARCHAR2(100) NOT NULL
);

CREATE TABLE SanPham (
    ID_SanPham       NUMBER PRIMARY KEY,
    TenSanPham       VARCHAR2(100) NOT NULL,
    DonViTinh        VARCHAR2(20),
    ID_LoaiSanPham   NUMBER,
    FOREIGN KEY (ID_LoaiSanPham) REFERENCES LoaiSanPham(ID_LoaiSanPham)
);

CREATE TABLE ChiTietTonKho (
    ID_KhoHang       NUMBER,
    ID_SanPham       NUMBER,
    SoLuong          NUMBER DEFAULT 0,
    PRIMARY KEY (ID_KhoHang, ID_SanPham),
    FOREIGN KEY (ID_KhoHang) REFERENCES KhoHang(ID_KhoHang),
    FOREIGN KEY (ID_SanPham) REFERENCES SanPham(ID_SanPham)
);


CREATE TABLE NguoiDung (
    ID_NguoiDung     NUMBER PRIMARY KEY,
    TenDangNhap      VARCHAR2(50) UNIQUE NOT NULL,
    MatKhau          VARCHAR2(100) NOT NULL,
    HoTen            VARCHAR2(100),
    TrangThaiXoa     NUMBER(1) DEFAULT 0 CHECK (TrangThaiXoa IN (0,1)),
    VaiTro           VARCHAR2(50), -- Hoặc dùng bảng Role nếu muốn tách
    ID_KhoHang       NUMBER,
    FOREIGN KEY (ID_KhoHang) REFERENCES KhoHang(ID_KhoHang)
);

CREATE TABLE PhieuNhapXuat (
    ID_PhieuNhapXuat NUMBER PRIMARY KEY,
    ID_NguoiDung     NUMBER,
    NgayTao          DATE DEFAULT SYSDATE,
    TrangThai        VARCHAR2(20)DEFAULT 'Chờ duyệt', -- ví dụ: 'Chờ duyệt', 'Đã duyệt'
    LoaiPhieu        VARCHAR2(10) CHECK (LoaiPhieu IN ('Nhap', 'Xuat')),
    FOREIGN KEY (ID_NguoiDung) REFERENCES NguoiDung(ID_NguoiDung)
);

CREATE TABLE ChiTietNhapXuat (
    ID_PhieuNhapXuat NUMBER,
    ID_SanPham       NUMBER,
    SoLuong          NUMBER,
    PRIMARY KEY (ID_PhieuNhapXuat, ID_SanPham),
    FOREIGN KEY (ID_PhieuNhapXuat) REFERENCES PhieuNhapXuat(ID_PhieuNhapXuat),
    FOREIGN KEY (ID_SanPham) REFERENCES SanPham(ID_SanPham)
);

CREATE TABLE PhieuKiemKe (
    ID_PhieuKiemKe   NUMBER PRIMARY KEY,
    ID_NguoiDung     NUMBER,
    NgayTao          DATE DEFAULT SYSDATE,
    FOREIGN KEY (ID_NguoiDung) REFERENCES NguoiDung(ID_NguoiDung)
);

CREATE TABLE ChiTietKiemKe (
    ID_PhieuKiemKe   NUMBER,
    ID_SanPham       NUMBER,
    SoLuongThucTe    NUMBER,
    SoLuongHeThong   NUMBER,
    ChenhLech        NUMBER,
    PRIMARY KEY (ID_PhieuKiemKe, ID_SanPham),
    FOREIGN KEY (ID_PhieuKiemKe) REFERENCES PhieuKiemKe(ID_PhieuKiemKe),
    FOREIGN KEY (ID_SanPham) REFERENCES SanPham(ID_SanPham)
);

CREATE TABLE BaoCao (
    ID_BaoCao        NUMBER PRIMARY KEY,
    ID_NguoiDung     NUMBER,
    LoaiBaoCao       VARCHAR2(20), -- ví dụ: 'Tuan', 'Thang', 'Nam'
    NgayTao          DATE DEFAULT SYSDATE,
    FOREIGN KEY (ID_NguoiDung) REFERENCES NguoiDung(ID_NguoiDung)
);

CREATE TABLE ChiTietBaoCao (
    ID_BaoCao        NUMBER,
    ID_SanPham       NUMBER,
    SoLuong       NUMBER,
    PRIMARY KEY (ID_BaoCao, ID_SanPham),
    FOREIGN KEY (ID_BaoCao) REFERENCES BaoCao(ID_BaoCao),
    FOREIGN KEY (ID_SanPham) REFERENCES SanPham(ID_SanPham)
);


-- Sequence cho mỗi bảng
CREATE SEQUENCE Seq_NguoiDung START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE Seq_KhoHang START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE Seq_LoaiSanPham START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE Seq_SanPham START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE Seq_PhieuNhapXuat START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE Seq_PhieuKiemKe START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE Seq_BaoCao START WITH 1 INCREMENT BY 1;


--Trigger tự động cập nhật số lượng tồn kho khi phê duyệt phiếu nhập xuất
CREATE OR REPLACE TRIGGER trg_CapNhatTonKho_AfterDuyet
AFTER UPDATE OF TrangThai ON PhieuNhapXuat
FOR EACH ROW
WHEN (NEW.TrangThai = 'Đã duyệt' AND OLD.TrangThai != 'Đã duyệt')
DECLARE
    v_IDKhoHang KhoHang.ID_KhoHang%TYPE;
    v_LoaiPhieu PHIEUNHAPXUAT.LOAIPHIEU%TYPE;
BEGIN
    -- Lấy ID kho từ người dùng tạo phiếu
    SELECT ID_KhoHang INTO v_IDKhoHang
    FROM NguoiDung
    WHERE ID_NguoiDung = :NEW.ID_NguoiDung;

    v_LoaiPhieu := :NEW.LoaiPhieu;

    -- Duyệt qua các dòng chi tiết phiếu
    FOR rec IN (
        SELECT ID_SanPham, SoLuong
        FROM CHITIETNHAPXUAT c
        WHERE ID_PhieuNhapXuat = :NEW.ID_PhieuNhapXuat
    ) LOOP
        BEGIN
            -- Cập nhật nếu đã có
            UPDATE ChiTietTonKho
            SET SoLuong = SoLuong +
                CASE 
                    WHEN v_LoaiPhieu = 'Nhap' THEN rec.SoLuong
                    ELSE -rec.SoLuong
                END
            WHERE ID_KhoHang = v_IDKhoHang AND ID_SanPham = rec.ID_SanPham;

            -- Nếu không có thì thêm mới
            IF SQL%ROWCOUNT = 0 THEN
                INSERT INTO ChiTietTonKho (ID_KhoHang, ID_SanPham, SoLuong)
                VALUES (
                    v_IDKhoHang,
                    rec.ID_SanPham,
                    CASE WHEN v_LoaiPhieu = 'Nhap' THEN rec.SoLuong ELSE -rec.SoLuong END
                );
            END IF;
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Lỗi khi xử lý sản phẩm ID: ' || rec.ID_SanPham);
        END;
    END LOOP;
END;
/

--Trigger tự động cập nhật tồn kho khi lập phiếu kiểm kê
CREATE OR REPLACE TRIGGER trg_CapNhatTonKho_SauChiTietKiemKe
AFTER INSERT ON ChiTietKiemKe
FOR EACH ROW
DECLARE
    v_IDKho NUMBER;
BEGIN
    -- Tìm kho của người dùng lập phiếu kiểm kê
    SELECT ND.ID_KhoHang INTO v_IDKho
    FROM PhieuKiemKe PKK
    JOIN NguoiDung ND ON PKK.ID_NguoiDung = ND.ID_NguoiDung
    WHERE PKK.ID_PhieuKiemKe = :NEW.ID_PhieuKiemKe;

    -- Cập nhật tồn kho nếu đã có dòng
    UPDATE ChiTietTonKho
    SET SoLuong = :NEW.SoLuongThucTe
    WHERE ID_KhoHang = v_IDKho AND ID_SanPham = :NEW.ID_SanPham;

    -- Nếu không có thì thêm mới
    IF SQL%ROWCOUNT = 0 THEN
        INSERT INTO ChiTietTonKho (ID_KhoHang, ID_SanPham, SoLuong)
        VALUES (v_IDKho, :NEW.ID_SanPham, :NEW.SoLuongThucTe);
    END IF;
END;
/


--insert
-- Thêm dữ liệu mẫu cho KhoHang
INSERT INTO KhoHang (ID_KhoHang, TenKhoHang, SoLuongToiDa, SoLuongTonKho)
VALUES (SEQ_KHOHANG.NEXTVAL, 'Kho Hà Nội', 1000, 500);

INSERT INTO KhoHang (ID_KhoHang, TenKhoHang, SoLuongToiDa, SoLuongTonKho)
VALUES (SEQ_KHOHANG.NEXTVAL, 'Kho TP.HCM', 1500, 700);

INSERT INTO KhoHang (ID_KhoHang, TenKhoHang, SoLuongToiDa, SoLuongTonKho)
VALUES (SEQ_KHOHANG.NEXTVAL, 'Kho Đà Nẵng', 1200, 300);


-- Thêm dữ liệu mẫu cho NguoiDung
INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'admin01', '123', 'Nguyễn Văn A', 'Admin', 1);

INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'quanly01', '123', 'Trần Thị B', 'Quản lý', 1);

INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'nvnhap01', '123', 'Lê Văn C', 'Nhân viên nhập', 2);

INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'nvxuat01', '123', 'Phạm Thị D', 'nhân viên xuất', 2);

INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'admin02', '123', 'Võ Văn E', 'Admin', 1);

INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'nvnhap02', '123', 'Đỗ Thị F', 'Nhân viên nhập', 3);

INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, VaiTro, ID_KhoHang)
VALUES (SEQ_NGUOIDUNG.NEXTVAL, 'nvxuat02', '123', 'Huỳnh Văn G', 'Nhân viên xuất', 3);


-- Thêm dữ liệu loại sản phẩm
INSERT INTO LoaiSanPham (ID_LoaiSanPham, TenLoaiSanPham)
VALUES (SEQ_LOAISANPHAM.NEXTVAL, 'Đồ gia dụng');

INSERT INTO LoaiSanPham (ID_LoaiSanPham, TenLoaiSanPham)
VALUES (SEQ_LOAISANPHAM.NEXTVAL, 'Đồ làm bếp');

INSERT INTO LoaiSanPham (ID_LoaiSanPham, TenLoaiSanPham)
VALUES (SEQ_LOAISANPHAM.NEXTVAL, 'Đồ trang trí');

INSERT INTO LoaiSanPham (ID_LoaiSanPham, TenLoaiSanPham)
VALUES (SEQ_LOAISANPHAM.NEXTVAL, 'Vật dụng phòng tắm');

-- Đồ gia dụng
INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Máy lọc không khí Sharp', 'Chiếc', 1);

INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Quạt đứng Panasonic', 'Chiếc', 1);

-- Đồ làm bếp
INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Nồi chiên không dầu Lock&Lock', 'Chiếc', 2);

INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Bộ dao Inox Nhật Bản', 'Bộ', 2);

-- Đồ trang trí
INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Đèn ngủ gốm sứ cao cấp', 'Chiếc', 3);

INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Tranh treo tường canvas', 'Bức', 3);

-- Vật dụng phòng tắm
INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Kệ nhà tắm inox 3 tầng', 'Chiếc', 4);

INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham)
VALUES (SEQ_SANPHAM.NEXTVAL, 'Bộ phụ kiện nhà tắm sứ cao cấp', 'Bộ', 4);

COMMIT;