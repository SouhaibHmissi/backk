package com.monapp.monapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CongeTriggerService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CongeTriggerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createCongeTrigger() {
        // Vérifier d'abord si le déclencheur existe déjà
        String triggerCheckQuery = "SELECT 1 FROM information_schema.triggers WHERE trigger_name = 'demande_conge_trigger'";
        List<Integer> triggerList = jdbcTemplate.queryForList(triggerCheckQuery, Integer.class);

        // Si le déclencheur n'existe pas, le créer
        if (triggerList.isEmpty()) {
            String triggerQuery = "CREATE TRIGGER demande_conge_trigger AFTER INSERT ON conge " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "DECLARE chef_id INT; " +
                    "DECLARE notification_subject VARCHAR(255); " +
                    "DECLARE notification_content TEXT; " +
                    "SELECT id INTO chef_id FROM user WHERE role = 'CHEF' AND service = " +
                    "(SELECT service FROM user WHERE id = NEW.user_id); " +
                    "SET notification_subject = 'Demande de congé'; " +
                    "SET notification_content = CONCAT(" +
                    "'Type : ', NEW.type, '\n', " +
                    "'Date de demande : ', NEW.date_dem, '\n', " +
                    "'Date de début : ', NEW.date_deb, '\n', " +
                    "'Date de fin : ', NEW.date_fin, '\n', " +
                    "'Durée : ', NEW.durée, '\n', " +
                    "'Statut : ', NEW.statut, '\n', " +
                    (isNewExceptionalConge() ?
                            "'Motif : ', NEW.motif, '\n', " :
                            "") +
                    "'File : ', NEW.file, '\n', " +
                    "'Solde : ', NEW.solde); " +
                    "INSERT INTO notification (sender_id, recipient_id, sent_at,subject, content) " +
                    "VALUES (NEW.user_id, chef_id  , NOW(), notification_subject,notification_content); " +
                    "END;";
            jdbcTemplate.execute(triggerQuery);
        }
    }

    private boolean isNewExceptionalConge() {
        return false;
    }
}
