package fi.kuku.dao;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import fi.kuku.entity.User;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Integer> {

	

}
