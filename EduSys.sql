CREATE DATABASE EduSys;
GO
USE EduSys;
GO
CREATE TABLE NhanVien
(
    MaNV NVARCHAR(20) PRIMARY KEY,
    MatKhau NVARCHAR(50) NOT NULL,
    HoTen NVARCHAR(50) NOT NULL,
    VaiTro BIT
        DEFAULT 0
);
CREATE TABLE ChuyenDe
(
    MaCD NCHAR(5) PRIMARY KEY,
    TenCD NVARCHAR(50) NOT NULL,
    HocPhi FLOAT NOT NULL,
    ThoiLuong INT NOT NULL,
    Hinh NVARCHAR(50) NOT NULL,
    MoTa NVARCHAR(255) NOT NULL
);
CREATE TABLE NguoiHoc
(
    MaNH NCHAR(7) PRIMARY KEY,
    TenNH NVARCHAR(50) NOT NULL,
    GioiTinh BIT NOT NULL,
    NgaySinh DATE NOT NULL,
    DienThoai NVARCHAR(24) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    GhiChu NVARCHAR(255) NULL,
    MaNV NVARCHAR(20) NOT NULL,
    NgayDK DATE
        DEFAULT GETDATE(),
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
);
CREATE TABLE KhoaHoc
(
    MaKH INT PRIMARY KEY IDENTITY(1, 1),
    MaCD NCHAR(5) NOT NULL,
    HocPhi FLOAT NOT NULL,
    ThoiLuong INT NOT NULL,
    NgayKG DATE NOT NULL,
    GhiChu NVARCHAR(255) NULL,
    MaNV NVARCHAR(20) NOT NULL,
    NgayTao DATE
        DEFAULT GETDATE(),
    FOREIGN KEY (MaCD) REFERENCES dbo.ChuyenDe (MaCD) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
);
CREATE TABLE HocVien
(
    MaHV INT PRIMARY KEY IDENTITY(1, 1),
    MaKH INT NOT NULL,
    MaNH NCHAR(7) NOT NULL,
    Diem FLOAT
        DEFAULT 0,
    FOREIGN KEY (MaKH) REFERENCES KhoaHoc (MaKH) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (MaNH) REFERENCES dbo.NguoiHoc (MaNH) ON DELETE NO ACTION ON UPDATE CASCADE
);


INSERT INTO ChuyenDe
VALUES
('CD001', N'Nhập môn lập trình', 8000000, 20, 'fileHinhAnh', N'Lập trình C'),
('CD002', N'Lập trình java1', 12000000, 20, 'fileHinhAnh', N'Lập trình cơ bản với Java'),
('CD003', N'Lập trình java2', 9000000, 20, 'fileHinhAnh', N'Lập trình java với AWT'),
('CD004', N'Lập trình java3', 10000000, 30, 'fileHinhAnh', N'Lập trình java với Swing'),
('CD005', N'Lập trình web cơ bản', 7000000, 14, 'fileHinhAnh', N'Xây dựng web với HTML/CSS');

