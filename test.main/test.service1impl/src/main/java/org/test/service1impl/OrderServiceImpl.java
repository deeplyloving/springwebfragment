package org.test.service1impl;

import org.springframework.stereotype.Service;
import org.test.base.service.CURDServiceImpl;
import org.test.model.Order;
import org.test.service1.OrderService;

/**
 * Hello world!
 *
 */
@Service
public class OrderServiceImpl extends CURDServiceImpl<Order> implements OrderService
{

}
