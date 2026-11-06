# API Knowledge Base
## 1. `useMutation` vs `useQuery`
- `useQuery` thường dùng để đọc dữ liệu từ server.
- `useMutation` dùng cho các thao tác thay đổi dữ liệu **(POST, PUT, PATCH và DELETE)**

## 2. `invalidateQueries()`
- Đánh dấu dữ liệu Running Activities hiện tại là **stale** và kích hoạt việc fetch lại đối với query đang được sử dụng.<br>
-> Lịch sử running có thể tự động cập nhật sau khi lưu thành công.

## QueryClient
- QueryClient là đối tượng trung tâm của TanStack Query
- Chịu trách nhiệm quản lý các query và mutation, đặc biệt là dữ liệu cache.
> Có thể sử dụng lại dữ liệu đã được cache thay vì lúc nào cũng phải gửi request mới.
### `useQueryClient()`
- Là câu lệnh dùng để lấy QueryClient ở cấp cao hơn (thường là ở function cha, chứa toàn bộ khung website)
> `const queryClient = useQueryClient()`

### `useQuery()`
`const { data } = useQuery({`<br>
&nbsp;&nbsp;&nbsp;`queryKey: ['runningActivities', userId],`<br>
&nbsp;&nbsp;&nbsp;`queryFn: () => getRunningActivities(userId)`<br>
`})`

### `useMutation()`
`const mutation = useMutation({`<br>
&nbsp;&nbsp;&nbsp;`mutationFn: createRunningActivity`<br>
`})`