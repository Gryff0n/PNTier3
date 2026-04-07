package ordre;

public interface LanceurOrdre {

    void fireOrdre(TypeOrdre ordre);

    void abonnement(EcouteurOrdre e, TypeOrdre...types);
}
