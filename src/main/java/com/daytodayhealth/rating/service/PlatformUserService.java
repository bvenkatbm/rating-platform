package com.daytodayhealth.rating.service;

import com.daytodayhealth.rating.domain.PlatformUserPrincipal;
import com.daytodayhealth.rating.entity.PlatformUser;
import com.daytodayhealth.rating.entity.RatingEntity;
import com.daytodayhealth.rating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlatformUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public PlatformUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        PlatformUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new PlatformUserPrincipal(user);
    }

    public void setNames(List<RatingEntity> ratingEntityList) {
        for (RatingEntity entity: ratingEntityList) {
            if (null != entity.getRatedBy())
                entity.setPostedByName(userRepository.getById(entity.getRatedBy()).getName());
            else entity.setPostedByName("Anonymous");
        }
    }
}
