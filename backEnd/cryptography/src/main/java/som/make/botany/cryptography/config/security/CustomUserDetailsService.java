package som.make.botany.cryptography.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import som.make.botany.cryptography.system.bean.SysUserBean;
import som.make.botany.cryptography.system.dao.SysUserDao;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private SysUserDao sysUserDao;

    @Autowired
    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserBean sysUserBean = sysUserDao.findByLoginName(username);
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("user");
        Set<SimpleGrantedAuthority> set = new HashSet<>();
        set.add(simpleGrantedAuthority);
        return new User(sysUserBean.getLoginName(), sysUserBean.getPassword(), set);
    }

}
