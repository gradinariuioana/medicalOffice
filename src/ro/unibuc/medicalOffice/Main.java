package ro.unibuc.medicalOffice;
import ro.unibuc.medicalOffice.domain.*;
import ro.unibuc.medicalOffice.service.DoctorService;
import ro.unibuc.medicalOffice.service.PatientService;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*

        System.out.println("Hello! Please choose the user mode: (Patient or Doctor)");
        String user = new Scanner(System.in).nextLine();
        if (user.equals("Patient")) {
            PatientService patient = new PatientService();
            String option = "0";
            while(!option.equals("7"))
            {
                System.out.println("please choose what you what you want to do:\n" +
                    "1. Make an appointment\n" +
                    "2. See your appointments\n" +
                    "3. See you referrals\n" +
                    "4. See your prescriptions\n" +
                    "5. See your Sick Leave Certificates\n" +
                    "6. See your examinations\n"+
                    "7. Exit");
                option = new Scanner(System.in).nextLine();
                switch (option) {
                    case "1":
                        patient.makeAppointment();
                        break;
                    case "2":
                        Appointment[] appointments = patient.getAppointmentsForPatient();
                        for (Appointment appointment : appointments) {
                            System.out.println("Doctor: " + appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName() +
                                    " Date: " + appointment.getDate() + " Intention: " + appointment.getMotive());
                        }
                        break;
                    case "3":
                        Referral[] referrals = patient.getReferralsForPatient();
                        for (Referral referral : referrals) {
                            System.out.println("From Doctor: " + referral.getFromDoctor().getFirstName() + " " + referral.getFromDoctor().getLastName() +
                                    " To Doctor: "+referral.getToDoctor().getFirstName()+" "+referral.getToDoctor().getLastName()+
                                    " Diagnosis: "+ referral.getDiagnosis().getDescription()+
                                    " Date: " + referral.getDate() + " Patient : " + referral.getPatient().getCnp());
                        }
                        break;
                    case "4":
                        Prescription[] prescriptions = patient.getPrescriptionsForPatient();
                        for (Prescription prescription : prescriptions) {
                            System.out.println("Doctor: " + prescription.getDoctor().getFirstName() + " " + prescription.getDoctor().getLastName() +
                                    " Patient: "+prescription.getPatient().getFirstName()+" "+prescription.getPatient().getLastName()+
                                    " Date: "+ prescription.getDate()+
                                    " Drugs: " + prescription.getDrugs());
                        }
                        break;
                    case "5":
                        SickLeaveCertificate[] sickLeaveCertificates = patient.getSickLeaveCertificatesForPatient();
                        for (SickLeaveCertificate sickLeaveCertificate : sickLeaveCertificates) {
                            System.out.println("Doctor: " + sickLeaveCertificate.getDoctor().getFirstName() + " " + sickLeaveCertificate.getDoctor().getLastName() +
                                    " Patient: "+sickLeaveCertificate.getPatient().getFirstName()+" "+sickLeaveCertificate.getPatient().getLastName()+
                                    " Diagnosis: "+ sickLeaveCertificate.getDiagnosis().getDescription()+
                                    " Starting Date: " + sickLeaveCertificate.getStartingDate() +
                                    " Number of Days : " + sickLeaveCertificate.getNumberOfDays()+
                                    " Type: " + sickLeaveCertificate.getType());
                        }
                        break;
                    case "6":
                        Examination[] examinations = patient.getExaminationsForPatient();
                        for (Examination examination : examinations) {
                            System.out.println("Doctor: " + examination.getDoctor().getFirstName() + " " + examination.getDoctor().getLastName() +
                                    " Patient: " + examination.getPatient().getFirstName() + " " + examination.getPatient().getLastName() +
                                    " Diagnosis: " + examination.getDiagnosis().getDescription() +
                                    " Date: " + examination.getDate());
                        }
                        break;
                    default:
                        System.out.println("Thank you! Have a great day!");
                }
            }
        }
        else {
            DoctorService doctor = new DoctorService();
            String option = "0";
            while(!option.equals("6"))
            {
                System.out.println("please choose what you what you want to do:\n" +
                        "1. See your appointments\n" +
                        "2. Write a prescription\n" +
                        "3. Write a referral\n" +
                        "4. Write a Sick Leave Certificate\n" +
                        "5. Note an examination\n"+
                        "6. Exit");
                option = new Scanner(System.in).nextLine();
                switch (option) {
                    case "1":
                        Appointment[] appointments = doctor.getAppointmentsForDoctor();
                        for (Appointment appointment : appointments) {
                            System.out.println("Patient: " + appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName() +
                                    " Date: " + appointment.getDate() + " Intention: " + appointment.getMotive());
                        }
                        break;
                    case "2":
                        doctor.writePrescription();
                        break;
                    case "3":
                        doctor.writeReferral();
                        break;
                    case "4":
                        doctor.writeSickLeaveCertificate();
                        break;
                    case "5":
                        doctor.noteExamination();
                        break;
                    default:
                        System.out.println("Thank you! Have a great day!");
                }
            }
        }*/
    }
}
