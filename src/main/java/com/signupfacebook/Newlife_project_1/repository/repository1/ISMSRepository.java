package com.signupfacebook.Newlife_project_1.repository.repository1;

import com.signupfacebook.Newlife_project_1.model.entity1.SmsEntity1;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
@Qualifier("employeeEntityManagerFactory")
public interface ISMSRepository extends JpaRepository<SmsEntity1, String> {
    @Query(value = "select * from sms " +
                    "where sms.date_receive like CONCAT(:dateReceiver, '%') or " +
                    "sms.date_send like CONCAT(:dateSend, '%') or " +
                    "sms.receiver like CONCAT(:receiver, '%') ", nativeQuery = true)
    List<SmsEntity1> findAllByDateSendOrDateReceiveOrReceiver(@Param("dateSend") String dateSend,
                                                              @Param("dateReceiver") String dateReceiver,
                                                              @Param("receiver") String receiver);

    @Query(value = "select * from sms " +
                    "where sms.date_receive like CONCAT(:dateReceiver, '%') and " +
                    "sms.date_send like CONCAT(:dateSend, '%') and " +
                    "sms.receiver like CONCAT(:receiver, '%') ", nativeQuery = true)
    List<SmsEntity1> findAllByDateSendAndDateReceiveAndReceiver(@Param("dateSend") String dateSend,
                                                                @Param("dateReceiver") String dateReceiver,
                                                                @Param("receiver") String receiver);
}
