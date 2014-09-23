package org.test.service;

import org.test.base.service.ICURDService;
import org.test.model.User;

/**
 * Hello world!
 *
 */
public interface UserService extends ICURDService<User>
{
   public void hello();
}
