package com.slz.demo.dao;

import com.slz.demo.pojo.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ChatDao extends JpaRepository<Chat,String >,JpaSpecificationExecutor<Chat>{

    List<Chat> findByChatIdentityOrderByTime(String chatIdentity);

    @Transactional
    @Modifying
    @Query(value = "update chat set is_read=1 where chat_identity=?1 and is_read=0"
            ,nativeQuery = true)
    void updateIsRead(String id);

    Integer countByToAccountAndIsRead(String account,Integer isRead);

    Integer countByToAccountAndFromAccountAndIsRead(String toAccount,String fromAccount,Integer isRead);
}
