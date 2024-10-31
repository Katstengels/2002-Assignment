import java.util.ArrayList;

public class Main {



    public static void main(String[] args) {
        MedicineInv mList = MedicineInv.getInstance();
        PatientInv pList = PatientInv.getInstance();

        StaffInv sList = StaffInv.getInstance(); //Get imported StaffInv

        ArrayList<Staff> staffList = sList.copyStaffList(); //Use members in staff list instance from StaffInv

        sList.printList(); //Print staffList



        boolean pwTest = staffList.get(0).changePassword("password", "AssWipe");

        if (pwTest) System.out.println("LOL");

        sList.printList();

        pwTest = staffList.get(0).changePassword("AssWipe", "BootLicker");

        for (Staff s : staffList) {     //Find Doctor member in staff list
            if (s instanceof Doctor) {
                Doctor doc = (Doctor) s;
                doc.houseMD();                          //Use houseMD function in Doctor class
                System.out.println(doc.toString());     //Use toString inherited from Staff class

            }
        }



                SubMain test = new SubMain();
        test.test();

    }


}
