import model.Appointment;
import model.Client;
import model.OfferedService;
import model.Professional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    static void main(String[] args) {

        var client1 = new Client("iaacob", "hambra", "iaacobh@gmail.com");
        var professional1 = new Professional("carlos", "varela",
                                              "carlosv@gmail.com","psiquiatra");
        var offeredService1 = new OfferedService("consulta",0);
        var appointment1 = new Appointment(offeredService1,professional1,client1,LocalDateTime.now());

        System.out.println(appointment1);


    }
}
