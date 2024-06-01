package shop.shopgames;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import shop.shopgames.entites.OrderDetails;
import shop.shopgames.entites.User;


public class OrderDetailsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDetails.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDetails orderDetails = (OrderDetails) target;


        if (!orderDetails.getAdressEmail().matches("[A-Za-z\\d]{2,15}@[A-Za-z\\d]{2,6}.[A-Za-z\\d]{2,6}")) {
            errors.rejectValue("adressEmail", "InvalidFormat.orderDetails.adressEmail");
        }


        if (!orderDetails.getZipCode().matches("\\d{2}-\\d{3}")) {
            errors.rejectValue("zipCode", "InvalidFormat.orderDetails.zipCode");
        }

        // Walidacja formatu numeru telefonu
        if (!orderDetails.getNumber().matches("\\d{3} \\d{3} \\d{3}")) {
            errors.rejectValue("number", "InvalidFormat.orderDetails.number");
        }
    }
}