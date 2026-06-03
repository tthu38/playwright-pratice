
Filter
// Tên sản phẩm cố định → String thường là đủ
.filter(new Locator.FilterOptions().setHasText("iPhone 15"))

// Giá có thể là "Rs. 500" hoặc "$500" → Pattern linh hoạt hơn
.filter(new Locator.FilterOptions()
    .setHasText(Pattern.compile("500")))

// Tên không biết viết hoa thường → CASE_INSENSITIVE
.filter(new Locator.FilterOptions()
    .setHasText(Pattern.compile("iphone", Pattern.CASE_INSENSITIVE)))

// Cách 3 — setHas(Locator): lọc theo locator bên trong
.filter(new Locator.FilterOptions()
    .setHas(page.getByRole(AriaRole.HEADING,
        new Page.GetByRoleOptions().setName("Product 2"))))

- setHas(): lấy những cái CÓ CHỨA
- setHasNot(): lấy những cái KHÔNG CHỨA
- Strictness = "Tao chỉ làm việc khi biết chính xác mày muốn element nào.
              Nếu tìm thấy nhiều hơn 1 → tao báo lỗi ngay!" 
    
"Test Runner là framework quản lý việc chạy test — Java có JUnit và TestNG. Tôi dùng TestNG vì hỗ trợ @DataProvider, grouping, parallel tốt hơn JUnit. Pattern chuẩn là share Browser giữa các test nhưng tạo BrowserContext mới cho mỗi test để đảm bảo isolation. Khi chạy parallel, mỗi thread phải có Playwright instance riêng để tránh conflict."


## Cấu trúc test: Navigate -> Action -> Assertion
Label -> getByLabel
Input -> getByPlaceholder
Div, span, p -> getByText
Button, a, input -> getByRole
Alt, img -> getByAltText

## Filter
Filter by text: nếu có Pattern.compile -> match linh hoạt hơn - tìm chuỗi con, không phân b