package com.sitesstorageproject.security;

import com.sitesstorageproject.repos.UserRepository;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class SitesAccessManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final UserRepository userRepo;

    public SitesAccessManager(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        return new AuthorizationDecision(authentication.get().getAuthorities().contains(new SimpleGrantedAuthority(object.getRequest().getRequestURI().substring(object.getRequest().getRequestURI().indexOf("site/")))));
//        return new AuthorizationDecision(authentication.get().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(object.getRequest().getRequestURI().substring(object.getRequest().getRequestURI().indexOf("site/") + 5))));
        boolean result = false;
        int pos = object.getRequest().getRequestURI().indexOf("site/");
        String name = object.getRequest().getRequestURI().substring(pos + 5, object.getRequest().getRequestURI().indexOf("/", pos + 5));
        String needed = "see_site_of_" + userRepo.findByLogin(name).getId().toString();
        for (GrantedAuthority authority : authentication.get().getAuthorities()) {
            if (authority.getAuthority().equals(needed) || authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_TEACHER")) {
                result = true;
            }
        }
        return new AuthorizationDecision(result);
    }
}
