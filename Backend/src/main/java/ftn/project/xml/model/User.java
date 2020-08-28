package ftn.project.xml.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String profession;
    private TUser.MyPapers myPapers;
    private TUser.MyReviews myReviews;
    private TUser.PendingPapersToReview pendingPapersToReview;
    private List<Authority> authorities;

    public User(String username, String password, String name, String surname, String email, String profession, TUser.MyPapers myPapers,
                TUser.MyReviews myReviews, TUser.PendingPapersToReview pendingPapersToReview, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profession = profession;
        this.myPapers = myPapers;
        this.myReviews = myReviews;
        this.pendingPapersToReview = pendingPapersToReview;
        this.authorities = authorities;
    }

    public User(TUser tUser) {
        this.username = tUser.getUsername();
        this.password = tUser.getPassword();
        this.name = tUser.getName();
        this.surname = tUser.getSurname();
        this.email = tUser.getEmail();
        this.profession = tUser.getProfession();
        this.myPapers = tUser.getMyPapers();
        this.myReviews = tUser.getMyReviews();
        this.pendingPapersToReview = tUser.getPendingPapersToReview();
        this.authorities = new ArrayList<Authority>();
        Authority a = new Authority();
        a.setType(tUser.getRole());
        this.authorities.add(a);
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TUser.MyPapers getMyPapers() {
        return myPapers;
    }

    public void setMyPapers(TUser.MyPapers myPapers) {
        this.myPapers = myPapers;
    }

    public TUser.MyReviews getMyReviews() {
        return myReviews;
    }

    public void setMyReviews(TUser.MyReviews myReviews) {
        this.myReviews = myReviews;
    }

    public TUser.PendingPapersToReview getPendingPapersToReview() {
        return pendingPapersToReview;
    }

    public void setPendingPapersToReview(TUser.PendingPapersToReview pendingPapersToReview) {
        this.pendingPapersToReview = pendingPapersToReview;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
