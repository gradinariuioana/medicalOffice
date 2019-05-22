package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.DBConnection;
import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Appointment;
import ro.unibuc.medicalOffice.domain.Doctor;
import ro.unibuc.medicalOffice.domain.Patient;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentDao {
    private Appointment[] appointments;

    public AppointmentDao(){
        appointments = new Appointment[0];
    }

    public void addAppointment(Date date, String motive, Patient patient, Doctor doctor){
        Appointment ob = new Appointment(date, motive, patient, doctor);
        Appointment[] aux_list = new Appointment[appointments.length + 1];

        for(int i = 0; i < appointments.length; i++)
            aux_list[i] = appointments[i];
        aux_list[aux_list.length - 1] = ob;
        appointments = aux_list;

        csvReaderWriter readerWriter = csvReaderWriter.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        String details = strDate+","+motive+","+patient.getCnp()+","+doctor.getFirstName()+","+doctor.getLastName();
        readerWriter.writeCsv("Appointments", details);

        try {
            String[] args = new String[4];
            String[] argstype = new String[4];
            args[0] = strDate;
            args[1] = motive;
            args[2] = patient.getCnp();
            args[3] = doctor.getPhone_number();
            DBConnection.write("appointments", args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readAppointment(Date date, String motive, Patient patient, Doctor doctor){
        Appointment ob = new Appointment(date, motive, patient, doctor);
        Appointment[] aux_list = new Appointment[appointments.length + 1];

        for(int i = 0; i < appointments.length; i++)
            aux_list[i] = appointments[i];
        aux_list[aux_list.length - 1] = ob;
        appointments = aux_list;
    }

    public Appointment getAppointment(Date date,Patient patient, Doctor doctor){
        for(int i = 0; i < appointments.length; i++)
            if(appointments[i].getDate().equals(date) && appointments[i].getDoctor().equals(doctor) && appointments[i].getPatient().equals(patient))
                return appointments[i];
        return null;
    }

    public Appointment getAppointment (int index){
        if(index >= 0 && index < appointments.length)
            return appointments[index];
        return null;
    }

    public void deleteAppointment(Date date,Patient patient, Doctor doctor){
        for(int i = 0; i < appointments.length; i++)
            if(appointments[i].getDate().equals(date) && appointments[i].getDoctor().equals(doctor) && appointments[i].getPatient().equals(patient))
                appointments[i] = null;
    }

    public int getSize(){
        return appointments.length;
    }
}
