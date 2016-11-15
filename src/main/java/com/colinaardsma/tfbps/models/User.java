package com.colinaardsma.tfbps.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.colinaardsma.tfbps.models.util.PasswordHash;


@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    private String userName;
    private String passHash;
    private String userGroup;

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

    protected void setUserName(String userName){
        this.userName = userName;
    }

    @NotNull
    @Column(name = "userGroup")
    public String getuserGroup() {
        return userGroup;
    }

    protected void setUserGroup(String userGroup) {
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
//    // turn this into custom spreadsheets
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "owner_id")
//    public Map<String, StockHolding> getPortfolio() {
//        return portfolio;
//    }
//
//    @SuppressWarnings("unused")
//	private void setPortfolio(Map<String, StockHolding> portfolio) {
//        this.portfolio = portfolio;
//    }
//
//    void addHolding (StockHolding holding) throws IllegalArgumentException {
//
//        // Ensure a holding for the symbol doesn't already exist
//        if (portfolio.containsKey(holding.getSymbol())) {
//            throw new IllegalArgumentException("A holding for symbol " + holding.getSymbol()
//                    + " already exits for user " + getUid());
//        }
//
//        portfolio.put(holding.getSymbol(), holding);
//    }

}