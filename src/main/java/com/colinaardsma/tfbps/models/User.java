package com.colinaardsma.tfbps.models;

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
    private String yahooGUID;
    
	private List<Post> posts;
	
	private List<YahooRotoLeague> yahooRotoLeagues;


//    private Map<String, StockHolding> portfolio; // turn this into custom spreadsheets
        
    public User(String userName, String password) {
        this.passHash = PasswordHash.getHash(password);
        this.userName = userName;
//        this.portfolio = new HashMap<String, StockHolding>();  // turn this into custom spreadsheets
        this.userGroup = "basic"; // start as a basic user
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
    
    // blog post methods
	protected void addPost(Post post) {
		posts.add(post);
	}
	
	@OneToMany
    @JoinColumn(name = "author_uid")
    public List<Post> getPosts() {
        return posts;
    }
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
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

    @Column(name = "yahooGUID")
    public String getYahooGUID() {
        return yahooGUID;
    }

    public void setYahooGUID(String yahooGUID) {
        this.yahooGUID = yahooGUID;
    }
    
//    @Column(name = "yahooRotoLeagueKeys")
//    public List<String> getYahooRotoLeagueKeys() {
//    	return yahooRotoLeagueKeys;
//    }
//    
//    public void setYahooRotoLeagueKeys(List<String> yahooRotoLeagueKeys) {
//    	this.yahooRotoLeagueKeys = yahooRotoLeagueKeys;
//    }
    
	// custom spreadsheet methods
    // turn this into custom spreadsheets
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "owner_id")
	@ManyToMany(mappedBy="users")
    public List<YahooRotoLeague> getYahooRotoLeagues() {
        return yahooRotoLeagues;
    }

    @SuppressWarnings("unused")
	private void setYahooRotoLeagues(List<YahooRotoLeague> yahooRotoLeagues) {
    	this.yahooRotoLeagues = yahooRotoLeagues;
    }

    void addYahooRotoLeague (YahooRotoLeague league) throws IllegalArgumentException {

        // Ensure a holding for the symbol doesn't already exist
        if (yahooRotoLeagues.contains(league)) {
            throw new IllegalArgumentException("League already exits for user " + getUserName());
        }

        yahooRotoLeagues.add(league);
    }

}