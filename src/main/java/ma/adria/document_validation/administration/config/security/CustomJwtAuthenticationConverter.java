package ma.adria.document_validation.administration.config.security;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;

public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {
    @Value("${app.key_cloak.client_id}")
    private String clientId;
    @Override
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        try {
            JSONObject resourceAccess = new JSONObject(jwt.getClaimAsString("resource_access"));
            if (resourceAccess.has(clientId)) {
                JSONObject microserviceAdmin = resourceAccess.getJSONObject(clientId);

                if (microserviceAdmin.has("roles")) {
                    JSONArray rolesArray = microserviceAdmin.getJSONArray("roles");

                    for (int i = 0; i < rolesArray.length(); i++) {
                        String role = rolesArray.getString(i);
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                }
            }
        } catch (JSONException e) {
            // Handle JSON parsing exception
            e.printStackTrace();
        }

        return authorities;
    }
}