INSERT INTO NhanVien
VALUES
('NV001', 'thai123', N'Đoàn Phú Thái', 1),
('NV002', 'quang123', N'Nguyễn Phú Quang', 1),
('NV003', 'luc123', N'Trịnh Tiến Lực', 1),
('NV004', 'nam123', N'Nguyễn Thành Nam', 0),
('NV005', 'thuy123', N'Nguyễn Thị Thúy', 0),
('NV006', 'anh123', N'Đào Ngọc Anh', 0),
('NV007', 'trung123', N'Nguyễn Thành Trung', 1),
('NV008', 'manh123', N'Nguyễn Đức Mạnh', 1),
('NV009', 'tung123', N'Mai Đình Tùng', 0),
('NV010', 'thuy123', N'Trịnh Thị Thu Thủy', 0);
INSERT INTO KhoaHoc
(
    MaCD,
    HocPhi,
    ThoiLuong,
    NgayKG,
    GhiChu,
    MaNV,
    NgayTao
)
VALUES
('CD001', 8000000, 20, '20210927', 'Ca 1', 'NV001', GETDATE()),
('CD001', 8000000, 20, '20210927', 'Ca 2', 'NV001', GETDATE()),
('CD001', 8000000, 20, '20210927', 'Ca 3', 'NV001', GETDATE()),
('CD001', 8000000, 20, '20210927', 'Ca 4', 'NV001', GETDATE()),
('CD001', 8000000, 20, '20210927', 'Ca 5', 'NV001', GETDATE()),
('CD002', 12000000, 20, '20210927', 'Ca 1', 'NV002', GETDATE()),
('CD002', 12000000, 20, '20210927', 'Ca 2', 'NV002', GETDATE()),
('CD002', 12000000, 20, '20210927', 'Ca 3', 'NV002', GETDATE()),
('CD002', 12000000, 20, '20210927', 'Ca 4', 'NV002', GETDATE()),
('CD002', 12000000, 20, '20210927', 'Ca 5', 'NV002', GETDATE()),
('CD003', 9000000, 20, '20210927', 'Ca 1', 'NV003', GETDATE()),
('CD003', 9000000, 20, '20210927', 'Ca 2', 'NV003', GETDATE()),
('CD003', 9000000, 20, '20210927', 'Ca 3', 'NV003', GETDATE()),
('CD003', 9000000, 20, '20210927', 'Ca 4', 'NV003', GETDATE()),
('CD003', 9000000, 20, '20210927', 'Ca 5', 'NV003', GETDATE()),
('CD004', 10000000, 30, '20210927', 'Ca 1', 'NV004', GETDATE()),
('CD004', 10000000, 30, '20210927', 'Ca 2', 'NV004', GETDATE()),
('CD004', 10000000, 30, '20210927', 'Ca 3', 'NV004', GETDATE()),
('CD004', 10000000, 30, '20210927', 'Ca 4', 'NV004', GETDATE()),
('CD004', 10000000, 30, '20210927', 'Ca 5', 'NV004', GETDATE()),
('CD005', 7000000, 14, '20210927', 'Ca 1', 'NV005', GETDATE()),
('CD005', 7000000, 14, '20210927', 'Ca 2', 'NV005', GETDATE()),
('CD005', 7000000, 14, '20210927', 'Ca 3', 'NV005', GETDATE()),
('CD005', 7000000, 14, '20210927', 'Ca 4', 'NV005', GETDATE()),
('CD005', 7000000, 14, '20210927', 'Ca 5', 'NV005', GETDATE());
INSERT INTO NguoiHoc
VALUES
('NH001', N'Trịnh Viết Hiếu', 0, '20020405', '012312412', 'hieutv@gmail.com', '', 'NV001', GETDATE()),
('NH002', N'Trịnh Thị Thùy Dung', 1, '20020704', '035143521', 'dungt3@gmail.com', '', 'NV001', GETDATE()),
('NH003', N'Nguyễn Duy Tân', 0, '20020405', '03151343', 'tannd@gmail.com', '', 'NV001', GETDATE()),
('NH004', N'Trinh Thanh Bình', 0, '20021214', '01241321', 'binhtt@gmail.com', '', 'NV001', GETDATE()),
('NH005', N'Trịnh Ngọc Mai', 1, '20021111', '024234332', 'maitn@gmail.com', '', 'NV001', GETDATE()),
('NH006', N'Hoàng Thùy Linh', 1, '20020405', '012312412', 'linhht@gmail.com', '', 'NV002', GETDATE()),
('NH007', N'Trịnh Thị Thùy Trang', 1, '20010404', '07421712', 'trangt3@gmail.com', '', 'NV002', GETDATE()),
('NH008', N'Mai Đình Tùng', 0, '20000706', '07347322', 'tungmd@gmail.com', '', 'NV002', GETDATE()),
('NH009', N'Đào Thùy Linh', 1, '20000307', '012312412', 'linhdt@gmail.com', '', 'NV002', GETDATE()),
('NH010', N'Đàm Thị Thúy', 1, '20020405', '0143223412', 'thuydt@gmail.com', '', 'NV002', GETDATE()),
('NH011', N'Bùi Đình Hạ', 0, '20010708', '018712412', 'habd@gmail.com', '', 'NV003', GETDATE()),
('NH012', N'Nguyễn Huyền Trang', 1, '20020405', '012312412', 'trangnh@gmail.com', '', 'NV003', GETDATE()),
('NH013', N'Trịnh Xuân Minh', 0, '20000409', '05827424', 'minhtx@gmail.com', '', 'NV003', GETDATE()),
('NH014', N'Mai Viết Thắng', 0, '20020411', '012312412', 'thangmv@gmail.com', '', 'NV003', GETDATE()),
('NH015', N'Bùi Xuân Đãi', 0, '20020405', '012312412', 'hieutv@gmail.com', '', 'NV003', GETDATE()),
('NH016', N'Vũ Xuân Dương', 0, '20010405', '02417243', 'duongvx@gmail.com', '', 'NV004', GETDATE()),
('NH017', N'Nguyễn Đình Hùng', 0, '20000909', '0248242', 'hungnd@gmail.com', '', 'NV004', GETDATE()),
('NH018', N'Đào Ngọc Bích', 1, '20020405', '012312412', 'bichdn@gmail.com', '', 'NV004', GETDATE()),
('NH019', N'Trịnh Thị Thu', 1, '20000117', '03937412', 'Thutt@gmail.com', '', 'NV004', GETDATE()),
('NH020', N'Nguyễn Phương Mai', 1, '20020405', '096347223', 'maipn@gmail.com', '', 'NV004', GETDATE()),
('NH021', N'Đề Quốc Tuấn', 0, '20000101', '012312412', 'tuandq@gmail.com', '', 'NV005', GETDATE()),
('NH022', N'Trần Hưng Nam', 0, '19990903', '06428323', 'namth@gmail.com', '', 'NV005', GETDATE()),
('NH023', N'Đoàn Vân Hưng', 0, '20020405', '07482643', 'hungvd@gmail.com', '', 'NV005', GETDATE()),
('NH024', N'Đỗ Thanh Tùng', 0, '20000321', '082933353', 'tungtd@gmail.com', '', 'NV005', GETDATE()),
('NH025', N'Bùi Quốc Huy', 0, '20011124', '08936256', 'huybq@gmail.com', '', 'NV005', GETDATE());
INSERT INTO HocVien
(
    MaKH,
    MaNH,
    Diem
)
VALUES
(1, 'NH001', 10),
(1, 'NH002', 7),
(1, 'NH003', 8),
(1, 'NH004', 10),
(1, 'NH005', 6),
(1, 'NH006', 5),
(1, 'NH007', 7),
(1, 'NH008', 9),
(1, 'NH009', 7),
(1, 'NH010', 3),
(1, 'NH011', 2),
(2, 'NH001', 10),
(2, 'NH002', 7),
(2, 'NH003', 8),
(2, 'NH004', 10),
(2, 'NH005', 6),
(2, 'NH006', 5),
(2, 'NH007', 7),
(2, 'NH008', 9),
(2, 'NH009', 7),
(2, 'NH010', 3),
(2, 'NH011', 2),
(3, 'NH012', 10),
(3, 'NH013', 7),
(3, 'NH014', 8),
(3, 'NH015', 10),
(3, 'NH016', 6),
(3, 'NH017', 5),
(3, 'NH018', 7),
(3, 'NH019', 9),
(3, 'NH020', 7),
(3, 'NH021', 3),
(3, 'NH022', 2),
(4, 'NH001', 10),
(4, 'NH002', 7),
(4, 'NH003', 8),
(4, 'NH004', 10),
(4, 'NH005', 6),
(4, 'NH006', 5),
(4, 'NH007', 7),
(4, 'NH008', 9),
(5, 'NH009', 7),
(5, 'NH010', 3),
(5, 'NH011', 2),
(5, 'NH001', 10),
(6, 'NH002', 7),
(6, 'NH003', 8),
(6, 'NH004', 10),
(6, 'NH005', 6),
(6, 'NH006', 5),
(6, 'NH007', 7),
(6, 'NH008', 9),
(6, 'NH009', 7),
(6, 'NH010', 3),
(6, 'NH011', 2),
(7, 'NH012', 10),
(7, 'NH013', 7),
(7, 'NH014', 8),
(7, 'NH015', 10),
(7, 'NH016', 6),
(7, 'NH017', 5),
(7, 'NH018', 7),
(7, 'NH019', 9),
(7, 'NH020', 7),
(7, 'NH021', 3),
(7, 'NH022', 2);
-------------------------------------------------- Proc Doanh Thu---------------------------------
IF OBJECT_ID('sp_BangDiem') IS NOT NULL
    DROP PROC dbo.sp_BangDiem;
