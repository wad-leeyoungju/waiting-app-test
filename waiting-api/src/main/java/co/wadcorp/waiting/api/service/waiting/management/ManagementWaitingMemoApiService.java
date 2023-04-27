package co.wadcorp.waiting.api.service.waiting.management;

import org.springframework.stereotype.Service;

@Service
public class ManagementWaitingMemoApiService {

  public Object save(String shopId, Object serviceRequest) {
    System.out.println("conflict!!!");
    return null;
  }

  public void delete(String waitingId) {

  }
}
