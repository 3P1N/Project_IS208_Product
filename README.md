![image](https://github.com/user-attachments/assets/0384fcab-d8cb-4d83-a8d5-6669ba975bfa)
# Project_IS208_Quản lý dự án Công nghệ thông tin
Đề tài: Xây dựng ứng dụng Desktop Quản lý kho hàng

Link repo: https://github.com/3P1N/Project_IS208_Product

Hướng dẫn cài đặt chương trình phần mềm hệ thống quản lý đơn vị giao vận

1. Hướng dẫn cài đặt
Để có thể thực thi dự án thì cần cài đặt NetBeans, NetBeans IDE, JDK (Java Development Kit) phiên bản 8 trở lên, Oracle Database phiên bản 11g hoặc 19c và để kết nối tới CSDL trên Oracle chúng ta cần Oracle JDBC Driver.
Cài đặt JDK (Java Development Kit):
Tải JDK từ trang chủ Oracle: https://www.oracle.com/java/technologies/downloads/?er=221886
2. Cài đặt và thiết lập biến môi trường.
Cài đặt IDE:
Tải và cài đặt IDE NetBeans từ: [https://netbeans.apache.org/download/] 
3. Mở project:
Tải source từ link: https://drive.google.com/drive/folders/1lsrRv1ow3POSmEr-6DEozR867J5cLk7w?usp=drive_link
Mở ứng dụng netbeans -> File -> Open project -> Chọn project vừa tải -> Open Project
4. Cài đặt các thư viện cần thiết:
Nhấp chuột phải vào Libraries -> Bấm Add JAR/ Folder
-> Chọn các file jar trong thư mục lib -> Bấm Open.
5. Chuẩn bị cơ sở dữ liệu:
Mở SQL Plus -> Tạo user “QuanLyKho” với mật khẩu “Admin123” và gán quyền:

      alter session set "_ORACLE_SCRIPT"=true;
      Create user QuanLyKho IDENTIFIED BY Admin123;
      GRANT DBA to QuanLyKho;

Mở file sql trong thư project: https://github.com/3P1N/Project_IS208_Product/blob/main/SQL.sql
Mở trình duyệt Oracle hoặc SQL Developer, dbForge Studio, …. Và khởi chạy file sql.


