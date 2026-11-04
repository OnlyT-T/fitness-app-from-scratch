# Function trong Form

## Tổng quát
1. **React**: `useState()` để quản lí dữ liệu người dùng nhập.
2. **TypeScript**: `Props`, `RunningActivityRequest` để kiểm soát dữ liệu.
3. **TanStack Query**: `useMutation()` và `useQueryClient()` để gửi dữ liệu backend và cập nhật dữ liệu sau khi lưu.
4. **JavaScript**: Xử lý sự kiện, kiểm tra dữ liệu và chuyển đổi kiểu dữ liệu.

## Flow
USER<br>
 │<br>
 │ Nhập thông tin chạy bộ<br>
 ▼<br>
RunningActivityForm<br>
 │<br>
 │ useState lưu dữ liệu input<br>
 ▼<br>
handleSubmit()<br>
 │<br>
 ├── Ngăn browser reload<br>
 │<br>
 ├── Chuyển String → Number<br>
 │<br>
 ├── Validate dữ liệu<br>
 │<br>
 ▼<br>
mutation.mutate()<br>
 │<br>
 ▼<br>
mutationFn<br>
 │<br>
 ▼<br>
createRunningActivity()<br>
 │<br>
 │ HTTP POST Request<br>
 ▼<br>
Spring Boot Backend<br>
 │<br>
 ▼<br>
PostgreSQL Database<br>
 │<br>
 │ Lưu thành công<br>
 ▼<br>
onSuccess()<br>
 │<br>
 ├── invalidateQueries()<br>
 │      └── Fetch lại danh sách activity<br>
 │<br>
 └── Reset form

## Flow code
1. `import`
2. `type Props`
3. `export function`
4. `queryClient`
5. `useState`
6. `mutation = useMutation({mutationFn: () => create...(), onSuccess: () => {},})`
7. `function handleSubmit`
8. `return ( ... )`