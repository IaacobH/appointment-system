package queries;

public final class AppointmentQueries {
    public static final String INSERT = """
                    INSERT INTO appointments(professional_id, client_id, offered_service_id, datetime)
                    VALUES(?,?,?,?)
                    """;
    public static final String FIND_BY_ID ="""
                    SELECT
                                    a.appointment_id,
                                    a.datetime AS appointment_datetime,
                                    s.appointment_status AS status_name,
                                
                                    os.offered_service_id,
                                    os.service_name AS offered_service_name,
                                    os.price,
                                
                                    p.professional_id,
                                    p.name AS professional_name,
                                    p.lastname AS professional_lastname,
                                    p.speciality,
                                    p.email AS professional_email,
                                
                                    c.client_id,
                                    c.name AS client_name,
                                    c.lastname AS client_lastname,
                                    c.email AS client_email
                                
                                FROM appointments a
                                
                                JOIN status s
                                    ON a.status_id = s.status_id
                                
                                JOIN offered_services os
                                    ON a.offered_service_id = os.offered_service_id
                                
                                JOIN professionals p
                                    ON a.professional_id = p.professional_id
                                
                                JOIN clients c
                                    ON a.client_id = c.client_id
                    WHERE appointment_id = ?;
                    """;
    public static final String FIND_ALL="""
                    SELECT
                                    a.appointment_id,
                                    a.datetime AS appointment_datetime,
                                    s.appointment_status AS status_name,
                                
                                    os.offered_service_id,
                                    os.service_name AS offered_service_name,
                                    os.price,
                                
                                    p.professional_id,
                                    p.name AS professional_name,
                                    p.lastname AS professional_lastname,
                                    p.speciality,
                                    p.email AS professional_email,
                                
                                    c.client_id,
                                    c.name AS client_name,
                                    c.lastname AS client_lastname,
                                    c.email AS client_email
                                
                                FROM appointments a
                                
                                JOIN status s
                                    ON a.status_id = s.status_id
                                
                                JOIN offered_services os
                                    ON a.offered_service_id = os.offered_service_id
                                
                                JOIN professionals p
                                    ON a.professional_id = p.professional_id
                                
                                JOIN clients c
                                    ON a.client_id = c.client_id;                    
                    """;
    public static final String UPDATE_APPOINTMENT="""
                UPDATE appointments
                SET professional_id = ?,
                	client_id = ?,
                	offered_service_id = ?,
                    datetime = ?,
                    status_id = ?
                WHERE appointment_id = ?;
                """;
    public static final String EXISTS_PROFESSIONAL_DATETIME="""
            SELECT 1
            FROM appointments
            WHERE professional_id = ?
              AND datetime = ?
              AND status_id = 1
            LIMIT 1;
            """;
    public static final String EXISTS_CLIENT_DATETIME="""
            SELECT 1
            FROM appointments
            WHERE client_id = ?
              AND datetime = ?
              AND status_id = 1
            LIMIT 1;
            """;

}
