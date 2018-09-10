package org.quickstart.components.validate;

import org.quickstart.bean.UserInfoAndRole;
import org.quickstart.exceptions.InvalidValidationException;

public interface ValidateParams {

	void validateForCreate(UserInfoAndRole userInfoAndRole) throws InvalidValidationException;

	void validateForUpdate(UserInfoAndRole userInfoAndRole) throws InvalidValidationException;
}
