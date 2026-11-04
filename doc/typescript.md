# TypeScript Knowledge Base

## 1. CÚ PHÁP TẠO BIẾN
- `var` là **FUNCTION** scope
- `let` là **BLOCK** scope
- `const` là **BLOCK** scope và *giá trị của biến không thể thay đổi*, ngoại trừ:
    + các **element** trong const array
    + **properties** của object

## 2. PROPS
- `Props` là viết tắt của **Properties**
- Đây là cơ chế truyền dữ liệu *từ component CHA xuống component CON*.
- VD: Running Page có trách nhiệm chạy cả trang, truyền dữ liệu userId xuống cho RunningPageForm hiển thị thông qua dòng code:<br>
> `export function RunningActivityForm({ userId }: Props) {}`

## 3. useEffect
Cho phép chạy code sau khi component đã được render
> = `window.addEventListener`

## 4. useRef
Cho phép giữ 1 giá trị qua các lần render mà khi làm thay đổi giá trị đó không làm component render lại

## 5. Strict Mode
Có thêm phần cleanup khi chạy code

## 6. Logical AND - `&&`
> `condition && expression` 
- Nếu `condition` là true, JS sẽ trả về `expression`
- Nếu `condition` là false, JS sẽ trả về `condition`, tức là false
- `condition` có thể là false hoặc 0