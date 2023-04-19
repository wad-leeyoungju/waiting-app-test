package co.wadcorp.waiting.data.infra.customer;

import co.wadcorp.waiting.data.domain.customer.CustomerEntity;
import co.wadcorp.waiting.data.domain.customer.CustomerRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long>,
    CustomerRepository {

  List<CustomerEntity> findAllBySeqIn(List<Long> seqs);
}