GO
CREATE PROC sp_BangDiem
(@MaKH INT)
AS
BEGIN
    SELECT NguoiHoc.MaNH,
           NguoiHoc.TenNH,
           HocVien.Diem
    FROM NguoiHoc
        JOIN HocVien
            ON NguoiHoc.MaNH = HocVien.MaNH
    WHERE HocVien.MaKH = @MaKH
    ORDER BY HocVien.Diem Desc;
END;
GO

--------------------------------------------------------------
IF OBJECT_ID('sp_LuongNguoiHoc') IS NOT NULL
    DROP PROC dbo.sp_LuongNguoiHoc;
GO
CREATE PROC sp_LuongNguoiHoc
AS
BEGIN
    SELECT YEAR(NgayDK) Nam,
           COUNT(*) SoLuong,
           MIN(NgayDK) DauTien,
           MAX(NgayDK) CuoiCung
    FROM NguoiHoc
    GROUP BY YEAR(NgayDK);
END;
GO
-----------------------------------------------------------------
IF OBJECT_ID('sp_DiemChuyenDe') IS NOT NULL
    DROP PROC sp_DiemChuyenDe;
GO
CREATE PROC sp_DiemChuyenDe
AS
BEGIN
    SELECT TenCD chuyenDe,
           COUNT(MaHV) SoHV,
           MIN(Diem) ThapNhat,
           MAX(Diem) CaoNhat,
           AVG(Diem) TrungBinh
    FROM KhoaHoc kh
        JOIN HocVien hv
            ON kh.MaKH = hv.MaKH
        JOIN ChuyenDe cd
            ON kh.MaCD = cd.MaCD
    GROUP BY TenCD;
