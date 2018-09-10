package org.quickstart.components.validate.impl;

import org.quickstart.bean.UserInfoAndRole;
import org.quickstart.components.util.Util;
import org.quickstart.components.validate.ValidateParams;
import org.quickstart.exceptions.InvalidValidationException;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

public class ValidateParamsImpl implements ValidateParams {
    private final List<String> requiredCreateFields = Arrays.asList("userName", "firstName", "lastName", "password");
    private final List<String> requiredUpdateFields = Arrays.asList("id", "userName", "firstName", "lastName");

    public void validateForCreate(UserInfoAndRole userInfoAndRole) throws InvalidValidationException {
		for (String requiredField: requiredCreateFields) {
			validateRequiredField(requiredField, userInfoAndRole);
		}
    }

	public void validateForUpdate(UserInfoAndRole userInfoAndRole) throws InvalidValidationException {
		for (String requiredField: requiredUpdateFields) {
			validateRequiredField(requiredField, userInfoAndRole);
		}
	}

    private void validateRequiredField(String requiredField, UserInfoAndRole userInfoAndRole) throws InvalidValidationException {
		try {
			String getterName = Util.fieldToGetter(requiredField);
			Method method = userInfoAndRole.getClass().getMethod(getterName);
			Object value = method.invoke(userInfoAndRole);
			if (value == null || Util.isEmpty(String.valueOf(value)))
				throw new InvalidValidationException("Not set required param: \"" + requiredField + "\"");
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
