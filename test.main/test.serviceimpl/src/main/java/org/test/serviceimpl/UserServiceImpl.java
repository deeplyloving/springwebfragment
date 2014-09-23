package org.test.serviceimpl;

import org.springframework.stereotype.Service;
import org.test.base.service.CURDServiceImpl;
import org.test.model.User;
import org.test.service.UserService;

/**
 * Hello world!
 *
 */

@Service
public class UserServiceImpl extends CURDServiceImpl<User> implements UserService
{
	
	public void hello() {
		log.info("执行了 userServiceImpl 的  hello 方法!");
	}

}
