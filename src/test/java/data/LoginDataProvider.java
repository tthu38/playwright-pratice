package data;

import org.testng.annotations.DataProvider;

import utils.ConfigReader;

public class LoginDataProvider {
    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return new Object[][] {
            // email                        password                        verifyType
            {ConfigReader.VALID_EMAIL,      ConfigReader.VALID_PASSWORD,    "success"},
            {ConfigReader.VALID_EMAIL,      "thienthu2004",                 "failed"},
            {"ohtthu0308@gmail",            ConfigReader.VALID_PASSWORD,    "failed"},
            {"",                            "",                             "stillOnPage"},
            {ConfigReader.VALID_EMAIL,      "",                             "stillOnPage"},
            {"",                            ConfigReader.VALID_PASSWORD,    "stillOnPage"},
        };
    }
}
