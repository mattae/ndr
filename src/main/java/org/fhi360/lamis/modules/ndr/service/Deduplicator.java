package org.fhi360.lamis.modules.ndr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.modules.ndr.util.Duplicate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class Deduplicator {
    private final SimpMessageSendingOperations messagingTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static List<String> tables = Arrays.asList("adhere_history", "adr_history", "anc", "chronic_care", "clinic",
            "delivery", "devolve", "dm_screen_history", "eac", "encounter", "laboratory", "maternal_followup",
            "mother_information", "nigqual", "oi_history", "partner_information", "pharmacy", "regimen_history",
            "status_history", "tb_screen_history", "patient_case_manager");

    private List<Long> removeDuplicate(Duplicate duplicate) {
        List<Long> deleteIds = new ArrayList<>();
        this.messagingTemplate.convertAndSend("/topic/ndr-status", String.format("Duplicate: %s", duplicate));
        List<Long> patientIds = jdbcTemplate.queryForList("select ID from PATIENT where FACILITY_ID = ? " +
                        "and HOSPITAL_NUM = ? order by last_modified desc",
                Long.class, duplicate.getFacilityId(), duplicate.getHospitalNum());
        Long primaryId = patientIds.get(0);
        transactionTemplate.execute(status1 -> {
            patientIds.stream()
                    .filter(id -> !id.equals(primaryId))
                    .forEach(id -> {
                        deleteIds.add(id);
                        /*
                          Migrate the following tables rows to the primary id: AdhereHistory, ADRHistory, ANC, ChronicCare,
                          Clinic, Delivery, Devolve, DMScreenHistory, EAC, Encounter, Laboratory, MaternalFollowup,
                          MotherInformation, Nigqual, OIHistory, PartnerInformation, Pharmacy, RegimenHistory, StatusHistory
                          and TBScreenHistory
                         */
                        transactionTemplate.execute(ts -> {
                            tables.forEach(table -> {
                                LOG.info("{}", String.format("update %s set patient_id = %s, archived = true where patient_id = %s and facility_id = %s",
                                        table, primaryId, id, duplicate.getFacilityId()));
                                String tableQuery = String.format("update %s set patient_id = %s, archived = true where patient_id = %s and facility_id = %s",
                                        table, primaryId, id, duplicate.getFacilityId());
                                jdbcTemplate.execute(tableQuery);
                            });
                            return null;
                        });
                    });

            //Remove duplicates from Pharmacy
            String pharmacyQuery = "select FACILITY_ID, PATIENT_ID, DATE_VISIT, count(*) from PHARMACY "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, DATE_VISIT having count(*) > 1";
            List<List<Long>> ids = jdbcTemplate.query(pharmacyQuery, (rs, i) -> {
                Date date = rs.getDate("date_visit");
                return jdbcTemplate.queryForList("select ID from PHARMACY where FACILITY_ID = ? "
                                + "and PATIENT_ID = ? and DATE_VISIT = ? order by last_modified desc", Long.class,
                        duplicate.getFacilityId(), primaryId, date);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Pharmacy {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update PHARMACY_LINE SET archived = true  where PHARMACY_ID = ? ", id);
                                jdbcTemplate.update("update PHARMACY SET archived = true  where ID = ?", id);
                            }
                    );
                    return null;
                });
            });

            //Remove duplicates from Clinic
            String clinicQuery = "select FACILITY_ID, PATIENT_ID, DATE_VISIT, count(*) from CLINIC "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, DATE_VISIT having count(*) > 1";
            ids = jdbcTemplate.query(clinicQuery, (rs, i) -> {
                Date date = rs.getDate("date_visit");
                return jdbcTemplate.queryForList("select ID from CLINIC where FACILITY_ID = ? "
                                + "and PATIENT_ID = ? and DATE_VISIT = ? order by last_modified desc", Long.class,
                        duplicate.getFacilityId(), primaryId, date);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Clinic {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update CLINIC SET archived = true  where ID = ?", id);
                            }
                    );
                    return null;
                });
            });

            //Remove duplicates from Laboratory
            String labQuery = "select FACILITY_ID, PATIENT_ID, DATE_RESULT_RECEIVED, count(*) from LABORATORY "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, " +
                    "DATE_RESULT_RECEIVED having count(*) > 1";
            ids = jdbcTemplate.query(labQuery, (rs, i) -> {
                Date date = rs.getDate("date_result_received");
                return jdbcTemplate.queryForList("select ID from LABORATORY where FACILITY_ID = ? "
                                + "and PATIENT_ID = ? and DATE_RESULT_RECEIVED = ? order by last_modified desc", Long.class,
                        duplicate.getFacilityId(), primaryId, date);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Laboratory {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update LABORATORY_LINE SET archived = true  where LABORATORY_ID = ?", id);
                                jdbcTemplate.update("update LABORATORY SET archived = true  where ID = ?", id);
                            }
                    );
                    return null;
                });
            });

            //Remove duplicates from Delivery
            String deliveryQuery = "select FACILITY_ID, PATIENT_ID, DATE_DELIVERY, count(*) from DELIVERY "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, DATE_DELIVERY having count(*) > 1";
            ids = jdbcTemplate.query(deliveryQuery, (rs, i) -> {
                Date date = rs.getDate("date_delivery");
                return jdbcTemplate.queryForList("select ID from DELIVERY where FACILITY_ID = ? "
                                + "and PATIENT_ID = ? and DATE_DELIVERY = ? order by last_modified desc", Long.class,
                        duplicate.getFacilityId(), primaryId, date);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Delivery {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update DELIVERY SET archived = true  where DELIVERY_ID = ? and PATIENT_ID = ? and FACILITY_ID = ?",
                                        id, primaryId, duplicate.getFacilityId());
                            }
                    );
                    return null;
                });
            });

            //Remove duplicates from EAC
            String eacQuery = "select FACILITY_ID, PATIENT_ID, DATE_EAC1, count(*) from EAC "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, DATE_EAC1 having count(*) > 1";
            ids = jdbcTemplate.query(eacQuery, (rs, i) -> {
                Date date = rs.getDate("date_eac1");
                return jdbcTemplate.queryForList("select ID from EAC where FACILITY_ID = ? "
                        + "and PATIENT_ID = ? and DATE_EAC1 = ? order by last_modified desc ", Long.class, duplicate.getFacilityId(), primaryId, date);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting EAC {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update EAC SET archived = true  where ID = ? and PATIENT_ID = ? and FACILITY_ID = ?",
                                        id, primaryId, duplicate.getFacilityId());
                            }
                    );
                    return null;
                });
            });

            //Remove duplicates from Patient Case Manager
            String caseQuery = "select FACILITY_ID, PATIENT_ID, date_assigned, count(*) from patient_case_manager "
                    + "where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, date_assigned having count(*) > 1";
            ids = jdbcTemplate.query(caseQuery, (rs, i) -> {
                Date date = rs.getDate("date_assigned");
                return jdbcTemplate.queryForList("select ID from patient_case_manager where FACILITY_ID = ? "
                        + "and PATIENT_ID = ? and DATE_assigned = ? order by last_modified desc ", Long.class, duplicate.getFacilityId(), primaryId, date);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Patient CaseManager {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update PATIENT_CASE_MANAGER SET archived = true  where ID = ? and PATIENT_ID = ? and FACILITY_ID = ?",
                                        id, primaryId, duplicate.getFacilityId());
                            }
                    );
                    return null;
                });
            });


            //Remove duplicates from Status History
            String statusQuery = "select FACILITY_ID, PATIENT_ID, DATE_STATUS, STATUS, count(*) from STATUS_HISTORY "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, DATE_STATUS, STATUS having count(*) > 1";
            ids = jdbcTemplate.query(statusQuery, (rs, i) -> {
                Date date = rs.getDate("date_status");
                String status = rs.getString("status");
                return jdbcTemplate.queryForList("select ID from STATUS_HISTORY where FACILITY_ID = ? "
                                + "and PATIENT_ID = ? and DATE_STATUS = ? and STATUS = ? order by last_modified desc",
                        Long.class, duplicate.getFacilityId(), primaryId, date, status);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Status History {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update STATUS_HISTORY SET archived = true  where ID = ? and PATIENT_ID = ? and FACILITY_ID = ?",
                                        id, primaryId, duplicate.getFacilityId());
                            }
                    );
                    return null;
                });
            });

            //Remove duplicates from Regimen History
            String regimeQuery = "select FACILITY_ID, PATIENT_ID, DATE_VISIT, REGIMEN_ID, count(*) from REGIMEN_HISTORY "
                    + " where FACILITY_ID = ? and PATIENT_ID = ? group by FACILITY_ID, PATIENT_ID, DATE_VISIT, REGIMEN_ID having count(*) > 1";
            ids = jdbcTemplate.query(regimeQuery, (rs, i) -> {
                Date date = rs.getDate("date_visit");
                String regimen = rs.getString("regimen_id");
                return jdbcTemplate.queryForList("select ID from REGIMEN_HISTORY where FACILITY_ID = ? "
                                + "and PATIENT_ID = ? and DATE_VISIT = ? and REGIMEN_ID = ? order by last_modified desc",
                        Long.class, duplicate.getFacilityId(), primaryId, date, regimen);
            }, duplicate.getFacilityId(), primaryId);
            ids.forEach(ids1 -> {
                if (ids1.size() > 0) {
                    ids1.remove(0);
                }
                transactionTemplate.execute(ts -> {
                    ids1.forEach(id -> {
                                LOG.info("Deleting Regimen History {}, facility {}", id, duplicate.getFacilityId());
                                jdbcTemplate.update("update REGIMEN_HISTORY SET archived = true where ID = ? and PATIENT_ID = ? and FACILITY_ID = ?",
                                        id, primaryId, duplicate.getFacilityId());
                            }
                    );
                    return null;
                });
            });
            return null;
        });
        return deleteIds;
    }

    private void removeRecordDuplicate() {
        //Remove duplicates from Pharmacy
        String pharmacyQuery = "select FACILITY_ID, PATIENT_ID, DATE_VISIT, count(*) from PHARMACY "
                + "group by FACILITY_ID, PATIENT_ID, DATE_VISIT having count(*) > 1";
        List<List<Long>> ids = jdbcTemplate.query(pharmacyQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_visit");
            return jdbcTemplate.queryForList("select ID from PHARMACY where FACILITY_ID = ? "
                            + "and PATIENT_ID = ? and DATE_VISIT = ? order by last_modified desc", Long.class,
                    facilityId, patientId, date);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update PHARMACY_LINE SET archived = true  where PHARMACY_ID = ?", id);
                        jdbcTemplate.update("update PHARMACY SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from Clinic
        String clinicQuery = "select FACILITY_ID, PATIENT_ID, DATE_VISIT, count(*) from CLINIC "
                + "group by FACILITY_ID, PATIENT_ID, DATE_VISIT having count(*) > 1";
        ids = jdbcTemplate.query(clinicQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_visit");
            return jdbcTemplate.queryForList("select ID from CLINIC where FACILITY_ID = ? "
                    + "and PATIENT_ID = ? and DATE_VISIT = ? order by last_modified desc", Long.class, facilityId, patientId, date);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update CLINIC SET archived = true  where ID = ? ", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from Laboratory
        String labQuery = "select FACILITY_ID, PATIENT_ID, DATE_RESULT_RECEIVED, count(*) from LABORATORY "
                + "group by FACILITY_ID, PATIENT_ID, LABTEST_ID, DATE_RESULT_RECEIVED having count(*) > 1";
        ids = jdbcTemplate.query(labQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_result_received");
            return jdbcTemplate.queryForList("select ID from LABORATORY where FACILITY_ID = ? "
                            + "and PATIENT_ID = ? and DATE_RESULT_RECEIVED = ? order by last_modified desc",
                    Long.class, facilityId, patientId, date);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update LABORATORY_LINE SET archived = true  where LABORATORY_ID = ?", id);
                        jdbcTemplate.update("update LABORATORY SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from Delivery
        String deliveryQuery = "select FACILITY_ID, PATIENT_ID, DATE_DELIVERY, count(*) from DELIVERY "
                + "group by FACILITY_ID, PATIENT_ID, DATE_DELIVERY having count(*) > 1";
        ids = jdbcTemplate.query(deliveryQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_delivery");
            return jdbcTemplate.queryForList("select ID from DELIVERY where FACILITY_ID = ? "
                            + "and PATIENT_ID = ? and DATE_DELIVERY = ? order by last_modified desc", Long.class,
                    facilityId, patientId, date);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update DELIVERY SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from EAC
        String eacQuery = "select FACILITY_ID, PATIENT_ID, DATE_EAC1, count(*) from EAC "
                + " group by FACILITY_ID, PATIENT_ID, DATE_EAC1 having count(*) > 1";
        ids = jdbcTemplate.query(eacQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_eac1");
            return jdbcTemplate.queryForList("select ID from EAC where FACILITY_ID = ? "
                    + "and PATIENT_ID = ? and DATE_EAC1 = ? order by last_modified desc", Long.class, facilityId, patientId, date);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update EAC SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from Patient CaseManager
        String pcmQuery = "select FACILITY_ID, PATIENT_ID, DATE_ASSIGNED, count(*) from PATIENT_CASE_MANAGER "
                + " group by FACILITY_ID, PATIENT_ID, DATE_ASSIGNED having count(*) > 1";
        ids = jdbcTemplate.query(eacQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_assigned");
            return jdbcTemplate.queryForList("select ID from PATIENT_CASE_MANAGER where FACILITY_ID = ? "
                    + "and PATIENT_ID = ? and DATE_ASSIGNED = ? order by last_modified desc", Long.class, facilityId, patientId, date);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update PATIENT_CASE_MANAGER SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from Status History
        String statusQuery = "select FACILITY_ID, PATIENT_ID, DATE_STATUS, STATUS, count(*) from STATUSHISTORY "
                + "group by FACILITY_ID, PATIENT_ID, DATE_STATUS, STATUS having count(*) > 1";
        ids = jdbcTemplate.query(statusQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_status");
            String status = rs.getString("status");
            return jdbcTemplate.queryForList("select ID from STATUS_HISTORY where FACILITY_ID = ? "
                            + "and PATIENT_ID = ? and DATE_STATUS = ? AND STATUS = ? order by last_modified desc",
                    Long.class, facilityId, patientId, date, status);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update STATUS_HISTORY SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });

        //Remove duplicates from Regimen History
        String regimeQuery = "select FACILITY_ID, PATIENT_ID, DATE_VISIT, REGIMEN_ID, count(*) from REGIMEN_HISTORY "
                + "group by FACILITY_ID, PATIENT_ID, DATE_VISIT, REGIMEN_ID having count(*) > 1";
        ids = jdbcTemplate.query(regimeQuery, (rs, i) -> {
            Long facilityId = rs.getLong("facility_id");
            Long patientId = rs.getLong("patient_id");
            Date date = rs.getDate("date_visit");
            String regimen = rs.getString("regimen_id");
            return jdbcTemplate.queryForList("select ID from REGIMEN_HISTORY where FACILITY_ID = ? "
                            + "and PATIENT_ID = ? and DATE_VISIT = ? and REGIMEN_ID = ? order by last_modified desc",
                    Long.class, facilityId, patientId, date, regimen);
        });
        ids.forEach(ids1 -> {
            if (ids1.size() > 0) {
                ids1.remove(0);
            }
            ids1.forEach(id -> transactionTemplate.execute(status -> {
                        jdbcTemplate.update("update REGIMEN_HISTORY SET archived = true  where ID = ?", id);
                        return null;
                    })
            );
        });
    }

    private void removeDuplicates() {
        LOG.info("Starting...");
        String query = "select FACILITY_ID as facilityId, HOSPITAL_NUM as hospitalNum, count(*) from PATIENT "
                + "group by FACILITY_ID, HOSPITAL_NUM having count(*) > 1 order by 1";
        List<Duplicate> duplicates = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Duplicate.class));
        this.messagingTemplate.convertAndSend("/topic/ndr-status", String.format("Duplicates: %s", duplicates));
        duplicates.forEach(duplicate -> {
            List<Long> deletes = removeDuplicate(duplicate);

            LOG.info("Deleting Patient {}, facility {}", deletes, duplicate.getFacilityId());
            Map<String, Object> params = new HashMap<>();
            params.put("ids", deletes);
            params.put("facility", duplicate.getFacilityId());
            transactionTemplate.execute(status -> {
                namedParameterJdbcTemplate.update("update patient SET archived = true  where id in (:ids) and facility_id = :facility", params);
                return null;
            });
        });
    }

    @Transactional
    public void deduplicate() {
        removeDuplicates();
        removeRecordDuplicate();
    }
}
