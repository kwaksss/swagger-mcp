package org.example.shoppingmall.Repository;

import org.example.shoppingmall.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

//DB 접근 창구
//JPA가 자동으로 findAll(),findById(),save,delete() 만들어준다.
public interface UserRepository extends JpaRepository<Users,Integer> {


}
