package io.hashimati.services;


import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import io.hashimati.domains.Order;
import io.hashimati.repositories.OrderRepository;


@Singleton
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Inject private OrderRepository orderRepository;

    public Order save(Order order){
        log.info("Saving  Order : {}", order);
        //TODO insert your logic here!
        //saving Object
        orderRepository.save(order);
        return order;
    }

    public Order findById(long id){
        log.info("Finding Order By Id: {}", id);
        return orderRepository.findById(id).orElse(null);
    }


    public boolean deleteById(long id){
        log.info("Deleting Order by Id: {}", id);
        try{
            orderRepository.deleteById(id);
            log.info("Deleted Order by Id: {}", id);
            return true;
        }
        catch(Exception ex)
        {
            log.info("Failed to delete Order by Id: {}", id);
            ex.printStackTrace();
            return false;
        }
    }

    public Iterable<Order> findAll() {
        log.info("Find All");
      return  orderRepository.findAll();
    }

    public boolean existsById(Long id)
    {
        log.info("Check if id exists: {}", id);
        return  orderRepository.existsById(id);

    }
    public Order update(Order order)
    {
        log.info("update {}", order);
        return orderRepository.update(order);

    }

}

