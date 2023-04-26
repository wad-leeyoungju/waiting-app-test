package co.wadcorp.waiting.data.service.settings;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class MemoSettingsServiceTest {

  @Test
  void streamTest() {
    IntStream.range(0, 10).forEach(i -> System.out.println(i));
  }

}