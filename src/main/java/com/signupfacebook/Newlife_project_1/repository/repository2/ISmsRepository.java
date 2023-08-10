package com.signupfacebook.Newlife_project_1.repository.repository2;

import com.signupfacebook.Newlife_project_1.model.entity2.SmsEntity2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("companyEntityManagerFactory")
public interface ISmsRepository extends JpaRepository<SmsEntity2, String> {
    @Query(value = "select * from sms " +
                    "where sms.sender_id like CONCAT(:sender, '%') " +
                    "and sms.received_time like CONCAT(:receiveTime, '%')", nativeQuery = true)
    List<SmsEntity2> findBySenderIdAndReceivedTime(@Param("sender") String sender,
                                                   @Param("receiveTime") String receiveTime);
}
