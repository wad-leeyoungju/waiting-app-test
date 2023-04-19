package co.wadcorp.waiting.data.infra.customer;

import co.wadcorp.waiting.data.domain.customer.TermsCustomerEntity;
import co.wadcorp.waiting.data.domain.customer.TermsCustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTermsCustomerRepository extends TermsCustomerRepository, JpaRepository<TermsCustomerEntity, Long> {

}
