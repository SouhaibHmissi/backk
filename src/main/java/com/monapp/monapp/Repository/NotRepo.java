package com.monapp.monapp.Repository;

import com.monapp.monapp.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NotRepo extends JpaRepository<Notification, Long> {
    @Query("SELECT u.email FROM User u JOIN Notification n ON u.id = n.sender_id " +
            "WHERE n.sender_id = :notificationId AND" +
            " u.role = 'EMPLOYE' AND n.sent_at = (SELECT MAX(n2.sent_at) FROM" +
            " Notification n2 WHERE n2.sender_id = n.sender_id)")
    String findSenderEmailByNotificationId(@Param("notificationId") Long notificationId);
    @Query("SELECT u.email FROM User u JOIN Notification n ON u.id = n.recipient_id " +
            "WHERE n.recipient_id = :notificationIdR AND u.id = :notificationIdR AND u.role = 'CHEF' AND " +
            "n.sent_at = (SELECT MAX(n3.sent_at) FROM Notification n3 WHERE n3.recipient_id = n.recipient_id)")
    String findRecipEmailByNotificationId(@Param("notificationIdR") Long notificationId);

}