END;
GO
---------------------------------------------------------------
IF OBJECT_ID('sp_DoanhThu') IS NOT NULL
    DROP PROC dbo.sp_DoanhThu;
GO
CREATE PROC sp_DoanhThu
(@Year INT)
AS
BEGIN
    SELECT cd.TenCD ChuyenDe,
           COUNT(DISTINCT kh.MaKH) SoKH,
           COUNT(hv.MaHV) SoHV,
           CONVERT(INT, SUM(kh.HocPhi), 0) DoanhThu,
           CONVERT(INT, MIN(kh.HocPhi), 0) ThapNhat,
           CONVERT(INT, MAX(kh.HocPhi), 0) CaoNhat,
           CONVERT(INT, AVG(kh.HocPhi), 0) TrungBinh
    FROM KhoaHoc kh
        JOIN HocVien hv
            ON kh.MaKH = hv.MaKH
        JOIN ChuyenDe cd
            ON kh.MaCD = cd.MaCD
    WHERE YEAR(NgayKG) = @Year
    GROUP BY cd.TenCD
END;
EXEC dbo.sp_DoanhThu @Year = 2021; -- int

GO
-------------------------------Proc Select----------------------------
IF OBJECT_ID('sp_ListNhanVien') IS NOT NULL
    DROP PROC dbo.sp_ListNhanVien;
