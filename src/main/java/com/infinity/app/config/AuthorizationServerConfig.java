package com.infinity.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	public AuthenticationManager authManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("ikenna1")
                .secret("lola1")
                .authorizedGrantTypes("password")
                .scopes("read");
    }	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authManager)
					.tokenStore(tokenStore())
					.accessTokenConverter(jConverter());
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jConverter() {
		var converter= new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyFactory= new KeyStoreKeyFactory(
					new ClassPathResource("ssia.jks"),"ssia123".toCharArray());
		
		converter.setKeyPair(keyFactory.getKeyPair("ssia"));
		return converter;
	}
}
