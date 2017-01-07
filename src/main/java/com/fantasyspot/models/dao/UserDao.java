package com.fantasyspot.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fantasyspot.models.User;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	List<User> findAll(); // get all users
    User findByUserName(String userName); // get user with username only
    User findByUid(int uid); // get user with unique id only
    User findByYahooGUID(String yahooGUID); // get user with yahoo GUID only

}
