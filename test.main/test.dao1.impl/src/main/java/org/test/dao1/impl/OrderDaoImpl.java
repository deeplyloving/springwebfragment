package org.test.dao1.impl;

import org.springframework.stereotype.Repository;
import org.test.base.dao.CURDDaoImpl;
import org.test.dao1.OrderDao;
import org.test.model.Order;

/**
 * Hello world!
 *
 */
@Repository
public class OrderDaoImpl extends CURDDaoImpl<Order> implements OrderDao
{
}
