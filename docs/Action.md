Text Input: using Locator.fill()
- Text input
page.getByRole(AriaRole.TEXTBOX).fill("Peter");

- Date input
page.getByLabel("Birth date").fill("2020-02-02");

- Time input
page.getByLabel("Appointment time").fill("13:15");

- Local datetime input
page.getByLabel("Local time").fill("2020-03-02T05:15");

".check() dùng cho cả checkbox và radio button. Khác nhau là checkbox chọn được nhiều, radio chỉ chọn 1. isChecked() dùng để verify trạng thái đang được chọn hay chưa." 
- Check the checkbox
page.getByLabel("I agree to the terms above").check();

- Assert the checked state
assertThat(page.getByLabel("Subscribe to newsletter")).isChecked();

- Select the radio button
page.getByLabel("XL").check();

- setForce(true) bỏ qua actionability checks của Playwright — click thẳng vào element dù đang bị che hay disabled. Tuy nhiên hạn chế dùng vì có thể làm test pass nhưng không reflect đúng behavior của người dùng thật."
- dispatchEvent() kích hoạt JavaScript event trực tiếp 

- pressSequentially(): 
                        + Tình huống 1 — Ô search có autocomplete/suggestion:
                        // Trang có dropdown gợi ý khi gõ
                         page.locator("#search").pressSequentially("iph");
                           // → Gõ "i" → dropdown hiện gợi ý
                          // → Gõ "p" → dropdown lọc lại
                         // → Gõ "h" → chọn "iPhone" từ dropdown
                        + Tình huống 2 — Ô input có validation realtime
                        + Tình huống 3 — Có delay để test loading
- press() mô phỏng bấm phím bàn phím thật 