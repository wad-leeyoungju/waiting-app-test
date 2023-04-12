package co.wadcorp.waiting.data.domain.settings;

import co.wadcorp.waiting.data.support.BaseEntity;
import co.wadcorp.waiting.data.support.BooleanYnConverter;
import co.wadcorp.waiting.data.support.OptionSettingsConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cw_option_settings")
public class OptionSettingsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "publish_yn", columnDefinition = "char")
    @Convert(converter = BooleanYnConverter.class)
    private Boolean isPublished;

    @Column(name = "data", columnDefinition = "text")
    @Convert(converter = OptionSettingsConverter.class)
    private OptionSettingsData optionSettingsData;

    public OptionSettingsEntity(String shopId, OptionSettingsData optionSettingsData) {
        this.shopId = shopId;
        this.isPublished = true;
        this.optionSettingsData = optionSettingsData;
    }

    public void unPublish() {
        this.isPublished = false;
    }

    public Boolean isUsedPersonOptionSetting() {
        return this.optionSettingsData.getIsUsedPersonOptionSetting();
    }

    public List<OptionSettingsData.PersonOptionSetting> getPersonOptionSettings() {
        return this.optionSettingsData.getPersonOptionSettings();
    }

}
