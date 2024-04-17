package factories;

import com.github.javafaker.Faker;
import models.User;

public class UserFactory {

    private static final String DEFAULT_PASSWORD = "thesecret";
    private static final Faker faker = new Faker();


    public static User createDefault() {
        var user = new User();
        user.setEmail(faker.internet().safeEmailAddress());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setTelephone(faker.phoneNumber().phoneNumber());
        user.setPassword(DEFAULT_PASSWORD);
        user.setPasswordConfirm(DEFAULT_PASSWORD);
        user.setAgreePrivacyPolicy(true);
        user.setShouldSubscribe(false);
        return user;
    }
}
