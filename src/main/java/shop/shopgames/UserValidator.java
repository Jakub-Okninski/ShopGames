package shop.shopgames;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import shop.shopgames.entites.User;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;


        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            errors.rejectValue("password", "Negative.user.password");
        }
    }
}
