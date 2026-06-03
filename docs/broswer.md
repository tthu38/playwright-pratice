## 1. Playwright hỗ trợ 3 browser chính

| Browser | Lấy từ | Lưu ý |
|---|---|---|
| **Chromium** | Open source build | Default, dùng phổ biến nhất |
| **Firefox** | Firefox Stable | Không dùng branded Firefox |
| **WebKit** | WebKit main branch | Gần giống Safari, không phải Safari thật |

## Browser Contexts — Isolation

### Khái niệm chính
- Mỗi test chạy trong **BrowserContext riêng biệt** 
  = giống tab ẩn danh mới
- Mỗi context có **local storage, session storage, 
  cookie độc lập** — không ảnh hưởng nhau

### Tại sao cần Test Isolation?
- Test fail không kéo theo test khác fail
- Dễ debug vì lỗi chỉ nằm trong 1 test
- Chạy parallel không cần quan tâm thứ tự

### 2 cách làm sạch giữa các test
| Cách | Vấn đề |
|---|---|
| Cleanup sau mỗi test | Dễ quên, một số thứ không cleanup được (visited links) |
| **Tạo mới từ đầu** ✅ | Luôn sạch, không bị ảnh hưởng test trước |
→ Playwright dùng cách 2 — tạo context mới mỗi test

### Code chuẩn
\```java
Browser browser = chromium.launch();
BrowserContext context = browser.newContext(); // mỗi test 1 context mới
Page page = context.newPage();
\```

### Multiple Contexts trong 1 test
Dùng khi test multi-user (chat, admin vs user):
\```java
BrowserContext userContext  = browser.newContext();
BrowserContext adminContext = browser.newContext();
// 2 context hoàn toàn độc lập trong cùng 1 browser
\```

### ⚠️ Notes quan trọng
- Context **nhanh và nhẹ** để tạo — không tốn nhiều resource
- **KHÔNG share context** giữa các test
- Context còn dùng để **emulate** mobile, locale, permissions
- Cleanup: luôn gọi `context.close()` sau mỗi test (@AfterMethod)