package ro.unibuc.medicalOffice.dao;

import ro.unibuc.medicalOffice.csvReaderWriter;
import ro.unibuc.medicalOffice.domain.Appointment;
import ro.unibuc.medicalOffice.domain.Doctor;
import ro.unibuc.medicalOffice.domain.Patient;

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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        String details = strDate+","+motive+","+patient.getCnp()+","+doctor.getFirstName()+","+doctor.getLastName();
        readerWriter.writeCsv("Appointments", details);
    }

    public void addAppointmentFromCsv(Date date, String motive, Patient patient, Doctor doctor){
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
