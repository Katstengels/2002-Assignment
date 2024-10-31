import java.util.ArrayList;
public class SubMain {

    public void test(){
        MedicineInv mList = MedicineInv.getInstance();

        String m1 = "Paracetamol";

        mList.printList();
        mList.plusStock("Amoxicillin",100);

        mList.printList();


    }

}
