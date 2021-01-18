package pub.hejun.cloud.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * Client服务
 *
 * @author HeJun
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // TODO 使用数据库或OpenFeign实现
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(passwordEncoder.encode("1234"));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("authorization_code,password,refresh_token".split(",")));
        clientDetails.setAccessTokenValiditySeconds(1800);
        clientDetails.setRefreshTokenValiditySeconds(3600);
        clientDetails.setRegisteredRedirectUri(Collections.emptySet());
        clientDetails.setScope(Arrays.asList("READ,WRITE".split(",")));
        clientDetails.setAutoApproveScopes(clientDetails.getScope());
        return clientDetails;
    }
}