GO
CREATE PROC sp_ListNhanVien @PageIndex INT
AS
BEGIN
    SELECT *
    FROM dbo.NhanVien
    ORDER BY MaNV OFFSET @PageIndex * 10 ROWS FETCH NEXT 10 ROWS ONLY;
END;
GO
------------------------------------------------------------------------
IF OBJECT_ID('sp_ListChuyenDe') IS NOT NULL
    DROP PROC dbo.sp_ListChuyenDe;
GO
CREATE PROC sp_ListChuyenDe @PageIndex INT
AS
BEGIN
    SELECT *
    FROM dbo.ChuyenDe
    ORDER BY MaCD OFFSET @PageIndex * 10 ROWS FETCH NEXT 10 ROWS ONLY;
END;
GO
--------------------------------------------------------------------------
IF OBJECT_ID('sp_ListNguoiHoc') IS NOT NULL
    DROP PROC dbo.sp_ListNguoiHoc;
GO
CREATE PROC sp_ListNguoiHoc @PageIndex INT
AS
BEGIN
    SELECT *
    FROM dbo.NguoiHoc
    ORDER BY MaNH OFFSET @PageIndex * 10 ROWS FETCH NEXT 10 ROWS ONLY;
END;
GO
-----------------------------------------------------------------------------
IF OBJECT_ID('sp_ListKhoaHoc') IS NOT NULL
    DROP PROC dbo.sp_ListKhoaHoc;
GO
CREATE PROC sp_ListKhoaHoc
    @PageIndex INT,
    @MaCD NCHAR(5)
AS
BEGIN
    SELECT *
    FROM KhoaHoc
    WHERE MaCD = @MaCD
    ORDER BY MaKH OFFSET @PageIndex * 10 ROWS FETCH NEXT 10 ROWS ONLY;
END;
GO
-------------------------------------------------------------------------------
IF OBJECT_ID('sp_ListHocVien') IS NOT NULL
    DROP PROC dbo.sp_ListHocVien;
GO
CREATE PROC sp_ListHocVien
    @PageIndex INT,
    @MaKH INT
AS
BEGIN
    SELECT *
    FROM dbo.HocVien
    WHERE MaKH = @MaKH
    ORDER BY MaHV OFFSET @PageIndex * 10 ROWS FETCH NEXT 10 ROWS ONLY;
END;
GO
-------------------------------------------------------------------
IF OBJECT_ID('sp_ListHocVien2') IS NOT NULL
    DROP PROC dbo.sp_ListHocVien2;
GO
CREATE PROC sp_ListHocVien2
    @PageIndex INT,
    @MaKH INT
AS
BEGIN
    SELECT *
    FROM dbo.NguoiHoc
    WHERE MaNH NOT IN
          (
              SELECT MaNH FROM dbo.HocVien WHERE MaKH = @MaKH
          )
    ORDER BY MaNH OFFSET @PageIndex * 10 ROWS FETCH NEXT 10 ROWS ONLY;
END;
GO
--gọi
GO
--------------Doanh Thu------------------
EXEC dbo.sp_BangDiem @MaKH = 1; -- int
EXEC dbo.sp_LuongNguoiHoc;
EXEC dbo.sp_DiemChuyenDe;
EXEC dbo.sp_DoanhThu @Year = 2021; -- int
---------------------Select Entity------------------
EXEC dbo.sp_ListNhanVien @PageIndex = 0; -- int
EXEC dbo.sp_ListChuyenDe @PageIndex = 0; -- int
EXEC dbo.sp_ListNguoiHoc @PageIndex = 0; -- int
EXEC dbo.sp_ListKhoaHoc @PageIndex = 0,   -- int
                        @MaCD = N'CD001'; -- nchar










