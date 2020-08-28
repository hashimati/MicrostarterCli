package io.hashimati.services

import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import io.hashimati.domains.Order
import io.hashimati.repositories.OrderRepository

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
class OrderService {
     Logger log = LoggerFactory.getLogger(OrderService.class)
    @Inject OrderRepository orderRepository

    Order save(Order order){
        log.info("Saving  Order : {}", order)
         //TODO insert your logic here!
         //saving Object

         return orderRepository.save(order)
     }

    Order findById(long id){
        log.info("Finding Order By Id: {}", id)
        return orderRepository.findById(id).orElse(null)
    }

    boolean deleteById(long id){
        try
        {
            orderRepository.deleteById(id);
            log.info("Deleting Order by Id: {}", id);
            return true;
        }
        catch(Exception ex)
        {
            log.info("Failed to delete Order by Id: {}", id);
            ex.printStackTrace();
            return false;
        }
    }


    Iterable<Order> findAll() {
        log.info("Find All")
      return  orderRepository.findAll();
    }

    Order update(Order order)
    {
        log.info("update {}", order)
        return orderRepository.update(order)

    }
}

