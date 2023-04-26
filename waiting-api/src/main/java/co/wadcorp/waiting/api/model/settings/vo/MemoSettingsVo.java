package co.wadcorp.waiting.api.model.settings.vo;

import java.util.List;
import lombok.Getter;

@Getter
public class MemoSettingsVo {

  private final List<String> keywords;

  public MemoSettingsVo(List<String> keywords) {
    this.keywords = keywords;
  }
}
