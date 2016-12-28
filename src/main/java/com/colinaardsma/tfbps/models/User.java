package com.colinaardsma.tfbps.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.colinaardsma.tfbps.models.util.PasswordHash;


// https://alextretyakov.blogspot.com/2013/07/jpa-many-to-many-mappings.html
// https://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example-annotation/
// https://docs.jboss.org/hibernate/stable/annotations/reference/en/html/entity.html


@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    private String userName;
    private String passHash;
    private String userGroup;
    private String email;
    private String yahooOAuthAccessToken;
    private String yahooOAuthSessionHandle;
    private String yahooOAuthTokenSecret;
    private Date yahooOAuthTokenExpiration;
    private String yahooGUID;
    
    // join variables
	private List<Post> posts;
	private List<YahooRotoLeague> yahooRotoLeagues;
	private List<YahooRotoTeam> yahooRotoTeams;
	private List<OttoneuOldSchoolLeague> ottoneuOldSchoolLeagues;
	private List<OttoneuOldSchoolTeam> ottoneuTeams;
	private List<UserCustomRankingsB> userBatterSGP;
    private List<UserCustomRankingsP> userPitcherSGP;
    private List<KeeperCosts> keeperCosts;

//    private Map<String, StockHolding> portfolio; // turn this into custom spreadsheets
        
    public User(String userName, String password) {
        this.passHash = PasswordHash.getHash(password);
        this.userName = userName;
//        this.portfolio = new HashMap<String, StockHolding>();  // turn this into custom spreadsheets
        this.userGroup = "basic"; // start as a basic user
        this.yahooOAuthTokenExpiration = new Date(System.currentTimeMillis());

    }

    // empty constructor so Hibernate can do its magic
    public User() {}

    @NotNull
    @Column(name = "username", unique = true)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    @NotNull
    @Column(name = "usergroup")
    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    @NotNull
    @Column(name = "hash")
    public String getPassHash() {
        return passHash;
    }

    protected void setPassHash(String passHash) {
        this.passHash = passHash;
    }
    
    @Column(name = "yahooOAuthSessionHandle")
    public String getYahooOAuthSessionHandle() {
        return yahooOAuthSessionHandle;
    }

    public void setYahooOAuthSessionHandle(String yahooOAuthSessionHandle) {
        this.yahooOAuthSessionHandle = yahooOAuthSessionHandle;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "yahooOAuthAccessToken", length = 100000)
    public String getYahooOAuthAccessToken() {
        return yahooOAuthAccessToken;
    }

    public void setYahooOAuthAccessToken(String yahooOAuthAccessToken) {
        this.yahooOAuthAccessToken = yahooOAuthAccessToken;
    }
    
    @Column(name = "yahooOAuthTokenSecret")
    public String getYahooOAuthTokenSecret() {
    	return yahooOAuthTokenSecret;
    }
    
    public void setYahooOAuthTokenSecret(String yahooOAuthTokenSecret) {
    	this.yahooOAuthTokenSecret = yahooOAuthTokenSecret;
    }

    @Column(name = "yahooOAuthTokenExpiration")
    public Date getYahooOAuthTokenExpiration() {
    	return yahooOAuthTokenExpiration;
    }
    
    public void setYahooOAuthTokenExpiration(Date yahooOAuthTokenExpiration) {
    	this.yahooOAuthTokenExpiration = yahooOAuthTokenExpiration;
    }
    
    @Column(name = "yahooGUID")
    public String getYahooGUID() {
        return yahooGUID;
    }

    public void setYahooGUID(String yahooGUID) {
        this.yahooGUID = yahooGUID;
    }
    
	@OneToMany
    @JoinColumn(name = "author_uid")
    public List<Post> getPosts() {
        return posts;
    }
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	@ManyToMany(mappedBy="users")
    public List<YahooRotoLeague> getYahooRotoLeagues() {
        return yahooRotoLeagues;
    }

    @SuppressWarnings("unused")
	private void setYahooRotoLeagues(List<YahooRotoLeague> yahooRotoLeagues) {
    	this.yahooRotoLeagues = yahooRotoLeagues;
    }
    
    @OneToMany
    @JoinColumn(name = "user_uid")
    public List<YahooRotoTeam> getYahooRotoTeams() {
    	return yahooRotoTeams;
    }
    
    @SuppressWarnings("unused")
    private void setYahooRotoTeams(List<YahooRotoTeam> yahooRotoTeams) {
    	this.yahooRotoTeams = yahooRotoTeams;
    }
    
	@ManyToMany(mappedBy="users")
    public List<OttoneuOldSchoolLeague> getOttoneuOldSchoolLeagues() {
        return ottoneuOldSchoolLeagues;
    }

    @SuppressWarnings("unused")
	private void setOttoneuOldSchoolLeagues(List<OttoneuOldSchoolLeague> ottoneuOldSchoolLeagues) {
    	this.ottoneuOldSchoolLeagues = ottoneuOldSchoolLeagues;
    }
    
    @OneToMany
    @JoinColumn(name = "user_uid")
    public List<OttoneuOldSchoolTeam> getOttoneuTeams() {
    	return ottoneuTeams;
    }
    
    @SuppressWarnings("unused")
    private void setOttoneuTeams(List<OttoneuOldSchoolTeam> ottoneuTeams) {
    	this.ottoneuTeams = ottoneuTeams;
    }
    
   @OneToMany
    @JoinColumn(name = "user_uid")
    public List<UserCustomRankingsB> getUserBatterSGP() {
    	return userBatterSGP;
    }
    
    @SuppressWarnings("unused")
   private void setUserBatterSGP(List<UserCustomRankingsB> userBatterSGP) {
    	this.userBatterSGP = userBatterSGP;
    }
    
    @OneToMany
    @JoinColumn(name = "user_uid")
    public List<UserCustomRankingsP> getUserPitcherSGP() {
    	return userPitcherSGP;
    }
    
    @SuppressWarnings("unused")
    private void setUserPitcherSGP(List<UserCustomRankingsP> userPitcherSGP) {
    	this.userPitcherSGP = userPitcherSGP;
    }

    @OneToMany
    @JoinColumn(name = "user_uid")
    public List<KeeperCosts> getKeeperCosts() {
    	return keeperCosts;
    }
    
    @SuppressWarnings("unused")
    private void setKeeperCosts(List<KeeperCosts> keeperCosts) {
    	this.keeperCosts = keeperCosts;
    }

	protected void addPost(Post post) {
		posts.add(post);
	}
	
    void addYahooRotoLeague (YahooRotoLeague league) throws IllegalArgumentException {

        // Ensure a holding for the symbol doesn't already exist
        if (yahooRotoLeagues.contains(league)) {
            throw new IllegalArgumentException("League already exits for user " + getUserName());
        }

        yahooRotoLeagues.add(league);
    }

}