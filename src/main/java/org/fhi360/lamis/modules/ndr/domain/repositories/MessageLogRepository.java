package org.fhi360.lamis.modules.ndr.domain.repositories;

import org.fhi360.lamis.modules.ndr.domain.entities.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
    List<MessageLog> findByIdentifier(String identifier);

    @Query("select coalesce(max(id), 0) from MessageLog ")
    Long lastMessageId();
}
