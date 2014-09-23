package org.test.dao.impl;

import org.springframework.stereotype.Repository;
import org.test.base.dao.CURDDaoImpl;
import org.test.dao.UserDao;
import org.test.model.User;

/**
 * Hello world!
 *
 */
@Repository
public class UserDaoImpl extends CURDDaoImpl<User> implements UserDao
{
	
}
