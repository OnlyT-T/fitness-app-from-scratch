# BACKEND

## `@Transactional`
- HOẶC tất cả các thao tác thành công **(COMMIT)**
- HOẶC nếu xảy ra lỗi phù hợp thì toàn bộ thay đổi sẽ bị hủy **(ROLLBACK)**
- Khi gán với METHOD
    + Nếu method đó bị lỗi mọi hoạt động ở trước đấy sẽ bị ROLLBACK