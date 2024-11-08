package project2002;

public class PrescriptedMed {

    private String medication;
    private int medicationAmount;
    private boolean medIsFilled;  //True = filled False = not filled

    public PrescriptedMed(String med, int medAmt){
        this.medication = med;
        this.medicationAmount = medAmt;
        this.medIsFilled = false;
    }

    public String getMedName() {
        return medication;
    }
    public int getMedAmount() {
        return medicationAmount;
    }
    public boolean isMedIsFilled() {
        return medIsFilled;
    }
}