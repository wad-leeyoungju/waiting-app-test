package co.wadcorp.waiting.infra.pos;

import co.wadcorp.waiting.infra.pos.dto.PosApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatchtablePosUserClient {

    public static final ParameterizedTypeReference<PosApiResponse<PosApiResponse.Reason>> POS_API_RESPONSE_PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final CatchtablePosUserHttpClient posUserHttpClient;

}
